package com.rts.persistence.dao;

import com.rts.persistence.model.ConfirmationCode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ConfirmationCodeDao {
	ConfirmationCode getConfirmationCodeByCode(String code);
	void saveConfirmationCodeByCode(ConfirmationCode cc);
}
