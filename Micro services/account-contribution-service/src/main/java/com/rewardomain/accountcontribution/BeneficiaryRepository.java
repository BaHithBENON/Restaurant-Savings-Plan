package com.rewardomain.accountcontribution;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.accountcontribution.bean.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository <Beneficiary, Long> {

}
