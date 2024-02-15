package com.rewardomain.rewardmanager.repository;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.rewardmanager.bean.Reward;

public interface RewardRepository extends JpaRepository <Reward, Long> {
	
	Optional<Reward> findByConfirmationNumber(long confirmationNumber);
}
