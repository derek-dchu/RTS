package com.mercury.rts.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	
	public List<Ticket> searchTicket(String dep, String des, String time) {
		List<String> property = Arrays.asList(new String[]{"dep", "des", "time"});
		List<String> value = Arrays.asList(new String[]{dep, des, time});
		
		return tdi.findAllByMulti(property, value);
	}
	
	public String cancel(int tx, int amount){
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
			return "Cannot cancel ticket.";
		}
	}
	
	public Set<Transaction> checkHistory(User user){
		return user.getTransactions();
	}
	
	public User findUserByEmail(String username){
		return udi.getUserByEmail(username);
	}
	
	public String saveCreditCard(User user,CreditCard creditCard){
		try {
			user.addCreditCard(creditCard);
			udi.saveUser(user);
			return null;
		} catch (HibernateException e) {
			return  "credit card save unsuccessfully";
		}
	}
	
	public String removeCreditCard(User user,CreditCard creditCard){
		try {
			user.removeCreditCard(creditCard);
			udi.saveUser(user);
			return null;
		} catch (HibernateException e) {
			return "not remove";
		}
	}
}

