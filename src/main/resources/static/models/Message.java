package com.JohnHaney.OpenJob.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(message = "Field is required!")
	private long messageId;
	private String messageBody;
}
