package com.rts.persistence.dao;

import com.rts.persistence.model.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TransactionDao {
	Transaction getTransactionById(int id);
	void changeStatus(Transaction transaction, String status);
	void saveTransaction(Transaction transaction);
}
