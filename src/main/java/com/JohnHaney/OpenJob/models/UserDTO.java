package com.JohnHaney.OpenJob.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class UserDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId")
	private Long userId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	@NotNull(message = "Field is required!")
	private String username;
	private String phoneNumber;
	private String country;
	private String city;
	@Column(unique = true)
	@NotNull(message = "Field is required!")
	private String email;
	private String password;
	private String role;
	
	@OneToMany(targetEntity=JobDTO.class, cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.LAZY)
	private List<JobDTO> jobPosts;
	
	@OneToMany(targetEntity = OrderDTO.class, cascade = CascadeType.ALL, mappedBy = "freelancer", fetch=FetchType.LAZY)
	private List<OrderDTO> orders;
	
	@OneToMany(targetEntity = MessageDTO.class, cascade = CascadeType.ALL, mappedBy = "sender", fetch=FetchType.LAZY)
	private List<MessageDTO> messages;
	
	@OneToMany(targetEntity = ReviewDTO.class, cascade = CascadeType.ALL, mappedBy = "sender", fetch=FetchType.LAZY)
	private List<ReviewDTO> userReviews;
	
	@OneToMany(targetEntity = ReviewDTO.class)
	private List<ReviewDTO> jobReviews;
	
	@OneToMany(targetEntity = SkillDTO.class)
	private List<SkillDTO> skills;
	
	@OneToOne
	private CartDTO cart;

//----------constructors----------------------

	public UserDTO() {
	}
	
	public UserDTO(String firstName, String lastName, @NotNull(message = "Field is required!") String username,
			String phoneNumber, @NotNull(message = "Field is required!") String email,
			String password, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public UserDTO(String firstName, String lastName, @NotNull(message = "Field is required!") String username,
			String phoneNumber, String country, String city, @NotNull(message = "Field is required!") String email,
			String password, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.city = city;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
//---------getters and setters ----------------------	

	public CartDTO getCart() {
		return cart;
	}

	public void setCart(CartDTO cart) {
		this.cart = cart;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<JobDTO> getJobPosts() {
		return jobPosts;
	}

	public void setJobPosts(List<JobDTO> jobPosts) {
		this.jobPosts = jobPosts;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}

	public List<ReviewDTO> getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(List<ReviewDTO> userReviews) {
		this.userReviews = userReviews;
	}

	public List<ReviewDTO> getJobReviews() {
		return jobReviews;
	}

	public void setJobReviews(List<ReviewDTO> jobReviews) {
		this.jobReviews = jobReviews;
	}

	public List<SkillDTO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", phoneNumber=" + phoneNumber + ", country=" + country + ", city=" + city + ", email="
				+ email + ", role=" + role + "]";
	}
}
