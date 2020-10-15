package com.JohnHaney.OpenJob.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JohnHaney.OpenJob.DAO.UserRepoIF;
import com.JohnHaney.OpenJob.models.User;

@Service
public class MyUserDetailServices implements UserDetailsService {

	@Autowired
	UserRepoIF userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> u = userRepo.findByUsername(username);
		u.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
		return u.map(MyUserDetials::new).get();
	}

}
