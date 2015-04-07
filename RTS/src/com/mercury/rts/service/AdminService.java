package com.mercury.rts.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.User;

@Service
@Transactional
public class AdminService {
	@Autowired
	@Qualifier("TicketDaoImpl")
	private TicketDaoImpl tdi;
	
	@Autowired
	@Qualifier("UserDaoImpl")
	private UserDaoImpl udi;
	
	public String changeUserStatus(User user,int status) throws Exception{
		try {
			user.setEnable(status);
			udi.saveUser(user);
			return null;
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	public String addOrUpdateTicket(Ticket ticket) throws Exception{
		try {
			tdi.saveTicket(ticket);
			return null;
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	public List<Ticket> listAllTicket(){
		return tdi.listAllTickets();
	}
}
