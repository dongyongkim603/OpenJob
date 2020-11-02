package com.JohnHaney.OpenJob.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class OrderDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	private String orderStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date estimatedDelivery;
	@Basic
	private LocalDate orderCreationDate;
	private Double price;
	
	@OneToOne
	private JobDTO job;
	
	@ManyToOne
	private UserDTO freelancer;

//---------getters and setters ----------------------	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public JobDTO getJob() {
		return job;
	}

	public void setJob(JobDTO job) {
		this.job = job;
	}

	public UserDTO getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(UserDTO freelancer) {
		this.freelancer = freelancer;
	}

	public Date getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(Date estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getOrderCreationDate() {
		return orderCreationDate;
	}

	public void setOrderCreationDate(LocalDate orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}

}
