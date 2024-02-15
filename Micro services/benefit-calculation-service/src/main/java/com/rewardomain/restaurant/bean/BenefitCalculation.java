package com.rewardomain.restaurant.bean;

public class BenefitCalculation {
	private String benefitAmount;

	public BenefitCalculation(String benefitAmount) {
		super();
		this.benefitAmount = benefitAmount;
	}
	
	public BenefitCalculation() {
		super();
	}

	/**
	 * @return the benefitAmount
	 */
	public String getBenefitAmount() {
		return benefitAmount;
	}

	/**
	 * @param benefitAmount the benefitAmount to set
	 */
	public void setBenefitAmount(String benefitAmount) {
		this.benefitAmount = benefitAmount;
	}
	
	
}
