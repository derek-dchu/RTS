package com.mercury.rts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.dao.impl.TransactionDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.CreditCard;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.persistence.model.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	@Qualifier("UserDaoImpl")
	private UserDaoImpl udi;
	
	@Autowired
	@Qualifier("TicketDaoImpl")
	private TicketDaoImpl tdi;
	
	@Autowired
	@Qualifier("TicketDaoImpl")
	private TransactionDaoImpl trdi;
	
	public String reg(User user){
		try{
			udi.saveUser(user);
		}
		catch (Exception e) {
			// TODO: handle exception
			return "can not find user";
		}
		return "Succeed";
	}
	
	public List<Ticket> searchTicket(String dep,String des,String time){
		List<String> property = new ArrayList<String>(Arrays.asList(new String[]{"dep", "des", "time"}));
		List<String> value = new ArrayList<String>(Arrays.asList(new String[]{dep, des, time}));
		
		return tdi.findAllByMulti(property, value);
	}
	
/*	public Transaction purchase(User user, Ticket ticket, int amount){
		
	}*/
	
	public String cancel(Transaction tr, int amount){
		Transaction transaction = trdi.findBy("tid", tr.getTid());
		Ticket ticket = tdi.findBy("ticketid", tr.getTicket().getTicketid());
		ticket.setSold(ticket.getSold()-amount);
		ticket.setAvailable(ticket.getAvailable()+amount);
		tdi.saveTicket(ticket);
		transaction.setStatus("c");
		trdi.saveTransaction(transaction);
		return "ticket has been returned";
	}
	
	public List<Transaction> checkHistory(User user){
		return trdi.findAllBy("userid", user.getUserid());
	}
	public String saveCreditCard(User user,CreditCard creditCard){
		try {
			user.addCreditCard(creditCard);
		} catch (Exception e) {
			return  "credit card save unsuccessfully";
		}
		return "credit card save successfully";
		
	}
	public String removeCreditCard(User user,CreditCard creditCard){
		try {
			user.removeCreditCard(creditCard);
		} catch (Exception e) {
			// TODO: handle exception
			return "not remove";
		}
		return "removed";
	}
}

