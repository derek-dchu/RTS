package com.mercury.rts.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.model.Transaction;
import com.mercury.rts.persistence.model.User;
import com.mercury.rts.service.UserService;


@Component
@Path("/transaction")
@Transactional
public class UserTransactionRest {
	@Autowired
	private UserService us;

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Set<Transaction> getTransactions(@QueryParam("username") String username){
		return us.checkHistory(us.findUserByEmail(username));
	}
	
	@POST
	@Path("/cancel")
	@Produces({MediaType.APPLICATION_JSON})
	public String cancelTicket(@FormParam("trid") int trid,@FormParam("amount") int amount){
		System.out.println("cancel");
		return us.cancel(trid, amount);
	}
}
