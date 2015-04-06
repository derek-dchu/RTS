package com.mercury.rts.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.mail.MailAppBean;
import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.User;

@Service
@Transactional
public class SystemService {
	private static Logger logger = Logger.getLogger(SystemService.class);
	
	@Autowired
	MailAppBean mailApp;
	@Autowired
	@Qualifier("UserDaoImpl")
	UserDaoImpl userDao;
	@Autowired
	@Qualifier("TicketDaoImpl")
	TicketDaoImpl ticketDao;
	
	public String sendEmail(User user, String subject, String content) {
		String username = String.format("%s %s", user.getFirstName(), user.getLastName());
		try {
			mailApp.sendMail(user.getEmail(), subject, username, content);
			return null;
		} catch (MailParseException e) {
			logger.error(e.getMessage());
			return "System Error: mail content is not valid.";
		} catch (MailException e) {
			logger.error(e.getMessage());
			return "System Error: mail cannot be sent.";
		}
	}
	
	public String changeUserStatus(User user, int status) {
		try {
			userDao.setUserStatus(user, status);
			return null;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return "System Error: user status cannot be updated.";
		}
	}
	
	public List<Ticket> listTicket() {
		try {
			return ticketDao.listAllEnableTickets();
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
