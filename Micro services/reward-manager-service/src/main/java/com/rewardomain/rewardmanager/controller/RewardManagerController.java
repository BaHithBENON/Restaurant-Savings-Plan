package com.rewardomain.rewardmanager.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.rewardmanager.bean.AccountContributionResponse;
import com.rewardomain.rewardmanager.bean.BenefitRestaurant;
import com.rewardomain.rewardmanager.bean.Confirmation;
import com.rewardomain.rewardmanager.bean.Dining;
import com.rewardomain.rewardmanager.bean.Reward;
import com.rewardomain.rewardmanager.exception.AccountContributionResponseError;
import com.rewardomain.rewardmanager.exception.ConfirmationError;
import com.rewardomain.rewardmanager.exception.RewardError;
import com.rewardomain.rewardmanager.proxy.AccountContributionProxy;
import com.rewardomain.rewardmanager.proxy.BenefitCalculationProxy;
import com.rewardomain.rewardmanager.repository.RewardRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/reward-manager")
public class RewardManagerController {
	
	@Autowired
	private BenefitCalculationProxy bcproxy;
	
	@Autowired
	private AccountContributionProxy acproxy;
	
	@Autowired
	private RewardRepository repository;

	@Autowired
	private Environment environment;

	private Logger logger = LoggerFactory.getLogger(RewardManagerController.class);
	
