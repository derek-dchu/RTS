package com.rts.service.test;

import com.rts.persistence.dao.impl.UserDaoImpl;
import com.rts.persistence.model.ConfirmationCode;
import com.rts.persistence.model.Ticket;
import com.rts.service.SystemService;
import com.rts.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
	/*public void testSendEmail() {
		User user = new User();
		user.setEmail("mercurysystems000@gmail.com");
		String subject = "RTS Admin: Test";
		String content = "testing system service: send email";
		String response = sysServ.sendEmail(user, subject, content);
		assertNull(response);
	}*/

	//@Test
	public void testChangeUserStatus() {
		User user = userDao.getUserById(1);
		String response = sysServ.changeUserStatus(user, 0);
		assertNull(response);
		assertEquals(0, user.getEnable());
	}

	//@Test
	public void testListTicket() {
		List<Ticket> list = sysServ.listTicket();
		assertEquals(2, list.size());
	}
	
	@Test
	public void testSendConfirmation() {
		User user = new User();
		user.setFirstName("derek");
		user.setEmail("mercurysystems000@gmail.com");
		String code = "123confirmationcode";
		String response = sysServ.sendConfirmation(user, code);
		assertNull(response);
	}
	
	//@Test
	public void testConfirmUser() {
		ConfirmationCode cc = new ConfirmationCode();
		cc.setUserid(1);
		cc.setCode("123confirmationcode");
		User user = userDao.getUserById(cc.getUserid());
		assertEquals(1, user.getEnable());
	}

}