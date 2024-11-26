package com.fpoly.java6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.model.dto.ContactRequest;
import com.fpoly.java6.model.dto.Response;
import com.fpoly.java6.service.MailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

	@Autowired
    private MailService emailService;

	 @PostMapping("/send")
	    public Response sendContactMessage(@RequestBody ContactRequest contactRequest) {
	        try {
	            String toEmail = "binhmtpc07886@fpt.edu.vn"; 
	            String subject = "Tin nhắn hỗ trợ từ " + contactRequest.getEmail();
	            String body = "Bạn có một tin nhắn hỗ trợ mới từ: " + contactRequest.getEmail() + "\n\n" + "Nội dung: " + contactRequest.getMessage();

	            // Gửi email
	            emailService.sendEmail(contactRequest.getEmail(), toEmail, subject, body);
	            return new Response("Email đã được gửi thành công!");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            return new Response("Lỗi gửi email: " + e.getMessage());
	        }
	    }

}
