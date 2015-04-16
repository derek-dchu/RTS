package com.mercury.rts.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.persistence.model.CreditCard;
import com.mercury.rts.persistence.model.User;
import com.mercury.rts.service.SystemService;


@Component
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
		cc.setCid(cvc);
		return sysServ.reg(u, cc);
	}
}
