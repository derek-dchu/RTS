package com.mercury.rts.resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.service.UserService;

@Component
@Path("/search")
public class SearchRest {
	@Autowired
	private UserService userService;
	
	@POST
	@Path("/searchticket")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Ticket> Searchticket(
			@FormParam("des") String des,
			@FormParam("dep") String dep,
			@FormParam("dtime") String dtime,
			@FormParam("atime") String atime) {
		return userService.searchTicket(dep, des, dtime, atime);
	}
	

}
