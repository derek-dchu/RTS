package com.mercury.rts.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailAppBean {
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void sendMail(String to, String subject, String dear, String content) throws MailParseException, MailException {
		MimeMessage message = mailSender.createMimeMessage();
		if (subject == null) subject = simpleMailMessage.getSubject();
		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(simpleMailMessage.getFrom());
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(String.format(simpleMailMessage.getText(), dear, content));
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		System.out.println("Ready to send mail");
		mailSender.send(message);
	}
}