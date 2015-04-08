package com.mercury.rts.persistence.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.CreditCardDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.CreditCard;
import com.mercury.rts.persistence.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/main/resources/test/test-rts-persistence.xml")
@TransactionConfiguration(defaultRollback = false,
	transactionManager = "transactionManager")
@Transactional
public class CreditCardDaoImplTest {
	@Autowired
	private CreditCardDaoImpl dao;
	@Autowired
	private UserDaoImpl userDao;
	
	@Test
	public void testSaveCreditCard() {
		
		CreditCard cc = new CreditCard();
		cc.setCnum(333);
		cc.setCid(333);
		cc.setCdate(3333);
		
		User u = userDao.getUserById(1);
		u.addCreditCard(cc);
		//dao.saveCreditCard(cc);
		userDao.saveUser(u);
	}

	@Test
	public void testRemoveCreditCard() {
		CreditCard cc = new CreditCard();
		cc.setCnum(333);
		cc.setCid(333);
		cc.setCdate(3333);
		
		dao.delete(cc);
	}

}