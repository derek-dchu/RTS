package com.rts.service;

import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService{
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserDao udi;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;  
		try {
			User u = udi.getUserByEmail(username);
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(u.getRole()));
			user = (UserDetails) new org.springframework.security.core.userdetails.User(
					u.getEmail(),
					u.getPassword(),
					(u.getEnable()==1),
					true,
					true,
					true,
					authorities 
			);
		System.out.println(u.getEmail()+" "+u.getPassword()+" "+u.getRole());
		} catch (Exception e) {
			logger.error("Error in retrieving user" + e.getMessage());
			e.printStackTrace();
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		return user;
	}		  
}
