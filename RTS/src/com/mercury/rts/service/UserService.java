package com.mercury.rts.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
	@Qualifier("TransactionDaoImpl")
	private TransactionDaoImpl trdi;
	
	public String reg(User user){
		user.setEnable(0);
		user.setRole("ROLE_USER");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		user.setPassword(encoder.encodePassword(user.getPassword(),null));

		try{
			udi.saveUser(user);
			return null;
		}
		catch (HibernateException e) {
			return "can not find user";
		}
	}
	
	public List<Ticket> searchTicket(String dep, String des, String dtime, String atime) {
		Map<String, Object> condition = new HashMap<String, Object>(0);
		condition.put("dep", dep);
		condition.put("des", des);
		
		if (!dtime.isEmpty()) {
			condition.put("dtime", dtime);
		}
		
		if (!atime.isEmpty()) {
			condition.put("atime", atime);
		}
		System.out.println(condition.entrySet());
		
		try {
			return tdi.findAllByMulti(condition);
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String cancel(Transaction tx, int amount){
		try {
			Transaction transaction = trdi.findBy("tid", tx.getTid());
			Ticket ticket = tx.getTicket();
			ticket.setSold(ticket.getSold() - amount);
			ticket.setAvailable(ticket.getAvailable() + amount);
			tdi.saveTicket(ticket);
			transaction.setStatus("C");
			trdi.saveTransaction(transaction);
			return null;
		} catch (HibernateException e) {
			return "Cannot cancel ticket.";
		}
	}
	
	public List<Transaction> checkHistory(User user){
		return trdi.findAllBy("userid", user.getUserid());
	}
	
	public String saveCreditCard(User user, CreditCard creditCard){
		try {
			user.addCreditCard(creditCard);
			udi.saveUser(user);
			return null;
		} catch (HibernateException e) {
			return  "credit card save unsuccessfully";
		}
	}
	
	public String removeCreditCard(User user, CreditCard creditCard){
		try {
			user.removeCreditCard(creditCard);
			udi.saveUser(user);
			return null;
		} catch (HibernateException e) {
			return "not remove";
		}
	}
}

