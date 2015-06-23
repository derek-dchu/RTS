package com.rts.persistence.dao;

import com.rts.persistence.model.CreditCard;

public interface CreditCardDao {
	
	void saveCreditCard(CreditCard creditcard);
	void removeCreditCard(CreditCard creditcard);

}
