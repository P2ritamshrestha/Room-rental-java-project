package com.room_rental.com.stha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmLinkToEmail(String to, String token) {
        String confirmationUrl = "http://localhost:5173/login?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirm your registration");
        String emailContent = "Please <a href=\"" + confirmationUrl + "\">click here</a> to confirm your registration.";
        message.setText(emailContent);
        mailSender.send(message);
    }
}