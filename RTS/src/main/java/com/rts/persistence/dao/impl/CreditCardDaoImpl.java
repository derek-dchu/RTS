package com.rts.persistence.dao.impl;

import com.rts.common.db.GenericDaoImpl;
import com.rts.persistence.dao.CreditCardDao;
import com.rts.persistence.model.CreditCard;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CreditCardDaoImpl extends GenericDaoImpl<CreditCard,Integer> implements CreditCardDao {

	public CreditCardDaoImpl() {}
	public CreditCardDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, CreditCard.class);
	}

	public void saveCreditCard(CreditCard creditcard) {
		save(creditcard); 
	}

	public void removeCreditCard(CreditCard creditcard) {
		delete(creditcard);
	}

}
