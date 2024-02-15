package com.rewardomain.restaurant.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rewardomain.restaurant.bean.BenefitRestaurantDetails;

//@FeignClient(name="benefit-restaurant", url="http://localhost:8100")
@FeignClient(name="benefit-restaurant-service")
public interface BenefitRestaurantProxy {
	
	@GetMapping("/benefit-restaurant/merchants/{merchantNumber}")
	public BenefitRestaurantDetails getBenefitRestaurantDetails(@PathVariable("merchantNumber") long number);
}
