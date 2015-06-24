package com.rts.persistence.dao.impl;

import com.rts.common.db.GenericDaoImpl;
import com.rts.persistence.dao.ConfirmationCodeDao;
import com.rts.persistence.model.ConfirmationCode;
import org.hibernate.SessionFactory;

public class ConfirmationCodeDaoImpl extends
        GenericDaoImpl<ConfirmationCode, String> implements ConfirmationCodeDao {
	
	public ConfirmationCodeDaoImpl() {}
	public ConfirmationCodeDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory, ConfirmationCode.class);
	}

	public ConfirmationCode getConfirmationCodeByCode(String code) {
		return findById(code);
	}

    public void saveConfirmationCodeByCode(ConfirmationCode cc) {
        save(cc);
    }
}
