package com.example.Appointment.repository;

import com.example.Appointment.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByName(String name);
    Status findFirstById(Integer i);
}
