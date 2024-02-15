package com.rewardomain.rewardmanager.bean;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dining {
	
	private long id;
	
	@JsonProperty("creadit_card_number")
	private String creditCardNumber;
	
	@JsonProperty("merchant_number")
	private long merchantNumber;
	
	@JsonProperty("dining_amount")
	private double diningAmount;
	
	@JsonProperty("dining_date")
	private String diningDate;
	
	@Transient
	private String executionChain;

	public Dining(long id, String creditCardNumber, long merchantNumber, double diningAmount, String diningDate) {
		super();
		this.id = id;
		this.creditCardNumber = creditCardNumber;
		this.merchantNumber = merchantNumber;
		this.diningAmount = diningAmount;
		this.diningDate = diningDate;
	}

	public Dining() {
		super();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * @return the merchantNumber
	 */
	public long getMerchantNumber() {
		return merchantNumber;
	}

	/**
	 * @param merchantNumber the merchantNumber to set
	 */
	public void setMerchantNumber(long merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	/**
	 * @return the diningAmount
	 */
	public double getDiningAmount() {
		return diningAmount;
	}

	/**
	 * @param diningAmount the diningAmount to set
	 */
	public void setDiningAmount(double diningAmount) {
		this.diningAmount = diningAmount;
	}

	/**
	 * @return the diningDate
	 */
	public String getDiningDate() {
		return diningDate;
	}

	/**
	 * @param diningDate the diningDate to set
	 */
	public void setDiningDate(String diningDate) {
		this.diningDate = diningDate;
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
