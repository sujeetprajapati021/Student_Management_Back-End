package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    // fetching the Email host, port, username, password and other details from application.yml

    @Value("${spring.mail.username}")
    private String emailUserName;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.port}")
    private int emailPort;

    @Value("${spring.mail.is_auth}")
    private Boolean emailIsAuth;

    // creating the bean of JavaMailSender so that we can use this JavaMailSender methods
    // by creating the object of JavaMailSender

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(emailPort);
        mailSender.setUsername(emailUserName);
        mailSender.setPassword(emailPassword);

        /*
         * If u got AuthenticationFailedException then
         * 1. Log on email
         * 2. account, in Account -> click Security ->
         * 3. turn off 2-step verification and
         * 4. turn on "Less secure app access"
         */

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", emailIsAuth.toString());
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
