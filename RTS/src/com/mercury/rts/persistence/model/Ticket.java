package com.mercury.rts.presistence.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="rts_ticket")
public class Ticket {
	private int ticketid,total,sold,available,enable;
	private String dep,des,dtime,atime,price;
	
	public Ticket(){}

	public Ticket(int ticketid, int total, int sold, int available, int enable,
			String dep, String des, String dtime, String atime, String price) {
		this.ticketid = ticketid;
		this.total = total;
		this.sold = sold;
		this.available = available;
		this.enable = enable;
		this.dep = dep;
		this.des = des;
		this.dtime = dtime;
		this.atime = atime;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_ticketid")
	@SequenceGenerator(name ="seq_ticketid",sequenceName="seq_ticketid",allocationSize=1,initialValue=1)
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
	
	
}
