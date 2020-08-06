package com.example.Appointment.repository;

import com.example.Appointment.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    public Status findByName(String name);
}
