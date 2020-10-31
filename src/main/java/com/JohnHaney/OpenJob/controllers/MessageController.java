package com.JohnHaney.OpenJob.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.JohnHaney.OpenJob.DAO.MessageRepoIF;
import com.JohnHaney.OpenJob.DAO.UserRepoIF;

@Controller
public class MessageController {

	@Autowired
	private UserRepoIF userRepo;
	
	@Autowired
	private MessageRepoIF messageRepo;
	
	@GetMapping("/contactForm/{userId}")
	public String messageUser(@PathVariable (value = "userId") Long userId) {
		
		return "contactForm";
	}
}
