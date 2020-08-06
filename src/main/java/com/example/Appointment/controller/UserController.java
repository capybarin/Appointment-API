package com.example.Appointment.controller;

import com.example.Appointment.entity.User;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.UserNotFoundException;
import com.example.Appointment.repository.RoleRepository;
import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    List<User> all(){
        return userRepository.findAll();
    }

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
            tmpUser.setRole(roleRepository.findByName((newUser.getRole().getName())));
            return userRepository.save(tmpUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteStudent(@PathVariable Long id){
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/hello")
    public String hello() {
        return "Hi user";
    }

}
