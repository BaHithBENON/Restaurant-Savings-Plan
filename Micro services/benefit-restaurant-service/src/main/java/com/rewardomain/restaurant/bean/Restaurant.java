package com.rewardomain.restaurant.bean;

import java.util.Objects;

import org.springframework.data.annotation.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
	
	@Id
	private Long id;
	@Column(name="merchant_number")
    private Long merchantNumber;
    private String name;
	@Column(name="benefit_percentage")
    private Double benefitPercentage;
	@Column(name="benefit_availability_policy")
    private String benefitAvailabilityPolicy;
	
	private boolean suspended;
	
	@Transient
	private String executionChain;
	
    
	public Restaurant(Long id, Long merchantNumber, String name, Double benefitPercentage,
			String benefitAvailabilityPolicy) {
		super();
		this.id = id;
		this.merchantNumber = merchantNumber;
		this.name = name;
		this.benefitPercentage = benefitPercentage;
		this.benefitAvailabilityPolicy = benefitAvailabilityPolicy;
		this.suspended = false;
	}

	public Restaurant(Long id, Long merchantNumber, String name, Double benefitPercentage,
			String benefitAvailabilityPolicy, boolean suspended) {
		super();
		this.id = id;
		this.merchantNumber = merchantNumber;
		this.name = name;
		this.benefitPercentage = benefitPercentage;
		this.benefitAvailabilityPolicy = benefitAvailabilityPolicy;
		this.suspended = suspended;
	}

	public Restaurant() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(Long merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBenefitPercentage() {
		return benefitPercentage;
	}

	public void setBenefitPercentage(Double benefitPercentage) {
		this.benefitPercentage = benefitPercentage;
	}

	public String getBenefitAvailabilityPolicy() {
		return benefitAvailabilityPolicy;
	}

	public void setBenefitAvailabilityPolicy(String benefitAvailabilityPolicy) {
		this.benefitAvailabilityPolicy = benefitAvailabilityPolicy;
	}
	
	public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
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

	@Override
	public int hashCode() {
		return Objects.hash(benefitAvailabilityPolicy, benefitPercentage, id, merchantNumber, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Restaurant)) {
			return false;
		}
		Restaurant other = (Restaurant) obj;
		return Objects.equals(benefitAvailabilityPolicy, other.benefitAvailabilityPolicy)
				&& Objects.equals(benefitPercentage, other.benefitPercentage) && Objects.equals(id, other.id)
				&& Objects.equals(merchantNumber, other.merchantNumber) && Objects.equals(name, other.name)
				&& suspended == other.suspended;
	}
	
	
    
    
}
