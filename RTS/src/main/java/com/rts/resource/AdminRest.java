package com.rts.resource;

import com.rts.persistence.model.Ticket;
import com.rts.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/admin")
@Transactional
public class AdminRest {
	@Autowired
	private AdminService as;
	
	@POST
	@Path("/addticket")
	@Produces({MediaType.APPLICATION_JSON})
	public String addTicket(
			@FormParam("tid") int tid,
			@FormParam("dep") String dep,
			@FormParam("des") String des,
			@FormParam("dtime") String dtime,
			@FormParam("atime") String atime,
			@FormParam("total") int total,
			@FormParam("sold") int sold,
			@FormParam("price") String price,
			@FormParam("status") int status) throws Exception {
		Ticket ticket = new Ticket();
		if (tid != 0 ) {
			ticket.setTicketid(tid);
			ticket.setEnable(status);
		} else {
			ticket.setEnable(0);
		}
		ticket.setDep(dep);
		ticket.setDes(des);
		ticket.setDtime(dtime);
		ticket.setAtime(atime);
		ticket.setTotal(total);
		ticket.setPrice(price);
		if (tid==0) {
			ticket.setAvailable(total);
			ticket.setSold(0);
		}else {
			ticket.setAvailable(total-sold);
			ticket.setSold(sold);
		}


		return as.addOrUpdateTicket(ticket);
	}
	
	@GET
	@Path("/listticket")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Ticket> listAllTicket(){
		return as.listAllTicket();
	}
	
}
