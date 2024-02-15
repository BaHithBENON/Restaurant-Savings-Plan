package com.rewardomain.accountcontribution.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.rewardomain.accountcontribution.bean.Beneficiary;

@ConfigurationProperties("account-contribution-service")
@Component
public class Distribution {
	
	public Distribution() {}
	
	public void distribute(List<Beneficiary> beneficiaires, double reward) {
		for(Beneficiary benficiary : beneficiaires) {
			benficiary.setSavings(benficiary.getSavings() + reward * (benficiary.getPercentage() / 100));
		}
	}
	
	public boolean distributeIsPossible(List<Beneficiary> beneficiaires) {
		double percentage = 0.0;
		for(Beneficiary benficiary : beneficiaires) {
			percentage += benficiary.getPercentage();
		}
		if(percentage != 100) {
			return false;
		} 
		return true;
	}
}
