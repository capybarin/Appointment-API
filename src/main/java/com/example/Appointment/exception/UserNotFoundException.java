package com.example.Appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such User")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id){
        super("User with id \"" + id + "\" not found");
    }
}
