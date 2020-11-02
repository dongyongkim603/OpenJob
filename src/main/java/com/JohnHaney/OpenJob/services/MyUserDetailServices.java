package com.JohnHaney.OpenJob.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.UserDTO;
import com.JohnHaney.OpenJob.security.MyUserDetials;

@Service
public class MyUserDetailServices implements UserDetailsService {

	@Autowired
	UserRepoIF userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDTO> u = userRepo.findByUsername(username);
		u.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
		return u.map(MyUserDetials::new).get();
	}

}
