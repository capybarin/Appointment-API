package com.example.Appointment.repository;

import com.example.Appointment.entity.Appoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointRepository extends JpaRepository<Appoint, Integer> {

    @Query(value = "SELECT a.id, a.status_id, a.student_id, a.teacher_data_id" +
            " FROM appoint a WHERE a.status_id <> 2", nativeQuery = true)
    List<Appoint> findAllExcludeDeclined();

    @Query(value = "SELECT a.id, a.status_id, a.student_id, a.teacher_data_id" +
            " FROM appoint a WHERE a.status_id = 1", nativeQuery = true)
    List <Appoint> findAllApproved();

    @Query(value = "SELECT a.id, a.status_id, a.student_id, a.teacher_data_id" +
            " FROM appoint a WHERE a.status_id = 2", nativeQuery = true)
    List <Appoint> findAllDeclined();

    @Query(value = "SELECT a.id, a.status_id, a.student_id, a.teacher_data_id" +
            " FROM appoint a WHERE a.status_id = 3", nativeQuery = true)
    List <Appoint> findAllNegotiation();

    @Query(value = "SELECT a.id, a.status_id, a.student_id, a.teacher_data_id" +
            " FROM appoint a WHERE a.status_id = 4", nativeQuery = true)
    List <Appoint> findAllOpen();
}
