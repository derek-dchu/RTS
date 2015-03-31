package com.mercury.rts.persistence.model;

import java.io.Serializable;

import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
@Table(name="rts_credit")
public class CreditCard implements Serializable{
	private int cnum;
	private int cid;
	private String date;
	private int userid;
	
	public CreditCard() {}
	public  CreditCard(int cnum, int cid, String date, int userid ){
		this.cnum=cnum;
		this.cid=cid;
		this.date=date;
		this.userid=userid;
	}

	@Id
	@Column(name="cnum")
	public int getCnum() {
		return cnum;
	}

	public void setCnum(int cnum) {
		this.cnum = cnum;
	}

	@Column(name="cid")
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name="userid")
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
}
