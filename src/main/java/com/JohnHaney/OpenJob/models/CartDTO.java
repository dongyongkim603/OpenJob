package com.JohnHaney.OpenJob.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class CartDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	
	private Double total;
	
	@OneToMany(targetEntity = JobDTO.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<JobDTO> jobs = new ArrayList<>();
	
	@OneToOne
	private UserDTO shopper;
	
//----------------------- Getters and Setters---------------

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<JobDTO> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobDTO> jobs) {
		this.jobs = jobs;
	}

	public UserDTO getShopper() {
		return shopper;
	}

	public void setShopper(UserDTO shopper) {
		this.shopper = shopper;
	}
	
//-----------------------------------------------------
	
	/**
	 * adds the job to the list
	 * @param job the target job
	 */
	public void addJobToCart(JobDTO job) {
		this.jobs.add(job);
	}
	
	/**
	 * will loop through the list of jobs find the prices and update the CartDTO total attribute 
	 */
	public void updateTotal() {
		Double total =0.0;
		for(JobDTO j: this.jobs) {
			total += j.getPrice();
		}
		setTotal(total);
	}
	
	/**
	 * allows for easier removal of jobs from CartDTO
	 * @param job the target job to be removed
	 * @return will return true if job was removed successfully
	 */
	public boolean removeFromCart(JobDTO job) {
		return this.jobs.remove(job);
	}
	
}
