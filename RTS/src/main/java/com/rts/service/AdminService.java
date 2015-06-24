package com.rts.service;


import com.rts.persistence.dao.TicketDao;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {

	@Autowired
	private TicketDao tdi;
	
	@Autowired
	private UserDao udi;
	
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
