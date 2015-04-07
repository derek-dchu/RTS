package com.mercury.rts.resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.service.AdminService;

@Component
@Path("/admin")
@Transactional
public class AdminRest {
	@Autowired
	private AdminService as;
	
	@POST
	@Path("/addticket")
	@Produces({MediaType.APPLICATION_JSON})
	public String addTicket(@FormParam("tid") int tid,@FormParam("dep") String dep,@FormParam("des") String des,@FormParam("dtime") String dtime,
			@FormParam("atime") String atime,@FormParam("total") int total,@FormParam("sold") int sold,@FormParam("price") String price,@FormParam("status") int status) throws Exception{
		Ticket ticket = new Ticket();
		if(tid != 0 ) {
			ticket.setTicketid(tid);
			ticket.setEnable(status);
		}else {
			ticket.setEnable(0);
		}
		ticket.setDep(dep);
		ticket.setDes(des);
		ticket.setDtime(dtime);
		ticket.setAtime(atime);
		ticket.setTotal(total);
		ticket.setPrice(price);
		if(sold==0){
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
	@Transactional
	@Produces({MediaType.APPLICATION_JSON})
	public List<Ticket> listAllTicket(){
		return as.listAllTicket();
	}
	
}
