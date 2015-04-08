package com.mercury.rts.web.controller;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mercury.rts.service.SystemService;

@Controller
public class controller {
	@Autowired
	private SystemService sysServ;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/reg")
	public String reg() {
		return "reg";
	}

	@RequestMapping("/admin")
	public String goMain(){
		return "admin";
	}
	
	@RequestMapping("/user")
	public String goMain3(){
		return "user";
	}
	
	@RequestMapping("/user_transaction")
	public String goMain4(){
		return "user_transaction";
	}
	
	@RequestMapping("/searchticket")
	public String searchTicket(){
		return "searchticket";
	}
	
	@RequestMapping("/sys/confirm")
	public String confirm(@QueryParam("code") String code) {
		System.out.println(code);
		System.out.println(sysServ.confirmUser(code));
		return "confirm";
	}
}

