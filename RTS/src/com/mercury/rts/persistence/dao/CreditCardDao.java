package com.mercury.rts.persistence.dao;

import com.mercury.rts.persistence.model.CreditCard;

public interface CreditCardDao {
	
	void saveCreditCard(CreditCard creditcard);
	void removeCreditCard(CreditCard creditcard);

}