	public ResponseEntity<Reward> defaultRewardResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new RewardError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	public ResponseEntity<ConfirmationError> defaultConfirmationResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new ConfirmationError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	public ResponseEntity<AccountContributionResponseError> defaultACRResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new AccountContributionResponseError(404L, e.getMessage()), httpStatusCode
		);
	}

	@Retry(name = "reward-manager", fallbackMethod = "defaultRewardResponse")
	@GetMapping("/rewards")
    public ResponseEntity<List<Reward>> getAllRewards() {
		logger.info("Reward Manager Service call received");
	    return new ResponseEntity<List<Reward>>(repository.findAll(), HttpStatusCode.valueOf(200));
    }
	
	@Retry(name = "reward-manager", fallbackMethod = "defaultRewardResponse")
	@GetMapping("/rewards/{confirmationNumber}")
	public ResponseEntity<Reward> getReward(@PathVariable long confirmationNumber) {
		logger.info("Reward Manager Service call received");
	    // Chercher la récompense dans le repository
	    // Optional<Reward> optionalReward = repository.findByConfirmationNumber(confirmationNumber);
	    Optional<Reward> optionalReward;
	    
	    try {
	    	optionalReward = repository.findByConfirmationNumber(confirmationNumber);
    	} catch (RuntimeException e) {
    		throw new RuntimeException("Reward non trouvé pour le numero de confirmation : " + confirmationNumber + ".");
    	}
	    
	    String port = environment.getProperty("local.server.port");
	    optionalReward.get().setExecutionChain("reward-service instance:" + 
    			port + " == invoked => " + optionalReward.get().getExecutionChain()
    	);

	    if (optionalReward.isPresent()) {
	        // Si la récompense est trouvée, renvoyer la réponse avec le statut 200 OK
	        return ResponseEntity.ok(optionalReward.get());
	    } else {
	        // Si la récompense n'est pas trouvée, renvoyer la réponse avec le statut 404 Not Found
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@Retry(name = "reward-manager", fallbackMethod = "defaultRewardResponse")
	@GetMapping("/rewards/v2/{id}")
	public ResponseEntity<Reward> getRewardById(@PathVariable long id) {
		logger.info("Reward Manager Service call received");
	    // Chercher la récompense dans le repository
	    // Optional<Reward> optionalReward = repository.findById(id);
		Optional<Reward> optionalReward;
		
	    try {
	    	optionalReward = repository.findById(id);
    	} catch (RuntimeException e) {
    		throw new RuntimeException("Reward non trouvé pour l'id : " + id + ".");
    	}
	    
	    String port = environment.getProperty("local.server.port");
	    optionalReward.get().setExecutionChain("reward-service instance:" + 
    			port + " == invoked => " + optionalReward.get().getExecutionChain()
    	);
	    
	    if (optionalReward.isPresent()) {
	        // Si la récompense est trouvée, renvoyer la réponse avec le statut 200 OK
	        return ResponseEntity.ok(optionalReward.get());
	    } else {
	        // Si la récompense n'est pas trouvée, renvoyer la réponse avec le statut 404 Not Found
	        return ResponseEntity.notFound().build();
	    }
	}

	@Retry(name = "reward-manager", fallbackMethod = "defaultConfirmationResponse")
	@PostMapping("/rewards")
    public ResponseEntity<Confirmation> createReward(@RequestBody Dining dining) {
		logger.info("Reward Manager Service call received");
		long merchantNumber = dining.getMerchantNumber();
		double diningAmount = dining.getDiningAmount();
		
		// Benefit Calculation Service invocation
		// BenefitRestaurant benefitRestaurant = bcproxy.getBenefitRestaurant(merchantNumber, diningAmount);
		BenefitRestaurant benefitRestaurant;
		
		try {
			benefitRestaurant = bcproxy.getBenefitRestaurant(merchantNumber, diningAmount);
    	} catch (RuntimeException e) {
    		throw new RuntimeException("Restaurant " + merchantNumber + " non retrouvé");
    	}
		
		String port = environment.getProperty("local.server.port");
		benefitRestaurant.setExecutionChain("reward-service instance:" + 
    			port + " == invoked => " + benefitRestaurant.getExecutionChain()
    	);
    	
		Reward reward = new Reward(dining.getId() + 100, dining.getId() + 1000,
				benefitRestaurant.getAmount(), merchantNumber, 
				LocalDateTime.parse(dining.getDiningDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
		);
		
		repository.save(reward);
		
		Confirmation confirmation = new Confirmation(reward.getConfirmationNumber());
		confirmation.setExecutionChain(benefitRestaurant.getExecutionChain());
		return new ResponseEntity<Confirmation>(
    			confirmation, 
    			HttpStatusCode.valueOf(200)
    	);
    }
	
	@Retry(name = "reward-manager", fallbackMethod = "defaultRewardResponse")
	@DeleteMapping("/rewards/{id}")
	public ResponseEntity<Void> deleteReward(@PathVariable long id) {
	    logger.info("Reward Manager Service call received");
	    
	    // Chercher le reward dans le repository par son ID
	    Optional<Reward> optionalReward = repository.findById(id);
	    
	    if (optionalReward.isPresent()) {
	        // Si le reward est trouvé, supprimer le reward du repository
	        repository.deleteById(id);
	        // Retourner une réponse avec le statut 204 No Content
	        return ResponseEntity.noContent().build();
	    } else {
	        // Si le reward n'est pas trouvé, retourner une réponse avec le statut 404 Not Found
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@Retry(name = "reward-manager", fallbackMethod = "defaultACRResponse")
	@PostMapping("/rewards/{confirmationNumber}/distribute/{credit_card_number}")
	public ResponseEntity<AccountContributionResponse> distributeRewardToBeneficiaries(
			@PathVariable long confirmationNumber,
			@PathVariable("credit_card_number") String creditCardNumber
	) {
		logger.info("Reward Manager Service call received");
	    // Chercher la récompense dans le repository
	    // Optional<Reward> optionalReward = repository.findByConfirmationNumber(confirmationNumber);
	    Optional<Reward> optionalReward;
	    
	    try {
	    	optionalReward = repository.findByConfirmationNumber(confirmationNumber);
    	} catch (RuntimeException e) {
    		throw new RuntimeException("Reward confimNum : " + confirmationNumber + " non retrouvé");
    	}

	    if (optionalReward.isPresent()) {
	        // Si la récompense est trouvée, invoquez le microservice « account contribution service »
	        Reward reward = optionalReward.get();
	        double rewardAmount = reward.getAmount();
	        
	        // Obtenez le numéro de carte du client depuis le modèle Reward ou d'une autre source
	        // String creditCardNumber = "mettez-ici-le-numero-de-carte-du-client"; 
	        // String port = environment.getProperty("local.server.port");
	        // Appelez le microservice « account contribution service » pour distribuer la récompense aux bénéficiaires
	        // ResponseEntity<AccountContributionResponse> response = acproxy.distributeReward(creditCardNumber, rewardAmount);
	        ResponseEntity<AccountContributionResponse> response;
	        
	        try {
	        	response = acproxy.distributeReward(creditCardNumber, rewardAmount);
	    	} catch (RuntimeException e) {
	    		throw new RuntimeException("Reward distribution failed !");
	    	}
	        
	        // Retournez la réponse du microservice « account contribution service »
	        return response;
	    } else {
	        // Si la récompense n'est pas trouvée, renvoyer la réponse avec le statut 404 Not Found
	        return ResponseEntity.notFound().build();
	    }
	}

}
