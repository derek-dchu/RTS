package com.rts.persistence.dao;

import java.util.List;

import com.rts.persistence.model.Ticket;

public interface TicketDao{
	Ticket getTicketById(int id);
	Ticket setTicketStatus(Ticket ticket,int status);
	void saveTicket(Ticket ticket);
	List<Ticket> listAllEnableTickets();
	List<Ticket> listAllDisableTickets();
	List<Ticket> listAllTickets();
}
