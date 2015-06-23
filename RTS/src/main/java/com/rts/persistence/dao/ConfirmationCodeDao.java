package com.rts.persistence.dao;

import com.rts.persistence.model.ConfirmationCode;

public interface ConfirmationCodeDao {
	ConfirmationCode getConfirmationCodeByCode(String code);
}
