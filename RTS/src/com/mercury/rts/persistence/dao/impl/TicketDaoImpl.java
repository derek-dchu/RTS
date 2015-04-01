package com.mercury.rts.persistence.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mercury.rts.common.db.HibernateDao;
import com.mercury.rts.persistence.dao.TicketDao;
import com.mercury.rts.persistence.model.Ticket;

public class TicketDaoImpl implements TicketDao {
	@Autowired
	@Qualifier("ticketHibernateDao")
	private HibernateDao<Ticket, Integer> hd;

	@Override
	public Ticket setTicketStatus(Ticket ticket, int status) {
		ticket.setEnable(status);
		saveTicket(ticket);
		return ticket;
	}

	@Override
	public void saveTicket(Ticket ticket) {
		hd.save(ticket);
	}

	@Override
	public List<Ticket> listAllEnableTickets() {
		return hd.findAllBy("enable", 1);
	}

	@Override
	public List<Ticket> listAllDisableTickets() {
		return hd.findAllBy("enable", 0);
	}

}
