package com.rts.persistence.dao;

import com.rts.persistence.model.User;

import java.util.List;

public interface UserDao {
	User getUserById(int id);
	User getUserByEmail(String email);
	User setUserStatus(User user, int status);
	void saveUser(User user);
	List<User> listAllUsers();
}
