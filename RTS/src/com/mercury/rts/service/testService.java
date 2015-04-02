package com.mercury.rts.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.common.db.GenericDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.User;

@Service
@Transactional
public class testService {
	@Autowired
	@Qualifier("testUserDao")
	private GenericDaoImpl<User, Integer> gd;
	
	public void testAdd(){
		User u = new User();
		u.setPassword("456");
		u.setEmail("3@3.com");
		u.setEnable(1);
		System.out.println(u.getEmail());
		gd.save(u);
		System.out.println("service");
	}
	
	
}
