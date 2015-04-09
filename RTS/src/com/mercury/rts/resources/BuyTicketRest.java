package com.mercury.rts.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.service.BuyTicket;

@Component
@Path("/buy")
public class BuyTicketRest {
	@Autowired
	private BuyTicket bt;

	@GET
	public void buyticke(
			@QueryParam("tid") int tid, 
			@QueryParam("username") String username, 
			@QueryParam("qt") int qt) {
		bt.buyTicketEnqueue(username, tid, qt);
	}
}
