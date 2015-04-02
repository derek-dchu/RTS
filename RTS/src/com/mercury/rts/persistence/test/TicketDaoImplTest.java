package com.mercury.rts.persistence.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.model.Ticket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-persistence.xml")
@TransactionConfiguration(defaultRollback = false,
	transactionManager = "transactionManager")
@Transactional
public class TicketDaoImplTest {
	@Autowired
	TicketDaoImpl dao;

	@Test
	public void testSetTicketStatus() {
		Ticket t = dao.findById(1);
		assertNotNull(t);
		dao.setTicketStatus(t, 0);
		t = dao.findById(1);
		assertEquals(0, t.getEnable());
	}

	@Test
	public void testSaveTicket() {
		Ticket t = new Ticket();
		t.setEnable(1);
		dao.save(t);
		//dao.flush();
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
		assertEquals(1, list.size());
	}

}
