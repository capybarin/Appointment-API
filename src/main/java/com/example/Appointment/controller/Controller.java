package com.example.Appointment.controller;

import com.example.Appointment.entity.Appoint;
import com.example.Appointment.entity.User;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.UserNotFoundException;
import com.example.Appointment.repository.AppointRepository;
import com.example.Appointment.repository.RoleRepository;
import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppointRepository appointRepository;

    @PostMapping("/register")
    User newUser(@RequestBody User newUser) {
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
    List<User> all(@RequestParam(value = "role", defaultValue = "all") String role){
        if (role.toLowerCase().equals("student")){
            return userRepository.findAllByRole_id(1);
        }
        else if (role.toLowerCase().equals("teacher")){
            return userRepository.findAllByRole_id(2);
        }
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/appointment")
    List <Appoint> getAppointsForUser(Authentication authentication){
        return appointRepository.findAllByTeacher_id(userRepository.findByEmail(authentication.getName()).getId());
    }
}
