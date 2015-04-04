package com.mercury.rts.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.rts.service.testService;

@Component
@Path("/kkk")
public class rrest {
	@Autowired
	private testService ts;
	
	@GET
	public void test(){
		System.out.println("servlet1");
		ts.testAdd();
		System.out.println("servlet2");
	}
}
