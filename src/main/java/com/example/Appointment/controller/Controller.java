package com.example.Appointment.controller;

import com.example.Appointment.entity.Appoint;
import com.example.Appointment.entity.TeacherData;
import com.example.Appointment.entity.User;
import com.example.Appointment.exception.AppointNotFoundException;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.UserNotFoundException;
import com.example.Appointment.exception.WrongParameterException;
import com.example.Appointment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AppointRepository appointRepository;

    @Autowired
    private TeacherDataRepository teacherDataRepository;

    @PostMapping("/register")
    public User newUser(@RequestBody User newUser) {
        User tmpUser = new User();
        if (newUser.getFirstName() == null || newUser.getFirstName().isEmpty()) {
            throw new ParameterMissingException("firstName");
        } else {
            tmpUser.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() == null || newUser.getLastName().isEmpty()) {
            throw new ParameterMissingException("lastName");
        } else {
            tmpUser.setLastName(newUser.getLastName());
        }
        try {
            if (newUser.getRole_id().getId() == null ||
                    newUser.getRole_id().getId().toString().isEmpty()) {
                throw new ParameterMissingException("role id");
            } else {
                if (newUser.getRole_id().getId().equals(1)) {
                    tmpUser.setRole_id(roleRepository.findByName("STUDENT"));
                } else if (newUser.getRole_id().getId().equals(2)) {
                    tmpUser.setRole_id(roleRepository.findByName("TEACHER"));
                } else throw new ParameterMissingException("role id");
            }
        } catch (NullPointerException e){
            throw new ParameterMissingException("role_id");
        }
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new ParameterMissingException("email");
        } else {
            tmpUser.setEmail(newUser.getEmail());
        }
        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            throw new ParameterMissingException("password");
        } else {
            tmpUser.setPassword(newUser.getPassword());
        }
        try {
            return userRepository.save(tmpUser);
        } catch (Exception e){
            throw new ParameterMissingException("email");
        }
    }

    @GetMapping("/users")
    public List<User> all(@RequestParam(value = "role", defaultValue = "all") String role){
        if (role.toLowerCase().equals("student")){
            return userRepository.findAllByRole_id(1);
        }
        else if (role.toLowerCase().equals("teacher")){
            return userRepository.findAllByRole_id(2);
        }
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/users/teacher/data")
    public TeacherData newData(@RequestBody TeacherData newData, Authentication authentication){
        TeacherData tmp = new TeacherData();
        LocalDate localDate = LocalDate.now();
        Date from;
        Date to;
        Date currDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        if (newData.getWorkfrom() == null) {
            throw new ParameterMissingException("workfrom");
        }
        try{
            from = simpleDateFormat.parse(String.valueOf(newData.getWorkfrom()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"workfrom\" parameter");
        }
        if (newData.getWorkto() == null) {
            throw new ParameterMissingException("workto");
        }
        try{
            to = simpleDateFormat.parse(String.valueOf(newData.getWorkto()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"workto\" parameter");
        }
        if (from.after(to)){
            throw new WrongParameterException("\"workto\" should not be before current \"workfrom\"");
        } else {
            tmp.setWorkfrom(Time.valueOf(String.valueOf(newData.getWorkfrom())));
            tmp.setWorkto(Time.valueOf(String.valueOf(newData.getWorkto())));
        }
        if (newData.getCurrency() == null || newData.getCurrency().isEmpty()) {
            throw new ParameterMissingException("currency");
        } else {
            tmp.setCurrency(newData.getCurrency());
        }
        if (newData.getPrice() == null || newData.getPrice().isEmpty()) {
            throw new ParameterMissingException("price");
        } else {
            tmp.setPrice(newData.getPrice());
        }
        tmp.setTeacher_id(userRepository.findByEmail(authentication.getName()));

        if (newData.getDate() == null){
            throw new ParameterMissingException("date");
        }
        Date toBeParsed;
        try{
            toBeParsed = sdf.parse(String.valueOf(newData.getDate()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"date\" parameter");
        }
        if (toBeParsed.before(currDate)){
            throw new WrongParameterException("Your \"date\" should not be before current date");
        }
        tmp.setDate(newData.getDate());

        if (!teacherDataRepository.findIfTimeSlotExists(userRepository.findByEmail(authentication.getName()).getId(),
                newData.getDate(), newData.getWorkfrom()).isEmpty()){
            throw new WrongParameterException("Time slots intersecting error");
        } else {
            return teacherDataRepository.save(tmp);
        }
    }

    @GetMapping("/users/teacher/data/me")
    public List <TeacherData> getDataForCurrentUser (Authentication authentication){
        return teacherDataRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
    }

    @GetMapping("/users/teacher/data")
    public List<TeacherData> teacherData (){
        return teacherDataRepository.findAll();
    }

    @PostMapping("/appointment")
    public Appoint newAppoint(@RequestBody Appoint newAppoint, Authentication authentication) {
        Appoint tmpAppoint = new Appoint();
        tmpAppoint.setStatus_id(statusRepository.findByName("Open"));
        tmpAppoint.setStudent_id(null);
        if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("STUDENT")){
            throw new WrongParameterException("Students cannot create new appointments");
        }
        List<TeacherData> data = teacherDataRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
        List<Integer> allowedIds = new ArrayList<>();
        for (TeacherData td: data) {
            allowedIds.add(td.getId());
        }
        if (newAppoint.getTeacher_data_id() == null){
            throw new ParameterMissingException("teacher_data_id");
        } else {
            if (!allowedIds.contains(newAppoint.getTeacher_data_id().getId())){
                throw new ParameterMissingException("You cannot use other teacher's ids");
            }
        }
        tmpAppoint.setTeacher_data_id(newAppoint.getTeacher_data_id());
        return appointRepository.save(tmpAppoint);
    }

    @GetMapping("/appointment")
    public List<Appoint> getAllAppoints(){
        return appointRepository.findAll();
    }

    @PostMapping("/appointment/{id}/reservation")
    public Appoint makeReservation (@PathVariable Integer id, Authentication authentication){
        Appoint appoint = appointRepository.findById(id).orElseThrow(() -> new AppointNotFoundException(id));
        if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("TEACHER")){
            throw new WrongParameterException("Teachers cannot make a reservation");
        }
        if (appoint.getStatus_id().getName().equals("Open")){
            appoint.setStatus_id(statusRepository.findByName("Negotiation"));
            appointRepository.save(appoint);
            return appoint;
        } else throw new WrongParameterException("You can't make a reservation with Appointment that is in the "
                + appoint.getStatus_id().getName() + " status");
    }
}
