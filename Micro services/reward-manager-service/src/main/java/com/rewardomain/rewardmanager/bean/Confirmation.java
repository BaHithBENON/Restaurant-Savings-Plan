package com.rewardomain.rewardmanager.bean;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Confirmation {
	
	@JsonProperty("reward_confirmation_number")
	private long rewardConfirmation;
	
	@Transient
	private String executionChain;

	public Confirmation(long rewardConfirmation) {
		super();
		this.rewardConfirmation = rewardConfirmation;
	}

	public Confirmation() {
		super();
	}

	/**
	 * @return the rewardConfirmation
	 */
	public long getRewardConfirmation() {
		return rewardConfirmation;
	}

	/**
	 * @param rewardConfirmation the rewardConfirmation to set
	 */
	public void setRewardConfirmation(long rewardConfirmation) {
		this.rewardConfirmation = rewardConfirmation;
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
