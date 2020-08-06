package com.example.Appointment.repository;

import com.example.Appointment.entity.Appoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointRepository extends JpaRepository<Appoint, Integer> {
    //public Appoint findAllByTeacher_id(Integer id);
}
