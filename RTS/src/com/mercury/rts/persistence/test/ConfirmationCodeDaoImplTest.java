package com.mercury.rts.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.persistence.dao.impl.ConfirmationCodeDaoImpl;
import com.mercury.rts.persistence.model.ConfirmationCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/main/resources/test/test-rts-persistence.xml")
@TransactionConfiguration(defaultRollback = false,
	transactionManager = "transactionManager")
@Transactional
public class ConfirmationCodeDaoImplTest {
	@Autowired
	ConfirmationCodeDaoImpl ccdi;

	@Test
	public void testGetConfirmationCodeByUserid() {
		ConfirmationCode cc = ccdi.getConfirmationCodeByUserid(1);
		assertNotNull(cc);
		assertEquals("123confirmationcode", cc.getCode());
	}

}
