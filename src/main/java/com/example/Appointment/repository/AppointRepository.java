package com.example.Appointment.repository;

import com.example.Appointment.entity.Appoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointRepository extends JpaRepository<Appoint, Integer> {

    @Query(value = "SELECT a.id, a.date, a.timestart, a.timeend, a.currency, a.status_id, a.price, a.teacher_id, a.student_id" +
            " FROM appoint a WHERE teacher_id = :i", nativeQuery = true)
    List<Appoint> findAllByTeacher_id(@Param("i") Integer i);
}
