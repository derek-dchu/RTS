package com.rts.persistence.dao.impl;

import com.rts.common.db.GenericDaoImpl;
import com.rts.persistence.dao.TransactionDao;
import com.rts.persistence.model.Transaction;
import org.hibernate.SessionFactory;

public class TransactionDaoImpl extends GenericDaoImpl<Transaction,Integer> implements TransactionDao {
	
	public TransactionDaoImpl() {}
	public TransactionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory, Transaction.class);
	}

    public Transaction getTransactionById(int id) {
        return findById(id);
    }

	public void changeStatus(Transaction transaction, String status) {
		transaction.setStatus(status);
		saveTransaction(transaction);
	}

	public void saveTransaction(Transaction transaction) {
		save(transaction);
	}
}
