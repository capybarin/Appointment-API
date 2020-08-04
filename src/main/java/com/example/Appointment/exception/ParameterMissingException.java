package com.example.Appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY)
public class ParameterMissingException extends RuntimeException{
    public ParameterMissingException(String param){
        super("Parameter \"" + param + "\" is missing or already exists");
    }
}
