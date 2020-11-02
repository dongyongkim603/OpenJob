package com.JohnHaney.OpenJob.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="photos")
public class PhotoDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long photoId;
	private String uploader;
	private String path;
	private String fileName;
	private String comments;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="userId")
	private UserDTO user;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="jobId")
	private JobDTO job;
	
//----------getters and setters------------------
	
	public Long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public JobDTO getJob() {
		return job;
	}
	public void setJob(JobDTO job) {
		this.job = job;
	}
}
