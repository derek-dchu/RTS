package com.mercury.rts.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		String successContent = "You have purchased "+quantity+" tickets from "+ticket.getDep()+" to "
				+ticket.getDes()+" on "+ticket.getDtime()+".\n Please arrive the station on time. " +
				"The ticket can be refunded 30 mins before the departure time. Thank you.";
		String failContent = "The ticket(s) you booked from "+ticket.getDep()+" to "
				+ticket.getDes()+" on "+ticket.getDtime()+" are sold out. " +
				"Please Choose to some other ones.  Thank you for your understanding, we apology for that";
		if(quantity <= available) {
			tx.setStatus("B");
			ticket.setAvailable(available-quantity);
			ticket.setSold(ticket.getSold()+quantity);
			tdi.saveTicket(ticket);
			trdi.saveTransaction(tx);
			ss.sendEmail(user, successSubject, successContent);
		} else {
			ss.sendEmail(user, failSubject, failContent);
		}
		return s;
	}
}
