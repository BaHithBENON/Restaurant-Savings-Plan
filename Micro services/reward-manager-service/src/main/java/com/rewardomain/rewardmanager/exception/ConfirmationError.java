package com.rewardomain.rewardmanager.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rewardomain.rewardmanager.bean.Confirmation;

@JsonIgnoreProperties(ignoreUnknown = true, 
	value = {"rewardConfirmation", "executionChain"}
)
public class ConfirmationError extends Confirmation {
	@JsonProperty("status_code")
	private Long statusCode;
	
	@JsonProperty("error_message")
	private String message;
	
	public ConfirmationError() {
		super();
	}

	public ConfirmationError(long statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	/**
	 * @return the statusCode
	 */
	public Long getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(Long statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
