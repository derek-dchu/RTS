package com.rts.config;

import com.rts.mail.MailApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Autowired
    private Environment e;

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl bean = new JavaMailSenderImpl();
        bean.setHost(e.getProperty("email.host"));
        bean.setPort(Integer.parseInt(e.getProperty("email.port")));
        bean.setUsername(e.getProperty("email.username"));
        bean.setPassword(e.getProperty("email.password"));
        bean.setJavaMailProperties(new Properties() {
            {
                setProperty("mail.smtp.auth", e.getProperty("email.smtp.auth"));
                setProperty("mail.smtp.starttls.enable", e.getProperty("email.smtp.starttls.enable"));
            }
        });
        return bean;
    }

    @Autowired
    @Bean
    public MailApp mailApp(JavaMailSender mailSender, SimpleMailMessage customMailMessage) {
        return new MailApp(mailSender, customMailMessage);
    }

    @Bean
    public SimpleMailMessage customMailMessage() {
        SimpleMailMessage bean = new SimpleMailMessage();
        bean.setFrom(e.getProperty("email.customMessage.from"));
        bean.setSubject(e.getProperty("email.customMessage.subject"));
        bean.setText(e.getProperty("email.customMessage.text"));
        return bean;
    }

    @Bean
    public VelocityEngineFactoryBean velocityEngine() {
        VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
        velocityEngine.setVelocityProperties(new Properties() {
            {
                setProperty("resource.loader", e.getProperty("velocityEngine.resource.loader"));
                setProperty("class.resource.loader.class", e.getProperty("velocityEngine.class.resource.loader.class"));
            }
        });
        return velocityEngine;
    }
}
