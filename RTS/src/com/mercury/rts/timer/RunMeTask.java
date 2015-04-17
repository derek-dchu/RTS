package com.mercury.rts.timer;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.service.BuyTicket;
import com.mercury.rts.util.TransactionQueue;


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
