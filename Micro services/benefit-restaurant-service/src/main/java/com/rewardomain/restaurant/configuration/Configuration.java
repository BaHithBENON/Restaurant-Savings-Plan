package com.rewardomain.restaurant.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("benefit-restaurant-service")
public class Configuration {

	@Value("${benefit-restauration-service.server-type}")
	private String type;
	
	@Value("${benefit-restauration-service.server-name}")
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
