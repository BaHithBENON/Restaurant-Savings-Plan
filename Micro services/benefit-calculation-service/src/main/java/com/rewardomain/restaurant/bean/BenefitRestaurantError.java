package com.rewardomain.restaurant.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, 
	value = {"amount", "executionChain"}
)
public class BenefitRestaurantError extends BenefitRestaurant {
	@JsonProperty("status_code")
	private Long statusCode;
	
	@JsonProperty("error_message")
	private String message;
	
	public BenefitRestaurantError() {
		super();
	}

	public BenefitRestaurantError(double amount, String executionChain) {
		super(amount, executionChain);
	}

	public BenefitRestaurantError(double amount, String executionChain, Long statusCode, String message) {
		super(amount, executionChain);
		this.statusCode = statusCode;
		this.message = message;
	}

	public BenefitRestaurantError(long statusCode, String message) {
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
