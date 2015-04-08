package com.mercury.rts.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.User;
import com.mercury.rts.service.SystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/main/resources/test/test-rts-persistence.xml",
		"classpath:/main/resources/test/test-rts-servlet.xml",
		"classpath:/main/resources/test/test-rts-mail.xml"
})
@Transactional
public class SystemServiceTest {
	@Autowired
	SystemService sysServ;
	@Autowired
	UserDaoImpl userDao;

	//@Test
	public void testSendEmail() {
		User user = new User();
		user.setEmail("mercurysystems000@gmail.com");
		String subject = "RTS Admin: Test";
		String content = "testing system service: send email";
		String response = sysServ.sendEmail(user, subject, content);
		assertNull(response);
	}

	@Test
	public void testChangeUserStatus() {
		User user = userDao.getUserById(1);
		String response = sysServ.changeUserStatus(user, 0);
		assertNull(response);
		assertEquals(0, user.getEnable());
	}

	@Test
	public void testListTicket() {
		List<Ticket> list = sysServ.listTicket();
		assertEquals(2, list.size());
	}

}