package com.rts.persistence.dao;

import com.rts.persistence.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserDao {
	User getUserById(int id);
	User getUserByEmail(String email);
	User setUserStatus(User user, int status);
	void saveUser(User user);
	List<User> listAllUsers();
}
