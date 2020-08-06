package com.example.Appointment.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "appoint")
public class Appoint{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private String date;

    @Column(name = "timestart")
    private String timestart;

    @Column(name = "timeend")
    private String timeend;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status_id;

    @Column(name = "price")
    private String price;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher_id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student_id;

    @Column(name = "currency")
    private String currency;

    public Status getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Status status_id) {
        this.status_id = status_id;
    }

    public User getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(User teacher_id) {
        this.teacher_id = teacher_id;
    }

    public User getStudent_id() {
        return student_id;
    }

    public void setStudent_id(User student_id) {
        this.student_id = student_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
                status_id.equals(appoint.status_id) &&
                price.equals(appoint.price) &&
                teacher_id.equals(appoint.teacher_id) &&
                student_id.equals(appoint.student_id) &&
                currency.equals(appoint.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, timestart, timeend, status_id, price, teacher_id, student_id, currency);
    }

    @Override
    public String toString() {
        return "Appoint{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", timestart='" + timestart + '\'' +
                ", timeend='" + timeend + '\'' +
                ", status_id=" + status_id +
                ", price='" + price + '\'' +
                ", teacher_id=" + teacher_id +
                ", student_id=" + student_id +
                ", currency='" + currency + '\'' +
                '}';
    }
}
