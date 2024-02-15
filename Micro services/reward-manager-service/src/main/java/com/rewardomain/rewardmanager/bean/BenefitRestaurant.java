package com.rewardomain.rewardmanager.bean;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BenefitRestaurant {

	@JsonProperty("benefitAmount")
	private double amount;
	
	@Transient
	private String executionChain;

	public BenefitRestaurant(double amount) {
		super();
		this.amount = amount;
	} 

	public BenefitRestaurant() {
		super();
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
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
