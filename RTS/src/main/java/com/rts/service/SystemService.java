package com.rts.service;

import com.rts.mail.MailApp;
import com.rts.mail.RegContent;
import com.rts.persistence.dao.ConfirmationCodeDao;
import com.rts.persistence.dao.TicketDao;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.ConfirmationCode;
import com.rts.persistence.model.CreditCard;
import com.rts.persistence.model.Ticket;
import com.rts.persistence.model.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SystemService {
	private static Logger logger = Logger.getLogger(SystemService.class);
	
	@Autowired
	private MailApp mailApp;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private ConfirmationCodeDao confirmationCodeDao;
	@Autowired
	private UserService userServ;
	
	public String sendEmail(String template, User user, String subject, Object content) {
		String firstName = "customer";
		String lastName = "";
		if (user.getFirstName() != null) { firstName = user.getFirstName(); }
		if (user.getLastName() != null) { lastName = user.getLastName(); }
		
		String username = String.format("%s %s", firstName, lastName);
		try {
			if (template != null) {
				mailApp.sendMail(template, user.getEmail(), subject, username, content);
			} else {
				mailApp.sendMail(user.getEmail(), subject, username, content);
			}
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
	
	public String reg(User user, CreditCard creditCard) {
		user.setEnable(0);
		user.setRole("ROLE_USER");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		user.setPassword(encoder.encodePassword(user.getPassword(),null));

		try{
			userDao.saveUser(user);
			userServ.saveCreditCard(user, creditCard);
			String code = UUID.randomUUID().toString();
			ConfirmationCode cc = new ConfirmationCode();
			cc.setUserid(user.getUserid());
			cc.setCode(code);
			confirmationCodeDao.saveConfirmationCodeByCode(cc);
			sendConfirmation(user, code);
			return null;
		}
		catch (HibernateException e) {
			logger.error(e.getMessage());
			return "System Error: user cannot be saved.";
		}
	}
	
	public String sendConfirmation(User user, String code) {
		String subject = "RTS Admin: please confirm your email";
		RegContent content = new RegContent();
		content.setCode(code);
		return sendEmail("registration-confirmation", user, subject, content);
	}
	
	public String confirmUser(String code) {
		if (code == null || code.isEmpty()) return "";
		try {
			ConfirmationCode cc = confirmationCodeDao.getConfirmationCodeByCode(code);
			User user = userDao.getUserById(cc.getUserid());
			userDao.setUserStatus(user, 1);
			return null;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			return "System Error: cannot confirm email.";
		}
		
	}
}
