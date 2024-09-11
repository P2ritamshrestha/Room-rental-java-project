package com.room_rental.com.stha.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmLinkToEmail(String to, String token) throws MessagingException {
        String confirmationUrl = "http://localhost:5173/login?token=" + token;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Mail Aayo !! Mail Aayo");

        // HTML content for the email
        String emailContent = "<p>Your Confirmation Link is here    :<a href=\"" + confirmationUrl + "\">Click Here</a>.</p>";
        helper.setText(emailContent, true);  // 'true' indicates that the content is HTML

        mailSender.send(mimeMessage);
    }
}
