package com.mercury.rts.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testc {

	@RequestMapping("/right")
	public String goMain(){
		return "right";
	}
}
