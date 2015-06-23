package com.rts.service;

import com.rts.persistence.dao.impl.TicketDaoImpl;
import com.rts.persistence.dao.impl.TransactionDaoImpl;
import com.rts.persistence.dao.impl.UserDaoImpl;
import com.rts.persistence.model.CreditCard;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.Transaction;
import com.rts.persistence.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);
	
	@Autowired
	private UserDaoImpl udi;
	
	@Autowired
	private TicketDaoImpl tdi;
	
	@Autowired
	private TransactionDaoImpl trdi;
	
	public List<Ticket> searchTicket(String dep, String des, String dtime, String atime) {
		Map<String, Object> condition = new HashMap<String, Object>(0);
		condition.put("enable", 1);
		condition.put("dep", dep);
		condition.put("des", des);
		
		if (!dtime.isEmpty()) {
			condition.put("dtime", dtime);
		}
		
		if (!atime.isEmpty()) {
			condition.put("atime", atime);
		}
		
		try {
			logger.debug(String.format("Search for tickets: %s", condition.entrySet()));
			return tdi.findAllByMulti(condition);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public String cancel(int tx, int amount) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		
		try {
			Transaction transaction = trdi.findBy("tid", tx);
			Ticket ticket = transaction.getTicket();
			
			if(amount == transaction.getQt()){
				ticket.setSold(ticket.getSold() - amount);
				ticket.setAvailable(ticket.getAvailable() + amount);
				tdi.saveTicket(ticket);
				transaction.setStatus("c");
				transaction.setTtime(sdf.format(date));
				trdi.saveTransaction(transaction);
			}else if(amount > transaction.getQt()){
				
			}else{
				Transaction t = new Transaction();
				t.setQt(amount);
				t.setStatus("c");
				ticket.setSold(ticket.getSold() - amount);
				ticket.setAvailable(ticket.getAvailable() + amount);
				t.setTicket(ticket);
				t.setUser(transaction.getUser());
				t.setTtime(sdf.format(date));
				tdi.saveTicket(ticket);
				trdi.saveTransaction(t);
			}
			return null;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return "Cannot cancel ticket.";
		}
	}
	
	public Set<Transaction> checkHistory(User user){
		Hibernate.initialize(user.getTransactions());
		return user.getTransactions();
	}
	
	public User findUserByEmail(String username){
		return udi.getUserByEmail(username);
	}
	
	public String saveCreditCard(User user, CreditCard creditCard){
		try {
			user.addCreditCard(creditCard);
			udi.saveUser(user);
			return null;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return  "credit card save unsuccessfully";
		}
	}
	
	public String removeCreditCard(User user, CreditCard creditCard){
		try {
			user.removeCreditCard(creditCard);
			udi.saveUser(user);
			return null;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return "not remove";
		}
	}
	
}

