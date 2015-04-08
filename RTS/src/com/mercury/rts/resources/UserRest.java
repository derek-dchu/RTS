package com.mercury.rts.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.persistence.model.User;
import com.mercury.rts.service.UserService;


@Component
@Path("/user")
public class UserRest {
	@Autowired
	private UserService us;

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
		return us.reg(u);
	}
}
