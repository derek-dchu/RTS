package com.rts.timer;

import com.rts.persistence.model.Transaction;
import com.rts.service.BuyTicket;
import com.rts.util.TransactionQueue;
import org.springframework.beans.factory.annotation.Autowired;


public class RunMeTask {
	@Autowired
	private BuyTicket bt;
	
	
	public void printMe() {
		System.out.println("Spring 3 + Quartz 1.8.6");
		while(TransactionQueue.size()>0){
			Transaction trans=TransactionQueue.remove();
			String result=bt.buyTicketDequeue(trans);
			System.out.println(TransactionQueue.size()+result);
		}
	}
}
