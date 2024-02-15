package com.rewardomain.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.restaurant.bean.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	Restaurant findByMerchantNumber(long number);
}
