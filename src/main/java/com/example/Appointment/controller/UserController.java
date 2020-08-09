package com.example.Appointment.controller;

import com.example.Appointment.entity.User;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.UserNotFoundException;
import com.example.Appointment.repository.*;
import com.example.Appointment.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private RoleRepository roleRepository;

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
        } catch (NullPointerException e) {
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
        emailSenderService.sendHelloMessage(tmpUser);
        try {
            return userRepository.save(tmpUser);
        } catch (Exception e) {
            throw new ParameterMissingException("email");
        }
    }

    @GetMapping("/users")
    public List<User> all(@RequestParam(value = "role", defaultValue = "all") String role) {
        switch (role.toLowerCase()){
            case "student": return userRepository.findAllByRole_id(1);
            case "teacher": return userRepository.findAllByRole_id(2);
            case "all": return userRepository.findAll();
            default: return userRepository.findAll();
        }
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
