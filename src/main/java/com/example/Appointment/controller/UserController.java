package com.example.Appointment.controller;

import com.example.Appointment.entity.User;
import com.example.Appointment.exception.ParameterMissingException;
import com.example.Appointment.exception.UserNotFoundException;
import com.example.Appointment.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;


    UserController(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all(){
        return repository.findAll();
    }

    @PostMapping("/users/register/student")
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
            tmpUser.setRole(1);
            return repository.save(tmpUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User updateUser (@RequestBody User newUser, @PathVariable Long id){
        return repository.findById(id)
                .map(user -> {
                    user.setEmail(newUser.getEmail());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setPassword(newUser.getPassword());
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    void deleteStudent(@PathVariable Long id){
        repository.deleteById(id);
    }
}
