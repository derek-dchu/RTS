package com.mercury.rts.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="rts_transaction")
public class Transaction implements Serializable{
	private String status,ttime;
	private int tid,userid,qt;
	private Ticket ticket;
	
	public Transaction(){}
	public Transaction(String status, String ttime, int tid, int userid,
			int qt, Ticket ticket) {
		this.status = status;
		this.ttime = ttime;
		this.tid = tid;
		this.userid = userid;
		this.qt = qt;
		this.ticket = ticket;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_tid")
	@SequenceGenerator(name ="seq_tid",sequenceName="seq_tid",allocationSize=1,initialValue=1)
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
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Column
	public int getQt() {
		return qt;
	}

	public void setQt(int qt) {
		this.qt = qt;
	}

	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=Ticket.class)
	@JoinColumn(name="ticketid",insertable=false,updatable=false)
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	
	
	
}
