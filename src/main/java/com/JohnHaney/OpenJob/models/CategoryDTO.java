package com.JohnHaney.OpenJob.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class CategoryDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	private String description;
	@NotNull(message = "Field is required!")
	private String name;
	@OneToMany(targetEntity = SubCategoryDTO.class)
	private List<SubCategoryDTO> subCategory;

//---------getters and setters ----------------------
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SubCategoryDTO> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(List<SubCategoryDTO> subCategory) {
		this.subCategory = subCategory;
	}
		
}
