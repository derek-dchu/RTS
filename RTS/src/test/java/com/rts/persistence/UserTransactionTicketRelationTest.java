package com.rts.persistence;

import com.rts.config.TestDataSourceConfig;
import com.rts.config.TestPersistenceConfig;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.Transaction;
import com.rts.persistence.model.User;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, TestPersistenceConfig.class},
        loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class UserTransactionTicketRelationTest {

	@Autowired
	SessionFactory sessionFactory;

	@Test
	public void test() {
		User user = new User();
		user.setEmail("1@1.com");
		user.setEnable(1);
		user.setFirstName("a");
		user.setLastName("b");
		user.setPassword("123");
		user.setRole("USER");
		sessionFactory.getCurrentSession().save(user);
		
		Transaction rtsTx = new Transaction();
		rtsTx.setQt(1);
		rtsTx.setStatus("P");
		rtsTx.setTtime("1990");
		
		Ticket ticket = new Ticket();
		sessionFactory.getCurrentSession().save(ticket);
		
		ticket.addTransaction(rtsTx);
		user.addTransaction(rtsTx);
		sessionFactory.getCurrentSession().flush();
	}

}