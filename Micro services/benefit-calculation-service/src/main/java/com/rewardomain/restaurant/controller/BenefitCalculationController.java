package com.rewardomain.restaurant.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rewardomain.restaurant.bean.BenefitRestaurant;
import com.rewardomain.restaurant.bean.BenefitRestaurantDetails;
import com.rewardomain.restaurant.bean.BenefitRestaurantError;
import com.rewardomain.restaurant.bean.Restaurant;
import com.rewardomain.restaurant.configuration.Configuration;
import com.rewardomain.restaurant.interfaces.BenefitRestaurantProxy;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/benefit-calculation")
public class BenefitCalculationController {
	
	@Autowired
	private Configuration configuration;
	@Autowired
	private final RestTemplate restTemplate;
	
	@Autowired
	private BenefitRestaurantProxy proxy;
	
	@Autowired
	private Environment environment;
	
	private Logger logger = LoggerFactory.getLogger(BenefitCalculationController.class);

    public BenefitCalculationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/v1/{merchantNumber}/{diningAmount}")
    public ResponseEntity<String> calculateBenefit(@PathVariable Long merchantNumber, @PathVariable Double diningAmount) {
        // Appel au microservice "Benefit Restaurant Service" pour obtenir le taux de rémunération
        String benefitServiceUrl = configuration.getBenefitRestaurantServiceUrl() + "/benefit-restaurant/merchants/{merchantNumber}";
        ResponseEntity<Restaurant> response = restTemplate.getForEntity(benefitServiceUrl, Restaurant.class, merchantNumber);

        if (response.getStatusCode() == HttpStatus.OK) {
            Restaurant restaurant = response.getBody();
            Double benefitPercentage = restaurant.getBenefitPercentage();

            // Calcul de la récompense en fonction du taux de rémunération et du montant du dîner
            Double benefitAmount = diningAmount * (benefitPercentage / 100);

            return ResponseEntity.ok("Benefit Amount: " + benefitAmount + " XOF");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error retrieving benefit information");
        }
    }
    
    @GetMapping("/v2/{merchantNumber}/{diningAmount}")
    public ResponseEntity<BenefitRestaurant> getBenefitAmount(
    		@PathVariable("merchantNumber") long number, @PathVariable("diningAmount") double amount) {
        
    	HashMap<String, String> uriVariables = new HashMap<>();
    	uriVariables.put("merchantNumber", String.valueOf(number));
    	
    	String benefitServiceUrl = configuration.getBenefitRestaurantServiceUrl() + "/benefit-restaurant/merchants/{merchantNumber}";
    	
    	ResponseEntity<BenefitRestaurantDetails> response = 
    			restTemplate.getForEntity(benefitServiceUrl, BenefitRestaurantDetails.class, uriVariables);
    	
    	
    	BenefitRestaurantDetails restaurantBenefitDetails = response.getBody();
    	
    	String port = environment.getProperty("local.server.port");
    	restaurantBenefitDetails.setExecutionChain("calculation-service instance:" + 
    			port + " == invoked => " + restaurantBenefitDetails.getExecutionChain()
    	);
    	
    	BenefitRestaurant benefitRestaurant = 
    			new BenefitRestaurant(amount, restaurantBenefitDetails.getPercentage());
    	benefitRestaurant.setExecutionChain(restaurantBenefitDetails.getExecutionChain());
    	
    	return new ResponseEntity<BenefitRestaurant>(
    			benefitRestaurant, HttpStatusCode.valueOf(200));
    }
    
    @Retry(name = "benefit-calculation", fallbackMethod = "defaultResponse")
    @GetMapping("/v3/{merchantNumber}/{diningAmount}")
    public ResponseEntity<BenefitRestaurant> getBenefitAmountFeign(
    		@PathVariable("merchantNumber") long number, @PathVariable("diningAmount") double amount) {
    	
    	logger.info("Benefit Calculation Service call received");
    	BenefitRestaurantDetails restaurantBenefitDetails;
    	
    	try {
    		restaurantBenefitDetails = proxy.getBenefitRestaurantDetails(number);
    	} catch (RuntimeException e) {
    		throw new RuntimeException("Restaurant non trouvé pour le numero : " + number + ".");
    	}
    	
    	String port = environment.getProperty("local.server.port");
    	restaurantBenefitDetails.setExecutionChain("calculation-service instance:" + 
    			port + " == invoked => " + restaurantBenefitDetails.getExecutionChain()
    	);
    	BenefitRestaurant benefitRestaurant = 
    		new BenefitRestaurant(amount, restaurantBenefitDetails.getPercentage());
    	benefitRestaurant.setExecutionChain(restaurantBenefitDetails.getExecutionChain());
    	
    	return new ResponseEntity<BenefitRestaurant>(
    			benefitRestaurant, HttpStatusCode.valueOf(200)
    	);
    }
    
    public ResponseEntity<BenefitRestaurant> defaultResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new BenefitRestaurantError(404L, e.getMessage()), httpStatusCode
		);
	}
}
