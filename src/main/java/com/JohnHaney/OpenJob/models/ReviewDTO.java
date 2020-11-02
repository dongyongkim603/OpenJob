package com.JohnHaney.OpenJob.models;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ReviewDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	@NotNull(message = "Field is required!")
	private String reviewBody;
	@NotNull(message = "Field is required!")
	private Float rating;
	@Basic
	private LocalDate creationDate;
	@ManyToOne
	private JobDTO job;
	@ManyToOne
	private UserDTO sender;
	
//---------getters and setters ----------------------	
	
	public JobDTO getJob() {
		return job;
	}
	public void setJob(JobDTO job) {
		this.job = job;
	}
	public UserDTO getSender() {
		return sender;
	}
	public void setSender(UserDTO sender) {
		this.sender = sender;
	}
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public String getReviewBody() {
		return reviewBody;
	}
	public void setReviewBody(String reviewBody) {
		this.reviewBody = reviewBody;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
}
