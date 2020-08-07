package com.example.Appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class TeacherDataNotFoundException extends RuntimeException {
    public TeacherDataNotFoundException(Integer id){
        super("Data with id \""+id+"\" not found");
    }
}
