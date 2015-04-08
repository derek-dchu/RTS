package com.mercury.rts.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/main/resources/test/test-rts-persistence.xml")
@TransactionConfiguration(defaultRollback = false,
	transactionManager = "transactionManager")
@Transactional
public class UserDaoImplTest {
	@Autowired
	private UserDaoImpl udi;
	
	@Test
	public void testGetUserById() {
		User u1 = udi.getUserById(1);
		assertNotNull(u1);
		assertEquals(1, u1.getUserid());
		assertEquals("123", u1.getPassword());
		assertEquals("1@1.com", u1.getEmail());
	}

	@Test
	public void testGetUserByEmail() {
		User u2 = udi.getUserByEmail("2@2.com");
		assertNotNull(u2);
		assertEquals(2, u2.getUserid());
		assertEquals("234", u2.getPassword());
		assertEquals("2@2.com", u2.getEmail());
	}

	@Test
	public void testSetUserStatus() {
		User u = udi.getUserByEmail("1@1.com");
		udi.setUserStatus(u, 0);
		u = udi.getUserByEmail("1@1.com");
		assertEquals(0, u.getEnable());
	}

	@Test
	public void testSaveUser() {
		User u = new User();
		u.setPassword("456");
		u.setEmail("3@3.com");
		u.setEnable(1);
		udi.save(u);
		//dao.flush();
		System.out.println("userid: " + u.getUserid());
		
		u = udi.getUserByEmail("3@3.com");
		assertEquals("456", u.getPassword());
	}

	@Test
	public void testListAllUsers() {
		List<User> list = udi.listAllUsers();
		assertEquals(2, list.size());
		assertEquals("1@1.com", list.get(0).getEmail());
		assertEquals("234", list.get(1).getPassword());
	}

}