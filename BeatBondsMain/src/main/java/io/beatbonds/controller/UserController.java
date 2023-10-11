package io.beatbonds.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.beatbonds.model.User;
import io.beatbonds.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	private UserService userService;
	
	
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> userOnBoarding(@RequestBody User user){
		User usr = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getCoins());
		userService.createUser(usr);
		return new ResponseEntity<>(usr, HttpStatus.CREATED);
	}
	
	@GetMapping("/allusers")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> ls = userService.getAllUser();
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}
}
