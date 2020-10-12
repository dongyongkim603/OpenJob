package com.JohnHaney.OpenJob.models;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(message = "Field is required!")
	private int categoryId;
	private String description;
	private String name;
	private List<SubCategory> subCategory;
}
