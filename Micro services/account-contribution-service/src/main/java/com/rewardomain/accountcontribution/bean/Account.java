package com.rewardomain.accountcontribution.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column
	private String owner;
	
	@JsonProperty("account_number")
	@Column(name="account_number")
	private String number;
	
	@Column(name="total_benefits")
	private double benefits;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Beneficiary> beneficiaries = new ArrayList<>();
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "credit_card_id")
	private CreditCard creditCard;

	public Account(Long id, String owner, String number, double benefits, List<Beneficiary> beneficiaries,
			CreditCard creditCard) {
		super();
		this.id = id;
		this.owner = owner;
		this.number = number;
		this.benefits = benefits;
		this.beneficiaries = beneficiaries;
		this.creditCard = creditCard;
	}

	public Account() {
		super();
	}

	public Account(String owner, String number) {
		this.owner = owner;
		this.number = number;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the benefits
	 */
	public double getBenefits() {
		return benefits;
	}

	/**
	 * @param benefits the benefits to set
	 */
	public void setBenefits(double benefits) {
		this.benefits = benefits;
	}

	/**
	 * @return the beneficaires
	 */
	public List<Beneficiary> getBeneficaires() {
		return beneficiaries;
	}

	/**
	 * @param beneficaires the beneficaires to set
	 */
	public void setBeneficaires(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	/**
	 * @return the creditCard
	 */
	public CreditCard getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	public boolean isValid() {
		double totalPercentage = 0.0;
		for(Beneficiary beneficiary : beneficiaries) {
			totalPercentage += beneficiary.getPercentage();
		}
		return totalPercentage == 100.0 ? true : false;
	}
	
}
