package com.mercury.rts.persistence.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "rts_user")
public class User implements Serializable {
	private int userid;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int enable;
	private String role;
	
	private Set<CreditCard> creditCards;
	private Set<Transaction> transactions;
	
	public User() {}

	@Id
	@SequenceGenerator(name = "seq_userid", sequenceName = "seq_userid", allocationSize = 1, initialValue = 1000000000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_userid")
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Column
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
	@Column
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToMany(mappedBy = "user")
	public Set<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Set<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
	
	public void addCreditCard(CreditCard creditCard) {
		creditCards.add(creditCard);
	}
	
	public void removeCreditCard(CreditCard creditCard) {
		creditCards.remove(creditCard);
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
}
