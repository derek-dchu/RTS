package com.mercury.rts.persistence.dao;

import com.mercury.rts.persistence.model.Transaction;

public interface TransactionDao {
	public void changeStatus(Transaction transaction, String status);
	public void saveTransaction(Transaction transaction);
}
