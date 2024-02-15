package com.rewardomain.accountcontribution.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rewardomain.accountcontribution.bean.Beneficiary;

@JsonIgnoreProperties(ignoreUnknown = true, 
	value = {"id", "number", "account"}
)
public class CreditCardError extends Beneficiary  {
	@JsonProperty("status_code")
	private Long statusCode;
	
	@JsonProperty("error_message")
	private String message;
	
	public CreditCardError() {
		super();
	}
	
	public CreditCardError(long statusCode, String message) {
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

