package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.User;
import com.JohnHaney.OpenJob.security.WebSecurityConfig;

	@Service
	public class UserServices{

		@Autowired
		UserRepoIF userRepo;
		
		
		public List<User> findAll() {
			return userRepo.findAll();
		}
		
		public User findById(Integer id) {
			return userRepo.findById(id).get();
		}
		
		public boolean existsById(Integer id) {
			return userRepo.existsById(id);
		}
		
		public void deleteById(Integer id) {
			if (existsById(id))
				userRepo.deleteById(id);
		}
		
		public void save(User user) {		
			user.setPassword(WebSecurityConfig.passwordEncoder().encode(user.getPassword()));
			userRepo.save(user);
		}
}
