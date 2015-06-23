package com.rts.mail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

public class MailAppBean {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage simpleMailMessage;
	@Autowired
	private VelocityEngine velocityEngine;
	
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
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	/*public void sendMail(String to, String subject, String dear, String content) throws MailParseException, MailException {
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
	}*/
	public void sendMail(final String template, final String to, final String subject, final String name,
						 final Object content) {
		final String templatePath = String.format("main/resources/templates/%s.vm", template);
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to);
                message.setSubject(subject);
                message.setFrom("admin@rts.com"); // could be parameterized...
                Map model = new HashMap();
                model.put("name", name);
                model.put("content", content);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, templatePath, model);
                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
	}
}