package com.mercury.rts.persistence.dao.impl;

import java.util.List;

import com.mercury.rts.common.db.GenericDaoImpl;
import com.mercury.rts.persistence.dao.TicketDao;
import com.mercury.rts.persistence.model.Ticket;

public class TicketDaoImpl extends GenericDaoImpl<Ticket, Integer> implements TicketDao {

	public TicketDaoImpl() {
		super(Ticket.class);
	}

	@Override
	public Ticket setTicketStatus(Ticket ticket, int status) {
		ticket.setEnable(status);
		saveTicket(ticket);
		return ticket;
	}

	@Override
	public void saveTicket(Ticket ticket) {
		save(ticket);
	}

	@Override
	public List<Ticket> listAllEnableTickets() {
		return findAllBy("enable", 1);
	}

	@Override
	public List<Ticket> listAllDisableTickets() {
		return findAllBy("enable", 0);
	}

	@Override
	public List<Ticket> listAllTickets() {
		return findAll();
	}

}
