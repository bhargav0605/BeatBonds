package io.beatbonds.service;

import java.util.List;

import io.beatbonds.model.User;

public interface UserService {
	void createUser(User user);
	List<User> getAllUser();
	
}
