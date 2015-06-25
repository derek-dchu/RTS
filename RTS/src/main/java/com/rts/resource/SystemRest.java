package com.rts.resource;

import com.rts.persistence.model.CreditCard;
import com.rts.persistence.model.User;
import com.rts.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/sys")
public class SystemRest {
	@Autowired
	private SystemService sysServ;

	@POST
	@Path("/reg")
	public String reg(
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("cnum") long cnum,
			@FormParam("cdate") int cdate,
			@FormParam("cvc") int cvc) {
		User u = new User();
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setEmail(email);
		u.setPassword(password);
		
		CreditCard cc = new CreditCard();
		cc.setCnum(cnum);
		cc.setCdate(cdate);
		cc.setCvc(cvc);
		return sysServ.reg(u, cc);
	}
}
