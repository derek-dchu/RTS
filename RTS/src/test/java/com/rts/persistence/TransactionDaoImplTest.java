package com.rts.persistence;

import com.rts.config.PersistenceConfig;
import com.rts.config.TestDataSourceConfig;
import com.rts.persistence.dao.TicketDao;
import com.rts.persistence.dao.TransactionDao;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.Transaction;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, PersistenceConfig.class},
		loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TransactionDaoImplTest {
	@Autowired
	TransactionDao txDao;
	@Autowired
	UserDao userDao;
	@Autowired
	TicketDao ticketDao;

	@Test
	public void testChangeStatus() {
		Transaction tx = txDao.getTransactionById(1);
		assertNotNull(tx);
		txDao.changeStatus(tx, "C");
		tx = txDao.getTransactionById(1);
		assertEquals("C", tx.getStatus());
	}

	@Test
	public void testSaveTransaction() {
		User u = userDao.getUserById(1);
		Ticket t = ticketDao.getTicketById(1);
		
		Transaction tx = new Transaction();
		tx.setUser(u);
		tx.setTicket(t);
		tx.setStatus("P");
		txDao.saveTransaction(tx);
		System.out.println("tid: " + tx.getTid());
	}

}