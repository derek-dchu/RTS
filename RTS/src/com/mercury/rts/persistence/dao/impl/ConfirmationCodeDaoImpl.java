package com.mercury.rts.persistence.dao.impl;

import com.mercury.rts.common.db.GenericDaoImpl;
import com.mercury.rts.persistence.dao.ConfirmationCodeDao;
import com.mercury.rts.persistence.model.ConfirmationCode;

public class ConfirmationCodeDaoImpl extends
		GenericDaoImpl<ConfirmationCode, String> implements ConfirmationCodeDao {
	
	public ConfirmationCodeDaoImpl() {
		super(ConfirmationCode.class);
	}

	@Override
	public ConfirmationCode getConfirmationCodeByCode(String code) {
		return findById(code);
	}

}
