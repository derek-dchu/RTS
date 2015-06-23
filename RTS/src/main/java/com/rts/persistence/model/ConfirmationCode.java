package com.rts.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "rts_confirm")
public class ConfirmationCode implements Serializable {
	private int userid;
	private String code;
	
	@Column
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Id
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
