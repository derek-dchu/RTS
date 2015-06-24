package com.rts.service;

import com.rts.config.EmailConfig;
import com.rts.config.ServiceConfig;
import com.rts.config.TestDataSourceConfig;
import com.rts.config.TestPersistenceConfig;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.ConfirmationCode;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TestDataSourceConfig.class,
		TestPersistenceConfig.class,
		ServiceConfig.class,
		EmailConfig.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class SystemServiceTest {

	@Autowired
	SystemService sysServ;
	@Autowired
	UserDao userDao;

//	@Test
	public void testSendEmail() {
		User user = new User();
		user.setEmail("mercurysystems000@gmail.com");
		String subject = "RTS Admin: Test";
		String content = "testing system service: send email";
		String response = sysServ.sendEmail(null, user, subject, content);
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
	
	@Test
	public void testSendConfirmation() {
		User user = new User();
		user.setFirstName("derek");
		user.setEmail("mercurysystems000@gmail.com");
		String code = "123confirmationcode";
		String response = sysServ.sendConfirmation(user, code);
		assertNull(response);
	}
	
	@Test
	public void testConfirmUser() {
		ConfirmationCode cc = new ConfirmationCode();
		cc.setUserid(1);
		cc.setCode("123confirmationcode");
		User user = userDao.getUserById(cc.getUserid());
		assertEquals(1, user.getEnable());
	}

}