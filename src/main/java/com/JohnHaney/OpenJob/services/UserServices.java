package com.JohnHaney.OpenJob.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.security.WebSecurityConfig;

	@Service
	public class UserServices{

		@Autowired
		UserRepoIF userRepo;
		
		public List<UserDTO> findAll() {
			return userRepo.findAll();
		}
		
		public UserDTO findById(Long id) {
			return userRepo.findById(id).get();
		}
		
		public boolean existsById(Long id) {
			return userRepo.existsById(id);
		}
		
		public boolean existsByUsername(String username) {
			UserDTO user = findByUsername(username);
			if(!user.equals(null))
				return true;
			else
				return false;
		}
		
		public UserDTO findByUsername(String username) {
			return userRepo.findByUsername(username).get();
		}
		
		public void deleteById(Long id) {
			if (existsById(id))
				userRepo.deleteById(id);
		}
		
		public void save(UserDTO user) {		
			user.setPassword(WebSecurityConfig.passwordEncoder().encode(user.getPassword()));
			userRepo.save(user);
		}
		
}
