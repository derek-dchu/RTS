package com.mercury.rts.persistence.dao.impl;

import java.util.List;

import com.mercury.rts.common.db.GenericDaoImpl;
import com.mercury.rts.persistence.dao.UserDao;
import com.mercury.rts.persistence.model.User;

public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {
	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User getUserById(int id) {
		return findById(id);
	}

	@Override
	public User getUserByEmail(String email) {
		return findBy("email", email);
	}

	@Override
	public User setUserStatus(User user, int status) {
		user.setEnable(status);
		saveUser(user);
		return user;
	}

	@Override
	public void saveUser(User user) {
		save(user);
	}

	@Override
	public List<User> listAllUsers() {
		return findAll();
	}

}
