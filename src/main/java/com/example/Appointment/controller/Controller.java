package com.example.Appointment.controller;

import com.example.Appointment.entity.Appoint;
import com.example.Appointment.entity.TeacherData;
import com.example.Appointment.entity.User;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.TeacherDataNotFoundException;
import com.example.Appointment.exception.UserNotFoundException;
import com.example.Appointment.exception.WrongParameterException;
import com.example.Appointment.repository.AppointRepository;
import com.example.Appointment.repository.RoleRepository;
import com.example.Appointment.repository.TeacherDataRepository;
import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @GetMapping("/users/teacher/data")
    public List <TeacherData> getAll(Authentication authentication){
        return teacherDataRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
    }

    @PostMapping("/users/teacher/data")
    public TeacherData newTeacherData(@RequestBody TeacherData newTeacherData, Authentication authentication) {
        TeacherData tmpData = new TeacherData();
        System.out.println("Work: "+newTeacherData.getWorkfrom());
        System.out.println("Obj:" + newTeacherData.toString());
        if (newTeacherData.getWorkfrom() == null){
            throw new ParameterMissingException("workfrom");
        }
        if (newTeacherData.getWorkto() == null){
            throw new ParameterMissingException("workto");
        }
        if (newTeacherData.getCurrency() == null || newTeacherData.getCurrency().isEmpty()){
            throw new ParameterMissingException("currency");
        }else {
            tmpData.setCurrency(newTeacherData.getCurrency());
        }
        if (newTeacherData.getPrice() == null || newTeacherData.getPrice().isEmpty()){
            throw new ParameterMissingException("price");
        }else {
            tmpData.setPrice(newTeacherData.getPrice());
        }
        tmpData.setTeacher_id(userRepository.findByEmail(authentication.getName()));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date from;
        Date to;
        try {
            from = sdf.parse(String.valueOf(newTeacherData.getWorkfrom()));
            to = sdf.parse(String.valueOf(newTeacherData.getWorkto()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"workfrom\" parameter");
        }
        if (from.after(to)){
            throw new WrongParameterException("\"workto\" should not be before current \"workfrom\"");
        } else {
            System.out.println("from "+newTeacherData.getWorkfrom()+"\nto "+newTeacherData.getWorkto());
            tmpData.setWorkfrom(Time.valueOf(String.valueOf(newTeacherData.getWorkfrom())));
            tmpData.setWorkto(Time.valueOf(String.valueOf(newTeacherData.getWorkto())));
        }
        return teacherDataRepository.save(tmpData);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/teacher/data/{id}")
    public void deleteTeacherData(@PathVariable Integer id){
        teacherDataRepository.deleteById(id);
    }

    @GetMapping("/users/teacher/data/{id}")
    public TeacherData getTeacherData(@PathVariable Integer id){
        return teacherDataRepository.findById(id)
                .orElseThrow(() -> new TeacherDataNotFoundException(id));
    }

    @GetMapping("/appointment")
    public List <Appoint> getAppointsForUser(Authentication authentication){
        return appointRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
    }

    /*@PostMapping("/appointment")
    public Appoint newAppoint(@RequestBody Appoint newAppoint){
        Appoint tmpAppoint = new Appoint();
        LocalDate localDate = LocalDate.now();
        Date currDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }*/
    /*@PostMapping("/appointment")
    Appoint newAppoint(@RequestBody Appoint newAppoint) {
        Appoint tmpAppoint = new Appoint();
        LocalDate localDate = LocalDate.now();
        Date currDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate;
        try {
            newDate = sdf.parse(newAppoint.getDate());
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"date\" parameter");
        }
        if (newAppoint.getDate() == null || newAppoint.getDate().isEmpty() ){
            throw new ParameterMissingException("date");
        } else if (newDate.before(currDate)){
            throw new WrongParameterException("Your date should not be before current date");
        } else{
            tmpAppoint.setDate(newAppoint.getDate());
        }

        return appointRepository.save(tmpAppoint);
    }*/
}
