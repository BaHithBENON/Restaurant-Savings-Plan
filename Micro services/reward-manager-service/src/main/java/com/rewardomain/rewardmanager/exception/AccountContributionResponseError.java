package com.rewardomain.rewardmanager.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rewardomain.rewardmanager.bean.AccountContributionResponse;

@JsonIgnoreProperties(ignoreUnknown = true, 
	value = {"code", "message", "executionChain"}
)
public class AccountContributionResponseError extends AccountContributionResponse {
	@JsonProperty("status_code")
	private Long statusCode;
	
	@JsonProperty("error_message")
	private String message;
	
	public AccountContributionResponseError() {
		super();
	}

	public AccountContributionResponseError(long statusCode, String message) {
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
