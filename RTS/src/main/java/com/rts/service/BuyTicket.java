package com.rts.service;

import com.rts.mail.BookContent;
import com.rts.persistence.dao.TicketDao;
import com.rts.persistence.dao.TransactionDao;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.Transaction;
import com.rts.persistence.model.User;
import com.rts.util.TransactionQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class BuyTicket {

	@Autowired
	private TicketDao tdi;
	
	@Autowired
	private UserDao udi;
	
	@Autowired
	private TransactionDao trdi;
	
	@Autowired
	private SystemService ss;
	
	public Transaction buyTicketEnqueue(String username, int tid, int quantity){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		User user = udi.getUserByEmail(username);
		Ticket ticket = tdi.getTicketById(tid);
		
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
		Ticket ticket = tdi.getTicketById(tx.getTicket().getTicketid());
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
			tx.setStatus("b");
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
