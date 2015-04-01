package com.mercury.rts.persistence.test;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.persistence.model.User;
import com.mercury.rts.persistence.util.HibernateUtil;

public class UserTransactionTicketRelationTest {
	Session session;
	org.hibernate.Transaction tx;

	@Before
	public void setUp() throws Exception {
		session = HibernateUtil.currentSession();
		tx = session.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		tx.rollback();
		HibernateUtil.closeSession();
	}

	@Test
	public void test() {
		User user = new User();
		user.setEmail("1@1.com");
		user.setEnable(1);
		user.setFirstName("a");
		user.setLastName("b");
		user.setPassword("123");
		user.setRole("USER");
		session.save(user);
		
		Transaction rtsTx = new Transaction();
		rtsTx.setQt(1);
		rtsTx.setStatus("P");
		rtsTx.setTtime("1990");
		
		Ticket ticket = new Ticket();
		session.save(ticket);
		
		ticket.addTransaction(rtsTx);
		user.addTransaction(rtsTx);
		session.flush();
	}

}
