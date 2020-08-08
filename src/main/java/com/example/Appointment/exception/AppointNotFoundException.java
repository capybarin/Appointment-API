package com.example.Appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class AppointNotFoundException extends RuntimeException{
    public AppointNotFoundException(Integer i){
        super("Appointment with id \"" + i + "\" not found");
    }
}
