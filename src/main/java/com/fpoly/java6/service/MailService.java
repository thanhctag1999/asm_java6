package com.fpoly.java6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.fpoly.java6.jpa.AccountJPA;

@Service
public class MailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendPasswordResetNotification(String email) {
        String message = "You can now reset your password. Please log in and change your password from your account settings.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText(message);
        mailMessage.setFrom("binhmtpc07886@fpt.edu.vn"); 

        emailSender.send(mailMessage);
    }
}
