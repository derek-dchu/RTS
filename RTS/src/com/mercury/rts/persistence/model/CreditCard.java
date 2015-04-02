package com.mercury.rts.persistence.model;

import java.io.Serializable;

import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
@Table(name="rts_credit")
public class CreditCard implements Serializable {
	private int cnum;
	private int cid;
	private int cdate;
	private User user;
	
	public CreditCard() {}

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

	@Column(name="cdate")
	public int getCdate() {
		return cdate;
	}

	public void setCdate(int cdate) {
		this.cdate = cdate;
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
