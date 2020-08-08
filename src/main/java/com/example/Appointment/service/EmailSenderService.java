package com.example.Appointment.service;

import com.example.Appointment.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendHelloMessage(User tmpUser){
        MimeMessage msg = javaMailSender.createMimeMessage();
        String text = "<p><br>Hello "+tmpUser.getFirstName()+",</br><br>Your API access credentials:</br><br>Login - "
                +tmpUser.getEmail()+"</br><br>Password - "+tmpUser.getPassword()+"</br></p>";
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(tmpUser.getEmail());
            helper.setSubject("Thank you for sign up!");
            helper.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

    public void sendTimeReservationMessage(User teacher, User student, Integer id, int flag){
        MimeMessage msg = javaMailSender.createMimeMessage();
        String text = null;
        String mail = null;
        if (flag == 1) {
            text = "<p><br>Hello " + teacher.getFirstName() +
                    ",</br><br>Your time slot has been reserved by: " + student.getFirstName() + " " + student.getLastName() + " </br></p>" +
                    "<p><br>You can use POST \"/appointment/" + id + "/approve\" or DELETE \"/appointment/" + id + "/decline\" to Approve or Decline that reservation</br></p>";
            mail = teacher.getEmail();
        }
        if (flag == 2){
            text = "<p><br>Hello " + student.getFirstName() +
                    ",</br><br>You reserved " + student.getFirstName() + "'s " + student.getLastName() + " time slot</br></p>" +
                    "<p><br>You can use DELETE \"/appointment/" + id + "/decline\" to Decline that reservation</br></p>";
            mail = student.getEmail();
        }
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail);
            helper.setSubject("Time slot has been reserved");
            helper.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

}
