package com.JohnHaney.OpenJob.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
public class SubCategoryDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subCategoryId;
	@NotNull(message = "Field is required!")
	private String subCategoryName;
	@OneToMany(targetEntity = JobDTO.class)
	private List<JobDTO> jobList;
	
//---------getters and setters ----------------------
	
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public List<JobDTO> getJobList() {
		return jobList;
	}
	public void setJobList(List<JobDTO> jobList) {
		this.jobList = jobList;
	}
}
