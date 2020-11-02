package com.JohnHaney.OpenJob.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class JobDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;
	@NotNull(message = "Field is required!")
	private String jobName;
	private String jobDescription;
	private Double price;
	
	//bidirectional
	@ManyToOne
	private UserDTO user;
	
	@Basic
	private LocalDate creationDate;
	
	@OneToMany(targetEntity = ReviewDTO.class, fetch = FetchType.LAZY, mappedBy = "job", cascade = CascadeType.MERGE)
	private List<ReviewDTO> jobReview;
	
//---------getters and setters ----------------------	
	
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public List<ReviewDTO> getJobReview() {
		return jobReview;
	}
	public void setJobReview(List<ReviewDTO> jobReview) {
		this.jobReview = jobReview;
	}
	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}

}
