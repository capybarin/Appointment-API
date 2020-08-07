package com.example.Appointment.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY)
public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String param){
        super(param);
    }
}
