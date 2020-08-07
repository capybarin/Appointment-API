package com.example.Appointment.repository;

import com.example.Appointment.entity.TeacherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface TeacherDataRepository extends JpaRepository<TeacherData, Integer> {

    @Query(value = "SELECT td.id, td.workfrom, td.workto, td.currency, td.price, td.teacher_id, td.date" +
            " FROM teacher_data td WHERE td.date = :d AND td.teacher_id = :i " +
            "AND :value < td.workto", nativeQuery = true)
    List<TeacherData> findIfTimeSlotExists(@Param("i") Integer i, @Param("d") LocalDate d, @Param("value") Time value);
}
