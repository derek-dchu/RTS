package com.mercury.rts.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.persistence.model.User;
import com.mercury.rts.service.SystemService;


@Component
@Path("/sys")
public class SystemRest {
	@Autowired
	private SystemService sysServ;

	@POST
	@Path("/reg")
	@Produces({MediaType.APPLICATION_JSON})
	public String reg(
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("email") String email,
			@FormParam("password") String password
	) {
		User u = new User();
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setEmail(email);
		u.setPassword(password);
		return sysServ.reg(u);
	}
	
	/*@GET
	@Path("/confirm")
	@Produces(MediaType.APPLICATION_JSON)
	public String confirm (@QueryParam("code") String code) {
		System.out.println(code);
		return sysServ.confirmUser(code);
	}*/
}