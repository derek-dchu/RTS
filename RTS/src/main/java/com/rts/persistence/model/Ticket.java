package com.rts.persistence.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@SuppressWarnings("serial")
@Entity
@Table(name="rts_ticket")
public class Ticket implements Serializable {
	private int ticketid;
	private int total;
	private int sold;
	private int available;
	private int enable;
	private String dep;
	private String des;
	private String dtime;
	private String atime;
	private String price;
	private Set<Transaction> transactions;
	
	public Ticket() {
		transactions = new HashSet<>();
	}
	
	@Id
//	@SequenceGenerator(name ="seq_ticketid", sequenceName = "seq_ticketid", allocationSize=1, initialValue=1000000000)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ticketid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false)
	public int getTicketid() {
		return ticketid;
	}

	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}

	@Column
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Column
	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	@Column
	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	@Column
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	@Column
	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	@Column
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Column
	public String getDtime() {
		return dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	@Column
	public String getAtime() {
		return atime;
	}

	public void setAtime(String atime) {
		this.atime = atime;
	}

	@Column
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@XmlTransient
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticketid")
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	
}
