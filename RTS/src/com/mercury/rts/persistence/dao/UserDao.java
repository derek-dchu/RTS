package com.mercury.rts.persistence.dao;

import java.util.List;

import com.mercury.rts.persistence.model.User;

public interface UserDao {
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public User setUserStatus(User user, int status);
	public void saveUser(User user);
	public List<User> listAllUsers();
}
