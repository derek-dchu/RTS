package com.rts.persistence;

import com.rts.config.TestDataSourceConfig;
import com.rts.config.TestPersistenceConfig;
import com.rts.persistence.dao.TicketDao;
import com.rts.persistence.model.Ticket;
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
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, TestPersistenceConfig.class},
		loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TicketDaoImplTest {
	@Autowired
	TicketDao dao;

	@Test
	public void testSetTicketStatus() {
		Ticket t = dao.getTicketById(1);
		assertNotNull(t);
		dao.setTicketStatus(t, 0);
		t = dao.getTicketById(1);
		assertEquals(0, t.getEnable());
	}

	@Test
	public void testSaveTicket() {
		Ticket t = new Ticket();
		t.setEnable(1);
		dao.saveTicket(t);
		System.out.println("ticketid: " + t.getTicketid());
	}

	@Test
	public void testListAllEnableTickets() {
		List<Ticket> list = dao.listAllEnableTickets();
		assertEquals(2, list.size());
	}

	@Test
	public void testListAllDisableTickets() {
		List<Ticket> list = dao.listAllDisableTickets();
		assertEquals(0, list.size());
	}

}