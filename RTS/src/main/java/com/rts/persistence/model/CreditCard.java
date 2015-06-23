package com.rts.persistence.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@SuppressWarnings("serial")
@Entity
@Table(name="rts_credit")
public class CreditCard implements Serializable {
	private long cnum;
	private int cvc;
	private int cdate;
	private User user;
	
	public CreditCard() {}

	@Id
	@Column(name="cnum")
	public long getCnum() {
		return cnum;
	}

	public void setCnum(long cnum) {
		this.cnum = cnum;
	}

	@Column(name="cvc")
	public int getCvc() {
		return cvc;
	}

	public void setCvc(int cid) {
		this.cvc = cid;
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
