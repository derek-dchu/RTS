package com.rts.web.controller;

import com.rts.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.QueryParam;

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
	
	@RequestMapping("/user_transaction")
	public String goMain4(){
		return "user_transaction";
	}
	
	@RequestMapping("/sys/confirm")
	public String confirm(@QueryParam("code") String code) {
		System.out.println(code);
		System.out.println(sysServ.confirmUser(code));
		return "confirm";
	}
}

