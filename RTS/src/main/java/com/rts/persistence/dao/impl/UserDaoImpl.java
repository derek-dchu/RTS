package com.rts.persistence.dao.impl;

import com.rts.common.db.GenericDaoImpl;
import com.rts.persistence.dao.UserDao;
import com.rts.persistence.model.User;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {

	public UserDaoImpl() {}
	public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

	public User getUserById(int id) {
		return findById(id);
	}

	public User getUserByEmail(String email) {
		return findBy("email", email);
	}

	public User setUserStatus(User user, int status) {
		user.setEnable(status);
		save(user);
		return user;
	}

	public void saveUser(User user) {
		save(user);
	}

	public List<User> listAllUsers() {
		return findAll();
	}

}
