package com.JohnHaney.OpenJob.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(message = "Field is required!")
	private long userId;
	private String firstName;
	private String lastName;
	@Column(unique=true) 
	private String username;
	private String phoneNumber;
	private String country;
	private String city;
	@Column(unique=true) 
	private String email;
	private String password;
	@OneToMany(targetEntity = Message.class)
	private List<Message> messages;
	@OneToMany(targetEntity = Review.class)
	private List<Review> reviews;
	@OneToMany(targetEntity = Skill.class)
	private List<Skill> skills;

}
