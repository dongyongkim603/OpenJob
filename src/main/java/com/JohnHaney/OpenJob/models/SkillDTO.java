package com.JohnHaney.OpenJob.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SkillDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer skillID;
	@NotNull(message = "Field is required!")
	private String skilltype;
	
//---------getters and setters ----------------------	
	
	public Integer getSkillID() {
		return skillID;
	}
	public void setSkillID(Integer skillID) {
		this.skillID = skillID;
	}
	public String getSkilltype() {
		return skilltype;
	}
	public void setSkilltype(String skilltype) {
		this.skilltype = skilltype;
	}

}
