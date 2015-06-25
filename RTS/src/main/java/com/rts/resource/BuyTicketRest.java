package com.rts.resource;

import com.rts.service.BuyTicket;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/buy")
public class BuyTicketRest {
	@Autowired
	private BuyTicket bt;

	@GET
	public void buyTicket(
			@QueryParam("tid") int tid,
			@QueryParam("username") String username,
			@QueryParam("qt") int qt) {
		bt.buyTicketEnqueue(username, tid, qt);
	}
}
