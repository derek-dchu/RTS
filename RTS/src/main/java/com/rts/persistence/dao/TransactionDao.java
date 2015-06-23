package com.rts.persistence.dao;

import com.rts.persistence.model.Transaction;

public interface TransactionDao {
	Transaction getTransactionById(int id);
	void changeStatus(Transaction transaction, String status);
	void saveTransaction(Transaction transaction);
}
