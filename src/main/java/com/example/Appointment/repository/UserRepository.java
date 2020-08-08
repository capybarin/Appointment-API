package com.example.Appointment.repository;

import com.example.Appointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query(value = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, u.role_id" +
            " FROM users u WHERE role_id = :i", nativeQuery = true)
    List<User> findAllByRole_id(@Param("i") Integer i);

    @Query(value = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, u.role_id" +
            " FROM users u WHERE u.id = :i", nativeQuery = true)
    User findByTeacher_id(@Param("i") Integer i);
}
