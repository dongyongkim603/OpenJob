package com.JohnHaney.OpenJob.models;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(message = "Field is required!")
	private long jobId;
	private String jobName;
	private String jobDescription;
	private double price;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creationDate;
	private List<Review> jobReview;
}
