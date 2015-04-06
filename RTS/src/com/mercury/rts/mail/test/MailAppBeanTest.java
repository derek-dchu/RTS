package com.mercury.rts.mail.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mercury.rts.mail.MailAppBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/main/resources/test-rts-mail.xml")
public class MailAppBeanTest {
	@Autowired
	private MailAppBean mailApp;

	@Test()
	public void testSendMail() {
		String to = "mercurysystems000@gmail.com";
		String dear = "user";
		String content = "Thank you for using our system.";
		
		mailApp.sendMail(to, null, dear, content);
	}

}
