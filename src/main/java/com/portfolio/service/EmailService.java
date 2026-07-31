package com.portfolio.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:admin@example.com}")
    private String recipientEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactNotification(String senderName, String senderEmail, String subject, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Portfolio Contact Form: " + (subject != null ? subject : "No Subject"));
        message.setText("You have received a new message from your portfolio contact form.\n\n" +
                "Sender Details:\n" +
                "Name: " + senderName + "\n" +
                "Email: " + senderEmail + "\n\n" +
                "Message:\n" +
                messageText);
        
        try {
            mailSender.send(message);
            log.info("Email notification sent successfully to {}", recipientEmail);
        } catch (MailException ex) {
            log.error("Failed to send email notification to {}. Error: {}", recipientEmail, ex.getMessage());
            // We catch this error, so contact message submission does not fail if SMTP settings are invalid/missing.
        }
    }
}
