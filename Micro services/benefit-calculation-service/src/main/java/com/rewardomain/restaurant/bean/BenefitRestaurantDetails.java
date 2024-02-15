package com.rewardomain.restaurant.bean;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BenefitRestaurantDetails extends BenefitRestaurant {
	
	private int id;
	@JsonProperty("merchantNumber")
	private int merchantNumber;
	private String name; 
	@JsonProperty("benefitPercentage")
	private double benefitPercentage;
	private String benefitAvailabilityPolicy;
	

	@Transient
	private String executionChain;
	
	
	// Ajout d'un constructeur par défaut
    public BenefitRestaurantDetails() {
        super();
        //System.out.print("number : ");
    }
	
	public BenefitRestaurantDetails(int id, int number, String name, double percentage, String availability) {
		super(number, percentage);
		// System.out.print("number : " + number);
		this.id = id;
		this.merchantNumber = number;
		this.name = name;
		this.benefitPercentage = percentage;
		this.benefitAvailabilityPolicy = availability;
	}

	public BenefitRestaurantDetails(double diningAmount, double benefitPercentage) {
		super(diningAmount, benefitPercentage);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return merchantNumber;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.merchantNumber = number;
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
	 * @return the percentage
	 */
	public double getPercentage() {
		return benefitPercentage;
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

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(double percentage) {
		this.benefitPercentage = percentage;
	}

	/**
	 * @return the availability
	 */
	public String getAvailability() {
		return benefitAvailabilityPolicy;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(String availability) {
		this.benefitAvailabilityPolicy = availability;
	}
	
	
}
