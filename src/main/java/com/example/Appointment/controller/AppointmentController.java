package com.example.Appointment.controller;

import com.example.Appointment.entity.Appoint;
import com.example.Appointment.entity.TeacherData;
import com.example.Appointment.exception.AppointNotFoundException;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.WrongParameterException;
import com.example.Appointment.repository.*;
import com.example.Appointment.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AppointRepository appointRepository;

    @Autowired
    private TeacherDataRepository teacherDataRepository;

    @PostMapping("/appointment")
    public Appoint newAppoint(@RequestBody Appoint newAppoint, Authentication authentication) {
        Appoint tmpAppoint = new Appoint();
        tmpAppoint.setStatus_id(statusRepository.findByName("Open"));
        tmpAppoint.setStudent_id(null);
        if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("STUDENT")) {
            throw new WrongParameterException("Students cannot create new appointments");
        }
        List<TeacherData> data = teacherDataRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
        List<Integer> allowedIds = new ArrayList<>();
        for (TeacherData td : data) {
            allowedIds.add(td.getId());
        }
        if (newAppoint.getTeacher_data_id() == null) {
            throw new ParameterMissingException("teacher_data_id");
        } else {
            if (!allowedIds.contains(newAppoint.getTeacher_data_id().getId())) {
                throw new ParameterMissingException("You cannot use other teacher's ids");
            }
        }
        tmpAppoint.setTeacher_data_id(newAppoint.getTeacher_data_id());
        return appointRepository.save(tmpAppoint);
    }

    @GetMapping("/appointment")
    public List<Appoint> getAllAppoints(@RequestParam(value = "status", defaultValue = "excludeDeclined") String status) {
        switch (status.toLowerCase()){
            case "all": return appointRepository.findAll();
            case "approved": return appointRepository.findAllApproved();
            case "declined": return appointRepository.findAllDeclined();
            case "negotiation": return appointRepository.findAllNegotiation();
            case "open": return appointRepository.findAllOpen();
            default: return appointRepository.findAllExcludeDeclined();
        }
    }

    @PostMapping("/appointment/{id}/reservation")
    public Appoint makeReservation(@PathVariable Integer id, Authentication authentication) {
        Appoint appoint = appointRepository.findById(id).orElseThrow(() -> new AppointNotFoundException(id));
        if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("TEACHER")) {
            throw new WrongParameterException("Teachers cannot make a reservation");
        }
        if (appoint.getStatus_id().getName().equals("Open")) {
            appoint.setStatus_id(statusRepository.findByName("Negotiation"));
            appoint.setStudent_id(userRepository.findByEmail(authentication.getName()));
            Appoint tmp = appointRepository.save(appoint);
            emailSenderService.sendTimeReservationMessage(userRepository.findByTeacher_id(tmp.getTeacher_data_id().getTeacher_id().getId()),
                    userRepository.findByEmail(authentication.getName()), tmp.getId(), 1);
            emailSenderService.sendTimeReservationMessage(userRepository.findByTeacher_id(tmp.getTeacher_data_id().getTeacher_id().getId()),
                    userRepository.findByEmail(authentication.getName()), tmp.getId(), 2);
            return tmp;
        } else throw new WrongParameterException("You can't make a reservation with Appointment that is in the "
                + appoint.getStatus_id().getName() + " status");
    }

    @PostMapping("/appointment/{id}/approve")
    public Appoint acceptReservation(@PathVariable Integer id, Authentication authentication) {
        Appoint appoint = appointRepository.findById(id).orElseThrow(() -> new AppointNotFoundException(id));
        if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("STUDENT")) {
            throw new WrongParameterException("Students cannot accept reservations");
        }
        /*List<TeacherData> teacherData = teacherDataRepository.findAllByTeacher_id(appoint.getTeacher_data_id().getId());
        List<Integer> allowedIds = new ArrayList<>();
        for (TeacherData td : teacherData) {
            allowedIds.add(td.getId());
        }
        if (!allowedIds.contains(appoint.getTeacher_data_id().getId())) {
            throw new ParameterMissingException("You cannot use other teacher's ids");
        }*/
        if (appoint.getStatus_id().getName().equals("Negotiation")) {
            appoint.setStatus_id(statusRepository.findByName("Approved"));
            return appointRepository.save(appoint);
        } else throw new WrongParameterException("You can't approve Appointment that is in the "
                + appoint.getStatus_id().getName() + " status");
    }

    @DeleteMapping("/appointment/{id}/decline")
    public Appoint declineReservation(@PathVariable Integer id, Authentication authentication) {
        Appoint appoint = appointRepository.findById(id).orElseThrow(() -> new AppointNotFoundException(id));
        if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("STUDENT")) {
            if (appoint.getStudent_id().getId().equals(userRepository.findByEmail(authentication.getName()).getId())) {
                if (appoint.getStatus_id().getName().equals("Approved") ||
                        appoint.getStatus_id().getName().equals("Negotiation")) {
                    appoint.setStatus_id(statusRepository.findByName("Declined"));
                } else throw new WrongParameterException("You can't decline Appointment that is in the "
                        + appoint.getStatus_id().getName() + " status");
            } else throw new WrongParameterException("You cannot decline other student's appointment");
        } else if (userRepository.findByEmail(authentication.getName()).getRole_id().getName().equals("TEACHER")) {
            /*List<TeacherData> data = teacherDataRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
            List<Integer> allowedIds = new ArrayList<>();
            for (TeacherData td : data) {
                allowedIds.add(td.getId());
            }
            if (!allowedIds.contains(appoint.getTeacher_data_id().getId())) {
                throw new ParameterMissingException("You cannot use other teacher's ids");
            }*/
            if (appoint.getStatus_id().getName().equals("Approved") ||
                    appoint.getStatus_id().getName().equals("Negotiation") ||
                    appoint.getStatus_id().getName().equals("Open")) {
                appoint.setStatus_id(statusRepository.findByName("Declined"));
            }
            else throw new WrongParameterException("You can't decline Appointment that is in the "
                    + appoint.getStatus_id().getName() + " status");
        }
        return appointRepository.save(appoint);
    }

}
