package com.rts.mail;

import com.rts.persistence.model.Ticket;

public class BookContent {
	public Ticket ticket;
	public int quantity;
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
