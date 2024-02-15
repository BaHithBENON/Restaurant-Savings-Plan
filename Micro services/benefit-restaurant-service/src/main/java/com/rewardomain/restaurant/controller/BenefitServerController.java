package com.rewardomain.restaurant.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.restaurant.bean.BenefitServer;
import com.rewardomain.restaurant.bean.Restaurant;
import com.rewardomain.restaurant.bean.RestaurantError;
import com.rewardomain.restaurant.configuration.Configuration;
import com.rewardomain.restaurant.repository.RestaurantRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class BenefitServerController {
	
	@Autowired
	private Configuration configuration;
	
	@Autowired
	private RestaurantRepository repository;
	
	@Autowired
	private Environment environment;
	
	private Logger logger = LoggerFactory.getLogger(BenefitServerController.class);
	
	@GetMapping("/benefitserver")
	public BenefitServer getBenefitServer() {
		return new BenefitServer(configuration.getType(), configuration.getName());
	}
	
	public ResponseEntity<Restaurant> defaultResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new RestaurantError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	@Retry(name = "benefit-restaurant", fallbackMethod = "defaultResponse")
	@GetMapping("/benefit-restaurant/merchants/{merchant_number}")
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable long merchant_number) {
		
		logger.info("Benefit Restaurant Service call received");
		
		Restaurant restaurant = repository.findByMerchantNumber(merchant_number);
		
		String port = environment.getProperty("local.server.port");
		
		HttpStatusCode httpStatusCode;
		
		if(restaurant == null) {
			//restaurant = new Restaurant();
			//httpStatusCode = HttpStatusCode.valueOf(404);
			throw new RuntimeException("Restaurant non trouvé pour le numero : " + merchant_number + ".");
		}
		restaurant.setExecutionChain("restaurant-service instance: " + port);
		httpStatusCode = HttpStatusCode.valueOf(200);
		//return restaurant;
		return ResponseEntity.status(httpStatusCode).body(restaurant);
	}
	
	@GetMapping("/benefit-restaurant/all")
    public List<Restaurant> getAllRestaurants() {
        // Récupérer tous les restaurants de la base de données
        return repository.findAll();
    }
	
	@PostMapping("/benefit-restaurant/add")
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant) {
		if(restaurantIsValid(restaurant)) {
			try {
				repository.save(restaurant);
				return ResponseEntity.ok("Restaurant ajouté avec succès.");
			} catch (DataIntegrityViolationException | ConstraintViolationException e) {
				// Gérer l'erreur de contrainte dans la base de données
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'ajout du restaurant : " + e.getMessage());
			} catch (Exception e) {
				// Gérer d'autres exceptions
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne lors de l'ajout du restaurant : " + e.getMessage());
			}
		} else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les données du restaurant ne sont pas valides.");
	    }
    }
	
	@PatchMapping("/benefit-restaurant/suspend/{id}")
	public ResponseEntity<String> suspendRestaurant(@PathVariable Long id, @RequestBody boolean suspend) {
	    Optional<Restaurant> optionalRestaurant = repository.findById(id);

	    if (optionalRestaurant.isPresent()) {
	        Restaurant restaurant = optionalRestaurant.get();
	        restaurant.setSuspended(suspend);
	        repository.save(restaurant);
	        return ResponseEntity.ok("État de suspension mis à jour avec succès.");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant non trouvé.");
	    }
	}
	
	// Mettez à jour le restaurant entier
    @PutMapping("/benefit-restaurant/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        Optional<Restaurant> existingRestaurantOptional = repository.findById(id);

        if (existingRestaurantOptional.isPresent()) {
            Restaurant existingRestaurant = existingRestaurantOptional.get();

            // Mettez à jour les propriétés du restaurant existant avec celles du restaurant mis à jour
            existingRestaurant.setMerchantNumber(updatedRestaurant.getMerchantNumber());
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setBenefitPercentage(updatedRestaurant.getBenefitPercentage());
            existingRestaurant.setBenefitAvailabilityPolicy(updatedRestaurant.getBenefitAvailabilityPolicy());
            existingRestaurant.setSuspended(updatedRestaurant.isSuspended());

            // Enregistrez les modifications dans la base de données
            repository.save(existingRestaurant);

            return new ResponseEntity<>(existingRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Mettez à jour une partie spécifique du restaurant (par exemple, le pourcentage de bénéfice)
    @PatchMapping("/benefit-restaurant/update/{id}")
    public ResponseEntity<Restaurant> partialUpdateRestaurant(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Restaurant> existingRestaurantOptional = repository.findById(id);

        if (existingRestaurantOptional.isPresent()) {
            Restaurant existingRestaurant = existingRestaurantOptional.get();

            // Mettez à jour seulement les propriétés spécifiées dans la requête partielle
            if (updates.containsKey("benefitPercentage")) {
                existingRestaurant.setBenefitPercentage((Double) updates.get("benefitPercentage"));
            }

            if (updates.containsKey("benefitAvailabilityPolicy")) {
            	existingRestaurant.setBenefitAvailabilityPolicy((String) updates.get("benefitAvailabilityPolicy"));
            }
            
            if (updates.containsKey("name")) {
            	existingRestaurant.setBenefitAvailabilityPolicy((String) updates.get("name"));
            }

            // Enregistrez les modifications dans la base de données
            repository.save(existingRestaurant);

            return new ResponseEntity<>(existingRestaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	
	private boolean restaurantIsValid(Restaurant restaurant) {
	    // Implémenter la logique pour vérifier si les champs obligatoires sont présents
	    return restaurant.getName() != null && !restaurant.getName().isEmpty()
	    		&& restaurant.getBenefitAvailabilityPolicy() != null && !restaurant.getBenefitAvailabilityPolicy().isEmpty()
	    		&& restaurant.getBenefitPercentage() != null && restaurant.getMerchantNumber() != null;
	}
}
