package com.mercury.rts.persistence.dao.impl;

import com.mercury.rts.common.db.GenericDaoImpl;
import com.mercury.rts.persistence.dao.TransactionDao;
import com.mercury.rts.persistence.model.Transaction;

public class TransactionDaoImpl extends GenericDaoImpl<Transaction,Integer> implements TransactionDao {
	
	public TransactionDaoImpl() {
		super(Transaction.class);
	}

	@Override
	public void changeStatus(Transaction transaction, String status) {
		transaction.setStatus(status);
		saveTransaction(transaction);
	}

	@Override
	public void saveTransaction(Transaction transaction) {
		save(transaction);
	}
}
