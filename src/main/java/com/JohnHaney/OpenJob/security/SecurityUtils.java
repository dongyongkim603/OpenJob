package com.JohnHaney.OpenJob.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.JohnHaney.OpenJob.models.User;

public final class SecurityUtils {

	// method to find login user via email (using sessions )

	private SecurityUtils() {
	}

	public static String getUser() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userName = null;
		if (authentication != null) {
			User newUser = new User();
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