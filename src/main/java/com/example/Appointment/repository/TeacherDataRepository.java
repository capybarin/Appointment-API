package com.example.Appointment.repository;

import com.example.Appointment.entity.TeacherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherDataRepository extends JpaRepository<TeacherData, Integer> {

    @Query(value = "SELECT td.id, td.workfrom, td.workto, td.currency, td.price, td.teacher_id" +
            " FROM teacher_data td WHERE teacher_id = :i", nativeQuery = true)
    List<TeacherData> findAllByTeacher_id(@Param("i") Integer i);
}
