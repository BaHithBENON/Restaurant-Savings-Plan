package com.rewardomain.rewardmanager.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.rewardomain.rewardmanager.bean.AccountContributionResponse;


// @FeignClient(name="account-contribution", url="http://localhost:8400")
@FeignClient(name="account-contribution-service")
public interface AccountContributionProxy {
	
	// Distribution de la récompense à tous les bénéficiaires d’un compte spécifique
	@PutMapping("/account-contribution/accounts/{credit_card_number}/reward/{reward}")
    public ResponseEntity<AccountContributionResponse> distributeReward(
    		@PathVariable("credit_card_number") String number,
    		@PathVariable double reward
    );
}
