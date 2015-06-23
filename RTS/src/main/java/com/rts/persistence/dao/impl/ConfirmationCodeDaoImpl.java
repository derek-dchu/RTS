package com.rts.persistence.dao.impl;

import com.rts.common.db.GenericDaoImpl;
import com.rts.persistence.dao.ConfirmationCodeDao;
import com.rts.persistence.model.ConfirmationCode;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ConfirmationCodeDaoImpl extends
        GenericDaoImpl<ConfirmationCode, String> implements ConfirmationCodeDao {
	
	public ConfirmationCodeDaoImpl() {}
	public ConfirmationCodeDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory, ConfirmationCode.class);
	}

	public ConfirmationCode getConfirmationCodeByCode(String code) {
		return findById(code);
	}

}
