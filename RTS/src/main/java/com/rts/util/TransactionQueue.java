package com.rts.util;

import com.rts.persistence.model.Transaction;

import java.util.LinkedList;


public class TransactionQueue {
		private static LinkedList<Transaction> q = new LinkedList<Transaction>();
		private TransactionQueue() {}
		public static LinkedList<Transaction> getTransactionQueue(){
			return q;
		}
		
		
		public static synchronized boolean add(Transaction t){
			q.add(t);
			return true;
		}
		
		public static Transaction remove() {
			return q.remove();
		}
		
		public static int size() {
			return q.size();
		}
}
