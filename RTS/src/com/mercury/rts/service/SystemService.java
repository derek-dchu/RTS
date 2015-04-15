package com.mercury.rts.service;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercury.rts.mail.MailAppBean;
import com.mercury.rts.mail.RegContent;
import com.mercury.rts.persistence.dao.impl.ConfirmationCodeDaoImpl;
import com.mercury.rts.persistence.dao.impl.TicketDaoImpl;
import com.mercury.rts.persistence.dao.impl.UserDaoImpl;
import com.mercury.rts.persistence.model.ConfirmationCode;
import com.mercury.rts.persistence.model.Ticket;
import com.mercury.rts.persistence.model.User;

@Service
@Transactional
public class SystemService {
	private static Logger logger = Logger.getLogger(SystemService.class);
	
	@Autowired
	MailAppBean mailApp;
	@Autowired
	UserDaoImpl userDao;
	@Autowired
	TicketDaoImpl ticketDao;
	@Autowired
	ConfirmationCodeDaoImpl confirmationCodeDao;
	
	public String sendEmail(String template, User user, String subject, Object content) {
		String firstName = "customer";
		String lastName = "";
		if (user.getFirstName() != null) { firstName = user.getFirstName(); }
		if (user.getLastName() != null) { lastName = user.getLastName(); }
		
		String username = String.format("%s %s", firstName, lastName);
		try {
			mailApp.sendMail(template, user.getEmail(), subject, username, content);
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
	
	public String reg(User user) {
		user.setEnable(0);
		user.setRole("ROLE_USER");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		user.setPassword(encoder.encodePassword(user.getPassword(),null));

		try{
			userDao.saveUser(user);
			
			String code = UUID.randomUUID().toString();
			ConfirmationCode cc = new ConfirmationCode();
			cc.setUserid(user.getUserid());
			cc.setCode(code);
			confirmationCodeDao.save(cc);
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
