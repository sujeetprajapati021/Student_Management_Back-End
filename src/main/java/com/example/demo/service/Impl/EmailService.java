package com.example.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String to, String from, String subject, String body, String[] cc) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
//			String content ="Contratulations !! "
//					+ "You have successfully register on Student App";
            messageHelper.setText(body, true);
        };
        try {
            javaMailSender.send(messagePreparator);
            System.out.println("User Register Welcome Message Sent Successfully");
        } catch (MailException e) {
            e.fillInStackTrace();
        }
    }
}
