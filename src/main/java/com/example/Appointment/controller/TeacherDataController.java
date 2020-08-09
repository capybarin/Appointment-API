package com.example.Appointment.controller;

import com.example.Appointment.entity.TeacherData;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.WrongParameterException;
import com.example.Appointment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class TeacherDataController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherDataRepository teacherDataRepository;

    @PostMapping("/teacher/data")
    public TeacherData newData(@RequestBody TeacherData newData, Authentication authentication) {
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
        try {
            from = simpleDateFormat.parse(String.valueOf(newData.getWorkfrom()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"workfrom\" parameter");
        }
        if (newData.getWorkto() == null) {
            throw new ParameterMissingException("workto");
        }
        try {
            to = simpleDateFormat.parse(String.valueOf(newData.getWorkto()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"workto\" parameter");
        }
        if (from.after(to)) {
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

        if (newData.getDate() == null) {
            throw new ParameterMissingException("date");
        }
        Date toBeParsed;
        try {
            toBeParsed = sdf.parse(String.valueOf(newData.getDate()));
        } catch (ParseException e) {
            throw new WrongParameterException("Invalid \"date\" parameter");
        }
        if (toBeParsed.before(currDate)) {
            throw new WrongParameterException("Your \"date\" should not be before current date");
        }
        tmp.setDate(newData.getDate());

        if (!teacherDataRepository.findIfTimeSlotExists(userRepository.findByEmail(authentication.getName()).getId(),
                newData.getDate(), newData.getWorkfrom()).isEmpty()) {
            throw new WrongParameterException("Time slots intersecting error");
        } else {
            return teacherDataRepository.save(tmp);
        }
    }

    @GetMapping("/teacher/data/me")
    public List<TeacherData> getDataForCurrentUser(Authentication authentication) {
        return teacherDataRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
    }

    @GetMapping("/teacher/data")
    public List<TeacherData> teacherData() {
        return teacherDataRepository.findAll();
    }

}
