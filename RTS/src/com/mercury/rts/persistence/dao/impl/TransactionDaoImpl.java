package com.mercury.rts.persistence.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mercury.rts.common.db.HibernateDao;
import com.mercury.rts.persistence.dao.TransactionDao;
import com.mercury.rts.persistence.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {
	@Autowired
	@Qualifier("transactionHibernateDao")
	private HibernateDao<Transaction, Integer> hd;
	
	@Override
	public void changeStatus(Transaction transaction, String status) {
		transaction.setStatus(status);
		saveTransaction(transaction);
	}

	@Override
	public void saveTransaction(Transaction transaction) {
		hd.save(transaction);
	}
}
