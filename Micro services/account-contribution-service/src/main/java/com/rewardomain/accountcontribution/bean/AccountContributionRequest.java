package com.rewardomain.accountcontribution.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountContributionRequest {
	
	private String name;
	
	@JsonProperty("credit_card_number")
	private String ccnumber;
	
	@JsonProperty("account_number")
	private String anumber;
	
	@JsonProperty("allocation_percentage")
	private double percentage;

	public AccountContributionRequest(String name, String ccnumber, String anumber, double percentage) {
		super();
		this.name = name;
		this.ccnumber = ccnumber;
		this.anumber = anumber;
		this.percentage = percentage;
	}

	public AccountContributionRequest() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ccnumber
	 */
	public String getCcnumber() {
		return ccnumber;
	}

	/**
	 * @param ccnumber the ccnumber to set
	 */
	public void setCcnumber(String ccnumber) {
		this.ccnumber = ccnumber;
	}

	/**
	 * @return the anumber
	 */
	public String getAnumber() {
		return anumber;
	}

	/**
	 * @param anumber the anumber to set
	 */
	public void setAnumber(String anumber) {
		this.anumber = anumber;
	}

	/**
	 * @return the percentage
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
}
