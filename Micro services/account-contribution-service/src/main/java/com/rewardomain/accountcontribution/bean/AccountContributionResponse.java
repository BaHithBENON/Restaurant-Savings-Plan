package com.rewardomain.accountcontribution.bean;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountContributionResponse {
	
	@JsonProperty("status_code")
	private int code;
	
	private String message;
	
	@Transient
	private String executionChain;

	public AccountContributionResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public AccountContributionResponse() {
		super();
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
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

	/**
	 * @return the executionChain
	 */
	public String getExecutionChain() {
		return executionChain;
	}

	/**
	 * @param executionChain the executionChain to set
	 */
	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}
	
	
	
}
