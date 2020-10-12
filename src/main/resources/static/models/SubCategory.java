package com.JohnHaney.OpenJob.models;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(message = "Field is required!")
	private int subCategoryId;
	private String subCategoryName;
	private List<Job> jobList;
}
