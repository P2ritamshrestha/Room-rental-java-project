package com.room_rental.com.stha.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.core.io.FileSystemResource;


import java.io.File;
import java.nio.charset.StandardCharsets;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;


    @Value("${naya.aawas.user.confirm}")
    private String passwordEmailTemplate;

    public void sendConfirmLinkToEmail(String to, String token) throws MessagingException {
//        String confirmationUrl = "http://localhost:5173/login?token=" + token;
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//
//        helper.setTo(to);
//        helper.setSubject("Mail Aayo !! Mail Aayo");
//
//        // HTML content for the email
//        String emailContent = "<p>Your Confirmation Link is here    :<a href=\"" + confirmationUrl + "\">Click Here</a>.</p>";
//        helper.setText(emailContent, true);  // 'true' indicates that the content is HTML
//        mailSender.send(mimeMessage);


        MimeMessage message = mailSender.createMimeMessage();
        String confirmationUrl = "http://localhost:5173/login?token=" + token;
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("link", confirmationUrl);
        helper.setTo(to);
        helper.setSubject("Mail Aayo !! Mail Aayo");
        String html = springTemplateEngine.process(passwordEmailTemplate,context);
        helper.setText(html,true);
        addImageAttachments(helper);
//        addPdfAttachment(helper);
        mailSender.send(message);
    }

    private void addImageAttachments(MimeMessageHelper helper) throws MessagingException {
        helper.addInline("logo", new ClassPathResource("templates/image/loogo.png"), "image/png");
        helper.addInline("facebook", new ClassPathResource("templates/image/facebook.png"), "image/png");
        helper.addInline("twitter", new ClassPathResource("templates/image/twitter.png"), "image/png");
        helper.addInline("linkedin", new ClassPathResource("templates/image/linkedin.png"), "image/png");
        helper.addInline("youtube", new ClassPathResource("templates/image/youtube.png"), "image/png");

    }
//    private void addPdfAttachment(MimeMessageHelper helper) throws MessagingException {
//        FileSystemResource file = new FileSystemResource(new File("C:/Users/Admin/Documents/CV-PRITAM.pdf"));
//        helper.addAttachment("document.pdf", file);
//    }
}
