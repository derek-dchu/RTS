package com.mercury.rts.persistence.dao;

import java.util.List;

import com.mercury.rts.persistence.model.Ticket;

public interface TicketDao{
	public Ticket setTicketStatus(Ticket ticket,int status);
	public void saveTicket(Ticket ticket);
	public List<Ticket> listAllEnableTickets();
	public List<Ticket> listAllDisableTickets();
}
