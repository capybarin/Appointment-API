package com.example.Appointment.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "appoint")
public class Appoint {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status_id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student_id;

    @ManyToOne
    @JoinColumn(name = "teacher_data_id")
    private TeacherData teacher_data_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Status status_id) {
        this.status_id = status_id;
    }

    public User getStudent_id() {
        return student_id;
    }

    public void setStudent_id(User student_id) {
        this.student_id = student_id;
    }

    public TeacherData getTeacher_data_id() {
        return teacher_data_id;
    }

    public void setTeacher_data_id(TeacherData teacher_data_id) {
        this.teacher_data_id = teacher_data_id;
    }

    @Override
    public String toString() {
        return "Appoint{" +
                "id=" + id +
                ", status_id=" + status_id +
                ", student_id=" + student_id +
                ", teacher_data_id=" + teacher_data_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appoint appoint = (Appoint) o;
        return id.equals(appoint.id) &&
                status_id.equals(appoint.status_id) &&
                student_id.equals(appoint.student_id) &&
                teacher_data_id.equals(appoint.teacher_data_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status_id, student_id, teacher_data_id);
    }
}
