package com.mercury.rts.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class controller {
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/admin")
	public String goMain(){
		return "admin";
	}
	
	@RequestMapping("/login")
	public String goMain2(){
		return "login";
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
	public String  searchTicket(){
		return "searchticket";
	}
}

