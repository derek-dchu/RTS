package com.rts.resource;

import com.rts.persistence.model.Ticket;
import com.rts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/search")
public class SearchRest {
	@Autowired
	private UserService userService;
	
	@POST
	@Path("/searchticket")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Ticket> searchTicket(
			@FormParam("des") String des,
			@FormParam("dep") String dep,
			@FormParam("dtime") String dtime,
			@FormParam("atime") String atime) {
		return userService.searchTicket(dep, des, dtime, atime);
	}
	

}
