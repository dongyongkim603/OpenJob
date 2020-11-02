package com.JohnHaney.OpenJob.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.JohnHaney.OpenJob.models.UserDTO;

public final class SecurityUtils {

	// method to find login user via email (using sessions )

	private SecurityUtils() {
	}

	/**
	 * 
	 * @return finds and returns the current users username
	 */
	public static String getUser() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userName = null;
		if (authentication != null) {
			UserDTO newUser = new UserDTO();
			try {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				userName = userDetails.getUsername();
			} catch (ClassCastException e) {
				e.getMessage();
			}

		}
		return userName;
	}

}
