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

}
