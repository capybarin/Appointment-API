package com.example.Appointment.controller;


import com.example.Appointment.entity.Appoint;
import com.example.Appointment.repository.AppointRepository;
import com.example.Appointment.repository.RoleRepository;
import com.example.Appointment.repository.StatusRepository;
import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AppointRepository appointRepository;

    /*@GetMapping("/appointments/teacher")
    List<Appoint> all(Authentication auth) {
        return (List<Appoint>) appointRepository.findAllByTeacher_id(userRepository.findByEmail(auth.getName()).getId());
    }
    */
}
