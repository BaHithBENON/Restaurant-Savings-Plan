package com.rewardomain.restaurant.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, 
	value = {"id", "name", "merchantNumber", "benefitPercentage", 
			"benefitAvailabilityPolicy", "suspended", "executionChain"
	}
)
public class RestaurantError extends Restaurant {
	
	@JsonProperty("status_code")
	private Long statusCode;
	
	@JsonProperty("error_message")
	private String message;
	
	public RestaurantError() {
		super();
	}

	public RestaurantError(Long id, Long merchantNumber, String name, Double benefitPercentage,
			String benefitAvailabilityPolicy) {
		super(id, merchantNumber, name, benefitPercentage, benefitAvailabilityPolicy);
	}

	public RestaurantError(Long id, Long merchantNumber, String name, Double benefitPercentage,
			String benefitAvailabilityPolicy, Long statusCode, String message) {
		super(id, merchantNumber, name, benefitPercentage, benefitAvailabilityPolicy);
		this.statusCode = statusCode;
		this.message = message;
	}

	public RestaurantError(Long statusCode, String message) {
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
