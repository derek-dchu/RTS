package com.mercury.rts.persistence.dao;

import com.mercury.rts.persistence.model.ConfirmationCode;

public interface ConfirmationCodeDao {
	public ConfirmationCode getConfirmationCodeByUserid(int userid);
}
