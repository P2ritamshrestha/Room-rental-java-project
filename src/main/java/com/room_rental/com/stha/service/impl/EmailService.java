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
        String confirmationUrl = "http://xxx.com/api/auth/confirm?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirm your registration");
        message.setText("Click the link below to confirm your registration: \n" + confirmationUrl);
        mailSender.send(message);
    }
}