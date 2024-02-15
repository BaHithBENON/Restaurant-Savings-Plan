package com.rewardomain.restaurant.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("benefit-calculation-service")
@Component
public class Configuration {
	
	@Value("${benefit.restaurant.service.url}")
	private String benefitRestaurantServiceUrl;

	/**
	 * @return the benefitRestaurantServiceUrl
	 */
	public String getBenefitRestaurantServiceUrl() {
		return benefitRestaurantServiceUrl;
	}

	/**
	 * @param benefitRestaurantServiceUrl the benefitRestaurantServiceUrl to set
	 */
	public void setBenefitRestaurantServiceUrl(String benefitRestaurantServiceUrl) {
		this.benefitRestaurantServiceUrl = benefitRestaurantServiceUrl;
	} 

}
