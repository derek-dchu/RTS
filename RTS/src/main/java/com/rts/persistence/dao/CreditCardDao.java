package com.rts.persistence.dao;

import com.rts.persistence.model.CreditCard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CreditCardDao {
	
	void saveCreditCard(CreditCard creditcard);
	void removeCreditCard(CreditCard creditcard);

}
