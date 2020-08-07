package com.example.Appointment.entity;


import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "teacher_data")
public class TeacherData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "workfrom")
    private Time workfrom;

    @Column(name = "workto")
    private Time workto;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private String price;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private User teacher_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getWorkfrom() {
        return workfrom;
    }

    public void setWorkfrom(Time workfrom) {
        this.workfrom = workfrom;
    }

    public Time getWorkto() {
        return workto;
    }

    public void setWorkto(Time workto) {
        this.workto = workto;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(User teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "TeacherData{" +
                "id=" + id +
                ", workfrom=" + workfrom +
                ", workto=" + workto +
                ", currency='" + currency + '\'' +
                ", price='" + price + '\'' +
                ", teacher_id=" + teacher_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherData that = (TeacherData) o;
        return id.equals(that.id) &&
                workfrom.equals(that.workfrom) &&
                workto.equals(that.workto) &&
                currency.equals(that.currency) &&
                price.equals(that.price) &&
                teacher_id.equals(that.teacher_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workfrom, workto, currency, price, teacher_id);
    }
}
