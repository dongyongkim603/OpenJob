package com.JohnHaney.OpenJob;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.JohnHaney.OpenJob.controllers.UserController;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.security.WebSecurityConfig;
import com.JohnHaney.OpenJob.services.MyUserDetailServices;
import com.JohnHaney.OpenJob.services.UserServices;

@SpringBootTest
class OpenJobUserServiceTest {
	
	@Autowired
	UserServices userService;

	@Test
	void existsByUsernameTest() {
		assertTrue(userService.existsByUsername("dongyongkim"));
	}
	
	@Test
	void findByUsernameTest() {
		assertNotNull(userService.findByUsername("dongyongkim"));
	}
	
	@Test
	void registerDuplicateUsername() {
		UserDTO user = new UserDTO();
		user.setUsername("newUser");
		user.setEmail("set@email.com");
		user.setPassword("password");
		user.setRole("ROLE_FREELANCER");
		String test;
		try
		{
			userService.save(user);
			test = "is not null";
		}catch(Exception e) {
			test = null;
		}
		assertNull(test);
	}
	
	@Test
	void findAllUsers() {
		
	}
	
}
