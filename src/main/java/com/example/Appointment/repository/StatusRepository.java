package com.example.Appointment.repository;

import com.example.Appointment.entity.Role;
import com.example.Appointment.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Role findByName(String name);
}
