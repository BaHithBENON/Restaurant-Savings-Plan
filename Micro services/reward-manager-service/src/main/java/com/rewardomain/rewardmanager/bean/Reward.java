package com.rewardomain.rewardmanager.bean;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Reward {
	
	@Id
	private long id;
	
	@Column(name="confirmation_number")
	private long confirmationNumber;
	
	private double amount;
	
	@Column(name="merchant_number")
	private long merchantNumber;
	
	@Column(name="reward_date")
	private LocalDateTime rewardDate;
	
	@Transient
	private String executionChain;

	public Reward(long id, long confirmationNumber, double amount, long merchantNumber, LocalDateTime rewardDate) {
		super();
		this.id = id;
		this.confirmationNumber = confirmationNumber;
		this.amount = amount;
		this.merchantNumber = merchantNumber;
		this.rewardDate = rewardDate;
	}

	public Reward() {
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
	 * @return the confirmationNumber
	 */
	public long getConfirmationNumber() {
		return confirmationNumber;
	}

	/**
	 * @param confirmationNumber the confirmationNumber to set
	 */
	public void setConfirmationNumber(long confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
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
	 * @return the rewardDate
	 */
	public LocalDateTime getRewardDate() {
		return rewardDate;
	}

	/**
	 * @param rewardDate the rewardDate to set
	 */
	public void setRewardDate(LocalDateTime rewardDate) {
		this.rewardDate = rewardDate;
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
