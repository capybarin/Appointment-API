package com.example.Appointment.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "appoint")
public class Appoint {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private String date;

    @Column(name = "timestart")
    private Time timestart;

    @Column(name = "timeend")
    private Time timeend;

    @Column(name = "price")
    private String price;

    @Column(name = "currency")
    private String currency;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status_id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private User student_id;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private User teacher_id;

    @OneToOne
    @JoinColumn(name = "teacher_data_id")
    private TeacherData teacher_data_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getTimestart() {
        return timestart;
    }

    public void setTimestart(Time timestart) {
        this.timestart = timestart;
    }

    public Time getTimeend() {
        return timeend;
    }

    public void setTimeend(Time timeend) {
        this.timeend = timeend;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public User getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(User teacher_id) {
        this.teacher_id = teacher_id;
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
                ", date='" + date + '\'' +
                ", timestart=" + timestart +
                ", timeend=" + timeend +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", status_id=" + status_id +
                ", student_id=" + student_id +
                ", teacher_id=" + teacher_id +
                ", teacher_data_id=" + teacher_data_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appoint appoint = (Appoint) o;
        return id.equals(appoint.id) &&
                date.equals(appoint.date) &&
                timestart.equals(appoint.timestart) &&
                timeend.equals(appoint.timeend) &&
                price.equals(appoint.price) &&
                currency.equals(appoint.currency) &&
                status_id.equals(appoint.status_id) &&
                student_id.equals(appoint.student_id) &&
                teacher_id.equals(appoint.teacher_id) &&
                teacher_data_id.equals(appoint.teacher_data_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, timestart, timeend, price, currency, status_id, student_id, teacher_id, teacher_data_id);
    }
}
