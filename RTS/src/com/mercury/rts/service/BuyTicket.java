package com.mercury.rts.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.mail.BookContent;
import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.dao.impl.TransactionDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.persistence.model.User;
import com.mercury.rts.util.TransactionQueue;

@Service
@Transactional
public class BuyTicket {

	@Autowired
	private TicketDaoImpl tdi;
	
	@Autowired
	private UserDaoImpl udi;
	
	@Autowired
	private TransactionDaoImpl trdi;
	
	@Autowired
	private SystemService ss;
	
	public Transaction buyTicketEnqueue(String username, int tid, int quantity){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
		
		User user = udi.getUserByEmail(username);
		Ticket ticket = tdi.findById(tid);
		
		Transaction tx = new Transaction();
		tx.setQt(quantity);
		tx.setTtime(sdf.format(date));
		tx.setTicket(ticket);
		tx.setUser(user);
		tx.setStatus("P");
		
		TransactionQueue.add(tx);
		return tx;
	}
	
	public String buyTicketDequeue(Transaction tx){
		String s = null;
		int quantity = tx.getQt();
		Ticket ticket = tdi.findById(tx.getTicket().getTicketid());
		int available = ticket.getAvailable();
		User user = tx.getUser();
		
		String successSubject = "You has booked the ticket successfully";
		String failSubject = "Booking failed";
		BookContent successContent = new BookContent();
		successContent.setQuantity(quantity);
		successContent.setTicket(ticket);
		BookContent failContent = new BookContent();
		failContent.setTicket(ticket);
		if(quantity <= available) {
			tx.setStatus("B");
			ticket.setAvailable(available-quantity);
			ticket.setSold(ticket.getSold()+quantity);
			tdi.saveTicket(ticket);
			trdi.saveTransaction(tx);
			ss.sendEmail("book-success", user, successSubject, successContent);
		} else {
			ss.sendEmail("book-fail", user, failSubject, failContent);
		}
		return s;
	}
}
