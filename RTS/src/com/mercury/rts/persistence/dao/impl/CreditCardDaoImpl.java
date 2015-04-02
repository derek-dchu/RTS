package com.mercury.rts.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.mercury.rts.common.db.GenericDaoImpl;
import com.mercury.rts.persistence.dao.CreditCardDao;
import com.mercury.rts.persistence.model.CreditCard;

@Repository
public class CreditCardDaoImpl extends GenericDaoImpl<CreditCard,Integer> implements CreditCardDao {
	
	public CreditCardDaoImpl() {
		super(CreditCard.class);
	}
	
	@Override
	public void saveCreditCard(CreditCard creditcard) {
		save(creditcard); 
	}

	@Override
	public void removeCreditCard(CreditCard creditcard) {
		delete(creditcard);
	}

}
