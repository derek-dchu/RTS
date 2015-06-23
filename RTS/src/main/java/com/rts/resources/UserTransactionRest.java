package com.rts.resources;

import com.rts.persistence.model.Transaction;
import com.rts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;


@Component
@Path("/transaction")
@Transactional
public class UserTransactionRest {
	@Autowired
	private UserService us;

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Set<Transaction> getTransactions(@QueryParam("username") String username) {
		return us.checkHistory(us.findUserByEmail(username));
	}
	
	@POST
	@Path("/cancel")
	@Produces({MediaType.APPLICATION_JSON})
	public String cancelTicket(@FormParam("trid") int trid, @FormParam("amount") int amount) {
		System.out.println("cancel");
		return us.cancel(trid, amount);
	}
}
