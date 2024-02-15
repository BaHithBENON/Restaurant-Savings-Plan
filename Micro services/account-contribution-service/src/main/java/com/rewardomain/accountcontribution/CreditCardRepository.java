package com.rewardomain.accountcontribution;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.accountcontribution.bean.CreditCard;

public interface CreditCardRepository extends JpaRepository <CreditCard, Long>  {

}
