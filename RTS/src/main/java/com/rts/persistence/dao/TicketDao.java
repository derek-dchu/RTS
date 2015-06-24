package com.rts.persistence.dao;

import com.rts.persistence.model.Ticket;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface TicketDao{
	Ticket getTicketById(int id);
	Ticket setTicketStatus(Ticket ticket,int status);
	void saveTicket(Ticket ticket);
	List<Ticket> listAllEnableTickets();
	List<Ticket> listAllDisableTickets();
	List<Ticket> listAllTickets();
	List<Ticket> listAllTicketsUnderCondition(Map<String, Object> condition);
}
