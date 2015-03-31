package com.mercury.test;

import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.CSS;

import org.hibernate.*;

import com.mercury.rts.persistence.model.CreditCard;
import com.mercury.rts.persistence.model.User;
import com.mercury.util.HibernateUtil;



public class test {
	public static void main(String[] args) {
		Session session = HibernateUtil.currentSession();
		Transaction ts = session.beginTransaction();
		

		User user = new User();
		user.setEmail("123");
		user.setEnable(1);
		user.setFirstName("ye");
		user.setLastName("yiren");
		user.setPassword("123");
		user.setRole("haha");
		session.save(user);
		
		CreditCard cc = new CreditCard(123,345,"yi",user);
		user.addCreditCard(cc);
		session.save(user);
		ts.commit();
	}

}
