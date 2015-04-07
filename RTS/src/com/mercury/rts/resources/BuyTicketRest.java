package com.mercury.rts.resources;

import java.util.LinkedList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.service.BuyTicket;
import com.mercury.rts.util.TransactionQueue;

@Component
@Path("/buy")
public class BuyTicketRest {
	@Autowired
	private BuyTicket bt;

	@GET
	public void buyticke(@QueryParam("tid") int tid,@QueryParam("username") String username, @QueryParam("qt") int qt){
		LinkedList<Transaction> q=TransactionQueue.getTransactionQueue();
		bt.buyTicketEnqueue(username, tid, 20);
	}
}
