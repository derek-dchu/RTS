package com.mercury.rts.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("TicketDaoImpl")
	private TicketDaoImpl tdi;
	
	@Autowired
	@Qualifier("UserDaoImpl")
	private UserDaoImpl udi;
	
	@Autowired
	@Qualifier("TransactionDaoImpl")
	private TransactionDaoImpl trdi;
	
	@Autowired
	private SystemService ss;
	
	public Transaction buyTicketEnqueue(String username,int tid,int quantity){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy*MM*dd*hh*mm");
		
		User u = udi.getUserByEmail(username);
		Ticket t = tdi.findById(tid);
		
		Transaction ts = new Transaction();
		ts.setQt(quantity);
		ts.setTtime(sdf.format(date));
		ts.setTicket(t);
		ts.setUser(u);
		ts.setStatus("p");
		
		TransactionQueue.add(ts);
		return ts;
	}
	
	public String buyTicketDequeue(Transaction trans){
		String s=null;
		int quantity=trans.getQt();
		Ticket t=trans.getTicket();
		int available=t.getAvailable();
		User u = trans.getUser();
		
		String sucSubject = "You has booked the ticket successfully";
		String failSubject = "Booking failed";
		String sucContent = " You have purchased "+quantity+" tickets from "+t.getDep()+" to "
				+t.getDes()+" on "+t.getDtime()+".\n Please arrive the station on time.  The ticket can be refunded 30 mins before the departure time. Thank you.";
		
		String failContent = " The ticket(s) you booked from "+t.getDep()+" to "+t.getDes()+" on "+t.getDtime()+" are sold out.  Please Choose to some other ones.  Thank you for your understanding, we apology for that";
		if(quantity<=available){
			trans.setStatus("b");
			t.setAvailable(available-quantity);
			t.setSold(t.getSold()+quantity);
			trdi.saveTransaction(trans);
			ss.sendEmail(u, sucSubject, sucContent);
		}else{
			ss.sendEmail(u, failSubject, failContent);
		}
		return s;
	}
}
