package com.JohnHaney.OpenJob.models;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long messageId;
	@NotNull(message = "Field is required!")
	private String messageBody;
	@Basic
	private LocalDate sentTime;
	
//---------getters and setters ----------------------
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public LocalDate getSentTime() {
		return sentTime;
	}
	public void setSentTime(LocalDate sentTime) {
		this.sentTime = sentTime;
	}
	
}
