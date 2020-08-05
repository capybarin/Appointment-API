package com.example.Appointment.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teacher_data")
public class TeacherData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "timestart")
    private String timestart;

    @Column(name = "timeend")
    private String timeend;

    @Column(name = "worktime")
    private String worktime;

    @Column(name = "workprice")
    private String workprice;

    @Column(name = "currency")
    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getWorkprice() {
        return workprice;
    }

    public void setWorkprice(String workprice) {
        this.workprice = workprice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "TeacherData{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", timestart='" + timestart + '\'' +
                ", timeend='" + timeend + '\'' +
                ", worktime='" + worktime + '\'' +
                ", workprice='" + workprice + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherData that = (TeacherData) o;
        return id.equals(that.id) &&
                date.equals(that.date) &&
                timestart.equals(that.timestart) &&
                timeend.equals(that.timeend) &&
                worktime.equals(that.worktime) &&
                workprice.equals(that.workprice) &&
                currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, timestart, timeend, worktime, workprice, currency);
    }
}
