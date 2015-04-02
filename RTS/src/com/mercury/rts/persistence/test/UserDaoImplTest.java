package com.mercury.rts.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.User;
import com.mercury.rts.persistence.util.HibernateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-persistence.xml")
@TransactionConfiguration(defaultRollback = false,
	transactionManager = "transactionManager")
public class UserDaoImplTest {
	@Autowired
	private UserDaoImpl dao;
	
	@Test
	public void testGetUserById() {
		User u1 = dao.getUserById(1);
		assertNotNull(u1);
		assertEquals(1, u1.getUserid());
		assertEquals("123", u1.getPassword());
		assertEquals("1@1.com", u1.getEmail());
	}

	@Test
	public void testGetUserByEmail() {
		User u2 = dao.getUserByEmail("2@2.com");
		assertNotNull(u2);
		assertEquals(2, u2.getUserid());
		assertEquals("123", u2.getPassword());
		assertEquals("2@2.com", u2.getEmail());
	}

	@Test
	public void testSetUserStatus() {
		User u = dao.getUserByEmail("1@1.com");
		dao.setUserStatus(u, 0);
		u = dao.getUserByEmail("1@1.com");
		assertEquals(0, u.getEnable());
	}

	@Test
	public void testSaveUser() {
		User u = new User();
		u.setPassword("456");
		u.setEmail("3@3.com");
		u.setEnable(1);
		dao.save(u);
		//dao.flush();
		System.out.println("id: " + u.getUserid());
		
		Session session = dao.getAppSessionFactory().openSession();
		List<User> list = session.createQuery("from User").list();
		for (User user : list) {
			System.out.println(user.getPassword());
		}
		
		assertEquals(3, list.size());
		
		u = dao.getUserByEmail("3@3.com");
		assertEquals("456", u.getPassword());
	}

	@Test
	public void testListAllUsers() {
		List<User> list = dao.listAllUsers();
		assertEquals("1@1.com", list.get(0).getEmail());
		assertEquals("123", list.get(1).getPassword());
	}

}
