package com.foodapp.dao;

import com.foodapp.model.User;

public interface UserDAO {

	void addUser(User user);

	User getUserById(int userId);

	User getUserByEmail(String email);

	void updateUser(User user);

	void deleteUser(int userId);
	
	User getUser(String username);
}