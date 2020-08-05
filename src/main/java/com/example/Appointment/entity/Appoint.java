package com.example.Appointment.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "appoint")
public class Appoint {

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

    @Column(name = "status_id")
    private String status_id;

    @Column(name = "price")
    private String price;

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

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Appoint{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", timestart='" + timestart + '\'' +
                ", timeend='" + timeend + '\'' +
                ", status_id='" + status_id + '\'' +
                ", price='" + price + '\'' +
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
                status_id.equals(appoint.status_id) &&
                price.equals(appoint.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, timestart, timeend, status_id, price);
    }
}
