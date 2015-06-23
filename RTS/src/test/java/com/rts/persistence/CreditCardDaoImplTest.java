package com.rts.persistence;

import com.rts.config.TestDataSourceConfig;
import com.rts.config.TestPersistenceConfig;
import com.rts.persistence.dao.CreditCardDao;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.CreditCard;
import com.rts.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, TestPersistenceConfig.class},
		loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CreditCardDaoImplTest {
	@Autowired
	private CreditCardDao dao;
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testSaveCreditCard() {
		
		CreditCard cc = new CreditCard();
		cc.setCnum(333);
		cc.setCvc(333);
		cc.setCdate(3333);
		
		User u = userDao.getUserById(1);
		u.addCreditCard(cc);
		//dao.saveCreditCard(cc);
		userDao.saveUser(u);

        u.getCreditCards().forEach(c -> {
            if (c.getCnum() == 333) {
                assertEquals(333, c.getCvc());
                assertEquals(3333, c.getCdate());
            }
        });
	}

	@Test
	public void testRemoveCreditCard() {
		CreditCard cc = new CreditCard();
		cc.setCnum(100);
		dao.removeCreditCard(cc);

        User u = userDao.getUserById(1);
        assertEquals(2, u.getCreditCards().size());
	}

}