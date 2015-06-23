package com.rts.persistence;

import com.rts.config.TestDataSourceConfig;
import com.rts.config.TestPersistenceConfig;
import com.rts.persistence.dao.ConfirmationCodeDao;
import com.rts.persistence.model.ConfirmationCode;
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
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, TestPersistenceConfig.class},
		loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ConfirmationCodeDaoImplTest {

	@Autowired
	ConfirmationCodeDao ccd;

	@Test
	public void testGetConfirmationCodeByUserid() {
		String code = "123confirmationcode";
		ConfirmationCode cc = ccd.getConfirmationCodeByCode(code);
		assertNotNull(cc);
		assertEquals(1, cc.getUserid());
	}

}
