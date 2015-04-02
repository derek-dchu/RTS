package com.mercury.rts.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.dao.impl.TransactionDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.persistence.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-persistence.xml")
@TransactionConfiguration(defaultRollback = false,
	transactionManager = "transactionManager")
@Transactional
public class TransactionDaoImplTest {
	@Autowired
	TransactionDaoImpl dao;
	@Autowired
	UserDaoImpl userDao;
	@Autowired
	TicketDaoImpl ticketDao;

	@Test
	public void testChangeStatus() {
		Transaction tx = dao.findById(1);
		assertNotNull(tx);
		dao.changeStatus(tx, "C");
		tx = dao.findById(1);
		assertEquals("C", tx.getStatus());
	}

	@Test
	public void testSaveTransaction() {
		User u = userDao.getUserById(1);
		Ticket t = ticketDao.findById(1);
		
		Transaction tx = new Transaction();
		tx.setUser(u);
		tx.setTicket(t);
		tx.setStatus("P");
		dao.save(tx);
		//dao.flush();
		System.out.println("tid: " + tx.getTid());
	}

}
