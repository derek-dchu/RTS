package com.mercury.rts.persistence.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mercury.rts.persistence.dao.CreditCardDao;
import com.mercury.rts.persistence.db.HibernateDao;
import com.mercury.rts.persistence.model.CreditCard;

public class CreditCardDaoImpl implements CreditCardDao {
	
	@Autowired
	@Qualifier("HibernateDao")
	private HibernateDao<CreditCard, Integer> hd;
	@Override
	public void saveCreditCard(CreditCard creditcard) {
		// TODO Auto-generated method stub
		hd.save(creditcard); 
	}

	@Override
	public void removeCreditCard(CreditCard creditcard) {
		// TODO Auto-generated method stub
		hd.delete(creditcard);

	}

}
