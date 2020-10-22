package com.JohnHaney.OpenJob.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(this.userDetailsService);
		return provider;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();// bcrypt as default

		String idForEncode = "bcrypt";

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		// add each type of encryption to the map
		encoders.put(idForEncode, new BCryptPasswordEncoder());// fast but not very secure
		encoders.put("noop", NoOpPasswordEncoder.getInstance());// plaintext no encryption
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());// very secure but large computational demand
		encoders.put("sha256", new StandardPasswordEncoder());

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);// set a default

		return passwordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("spring").password(encoder.encode("spring")).roles("ADMIN");
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/login").permitAll();
		http.authorizeRequests().antMatchers("/TODO").hasRole("USER");// sets permissions the page TODO: add in
																		// login/registration page/controller

		http.authorizeRequests()

				.and()

				// this acts like session
				.formLogin().loginPage("/login").loginProcessingUrl("/login/authenticate").permitAll()
				.failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("uname")
				.passwordParameter("psw")

				.and()

				.exceptionHandling().accessDeniedPage("/accessdenied")

				.and()

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").and()
				.exceptionHandling().accessDeniedPage("/accessdenied")

				.and()

				.csrf().disable();
	}
}