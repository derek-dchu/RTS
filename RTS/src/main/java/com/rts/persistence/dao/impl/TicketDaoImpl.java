package com.rts.persistence.dao.impl;

import com.rts.common.db.GenericDaoImpl;
import com.rts.persistence.dao.TicketDao;
import com.rts.persistence.model.Ticket;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

public class TicketDaoImpl extends GenericDaoImpl<Ticket, Integer> implements TicketDao {

	public TicketDaoImpl() {}
	public TicketDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory, Ticket.class);
	}

    public Ticket getTicketById(int id) {
        return findById(id);
    }

	public Ticket setTicketStatus(Ticket ticket, int status) {
		ticket.setEnable(status);
		saveTicket(ticket);
		return ticket;
	}

	public void saveTicket(Ticket ticket) {
		save(ticket);
	}

	public List<Ticket> listAllEnableTickets() {
		return findAllBy("enable", 1);
	}

	public List<Ticket> listAllDisableTickets() {
		return findAllBy("enable", 0);
	}

	public List<Ticket> listAllTickets() {
		return findAll();
	}

	public List<Ticket> listAllTicketsUnderCondition(Map<String, Object> condition) {
		return findAllByMulti(condition);
	}
}
