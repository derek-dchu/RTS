package com.mercury.rts.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@SuppressWarnings("serial")
@Entity
@Table(name="rts_transaction")
public class Transaction implements Serializable{
	private String status;
	private String ttime;
	private int tid;
	private int qt;
	private Ticket ticket;
	private User user;
	
	public Transaction() {}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tid")
	@SequenceGenerator(name ="seq_tid", sequenceName = "seq_tid",allocationSize=1,initialValue=1)
	@Column(nullable=false)
	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	@Column
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column
	public String getTtime() {
		return ttime;
	}

	public void setTtime(String ttime) {
		this.ttime = ttime;
	}

	@Column
	public int getQt() {
		return qt;
	}

	public void setQt(int qt) {
		this.qt = qt;
	}
	@ManyToOne
	@JoinColumn(name = "ticketid", insertable = true, updatable = false)
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	@ManyToOne
	@JoinColumn(name = "userid", insertable = true, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
