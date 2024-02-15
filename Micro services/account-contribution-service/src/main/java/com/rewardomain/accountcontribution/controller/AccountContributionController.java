package com.rewardomain.accountcontribution.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.accountcontribution.BeneficiaryRepository;
import com.rewardomain.accountcontribution.CreditCardRepository;
import com.rewardomain.accountcontribution.bean.Account;
import com.rewardomain.accountcontribution.bean.AccountContributionRequest;
import com.rewardomain.accountcontribution.bean.AccountContributionResponse;
import com.rewardomain.accountcontribution.bean.Beneficiary;
import com.rewardomain.accountcontribution.bean.CreditCard;
import com.rewardomain.accountcontribution.configuration.Distribution;
import com.rewardomain.accountcontribution.exception.AccountContributionResponseError;
import com.rewardomain.accountcontribution.exception.AccountError;
import com.rewardomain.accountcontribution.exception.BeneficiaryError;
import com.rewardomain.accountcontribution.exception.CreditCardError;
import com.rewardomain.accountcontribution.repository.AccountRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/account-contribution")
public class AccountContributionController {

	@Autowired
	private CreditCardRepository creditCardRepository; 
	
	@Autowired
	private AccountRepository accountRepository; 
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository; 
	
	@Autowired
	private Distribution distribution; 
	
	@Autowired
	private Environment environment;

	private Logger logger = LoggerFactory.getLogger(AccountContributionController.class);
	
	
	public ResponseEntity<AccountContributionResponseError> defaultACRResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new AccountContributionResponseError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	public ResponseEntity<AccountError> defaultAccountResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new AccountError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	public ResponseEntity<BeneficiaryError> defaultBeneficiaryResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new BeneficiaryError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	public ResponseEntity<CreditCardError> defaultCreditCardResponse(RuntimeException e) {
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
		return new ResponseEntity<>(
				new CreditCardError(404L, e.getMessage()), httpStatusCode
		);
	}
	
	// Créer un compte
	@Retry(name = "account-contribution", fallbackMethod = "defaultACRResponse")
	@PostMapping("/accounts")
    public ResponseEntity<AccountContributionResponse> createAccount(
    		@RequestBody AccountContributionRequest request
    ) {
		logger.info("Account Contribution Service call received");
		
		CreditCard creditCard = new CreditCard(request.getCcnumber());
		Account account = new Account(request.getName(), request.getAnumber());
		account.setCreditCard(creditCard);
		
		try {
			creditCardRepository.save(creditCard);
			accountRepository.save(account);
		} catch (RuntimeException e) {
    		throw new RuntimeException("Account Created failed !");
    	}
		
		String port = environment.getProperty("local.server.port");
		AccountContributionResponse acr = new AccountContributionResponse(201, "Account created.");
		acr.setExecutionChain("account-distrubution-service instance: " + port);
		return new ResponseEntity<>(
    			acr, HttpStatusCode.valueOf(200)
    	);
    }
	
	// Rechercher un compte à partir du numéro de compte
	@Retry(name = "account-contribution", fallbackMethod = "defaultAccountResponse")
	@GetMapping("/accounts/{account_number}")
	public ResponseEntity<Account> getAccount(
			@PathVariable("account_number") String number
	) {
		logger.info("Account Contribution Service call received");
		Account account;
	    try {
	    	account = accountRepository.findByNumber(number);	    	
	    } catch (RuntimeException e) {
    		throw new RuntimeException("Account Getting failed !");
    	}
	    if(account != null) {
	    	return new ResponseEntity<Account>(account, HttpStatusCode.valueOf(200));
	    } 
    	return new ResponseEntity<Account>(account, HttpStatusCode.valueOf(404));
	}
	
	// Rechercher tous les comptes
	@Retry(name = "account-contribution", fallbackMethod = "defaultAccountResponse")
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAccounts() {
		logger.info("Account Contribution Service call received");
	    return new ResponseEntity<List<Account>>(accountRepository.findAll(), HttpStatusCode.valueOf(200));
	}
	
	// Créer d'un bénéficiaire
	@PostMapping("/accounts/{account_number}/beneficiaries")
	@Retry(name = "account-contribution", fallbackMethod = "defaultACRResponse")
    public ResponseEntity<AccountContributionResponse> createBeneficiary(
    		@RequestBody AccountContributionRequest request,
    		@PathVariable("account_number") String anumber
    ) {
		logger.info("Account Contribution Service call received");
		
		Beneficiary beneficiary = new Beneficiary(request.getPercentage(), request.getName());
		Account account;
		try {
			account = accountRepository.findByNumber(anumber);
		} catch (RuntimeException e) {
    		throw new RuntimeException("Account not found failed !");
    	}

		String port = environment.getProperty("local.server.port");
		if(account != null) {
			beneficiary.setAccount(account);
			
			try {
				beneficiaryRepository.save(beneficiary);				
			} catch (RuntimeException e) {
	    		throw new RuntimeException("Create beneficiary failed !");
	    	}
			
			AccountContributionResponse acr = new AccountContributionResponse(201, "Beneficiary created and added.");
			acr.setExecutionChain("account-distrubution-service instance: " + port);
			return new ResponseEntity<>(acr, HttpStatusCode.valueOf(201));
		}

		AccountContributionResponse acr = new AccountContributionResponse(404, "Account not found.");
		acr.setExecutionChain("account-distrubution-service instance: " + port);
		return new ResponseEntity<>(acr, HttpStatusCode.valueOf(404));
    }
	
	
	// Modifier le taux d'allocation d'un bénéficiaire
	@Retry(name = "account-contribution", fallbackMethod = "defaultACRResponse")
	@PutMapping("/accounts/beneficiaries/{id}")
    public ResponseEntity<AccountContributionResponse> updateBeneficiary(
    		@RequestBody AccountContributionRequest request,
    		@PathVariable long id
    ) {
		logger.info("Account Contribution Service call received");
		
		Optional<Beneficiary> beneficiary;
		try {
			beneficiary = beneficiaryRepository.findById(id);			
		} catch (RuntimeException e) {
    		throw new RuntimeException("Beneficairy Retreived failed !");
    	}
		
		String port = environment.getProperty("local.server.port");
		
		if(beneficiary.isPresent()) {
			beneficiary.get().setPercentage(request.getPercentage());
			beneficiary.get().setName(request.getName());
			
			try {
				beneficiaryRepository.save(beneficiary.get());				
			} catch (RuntimeException e) {
	    		throw new RuntimeException("beneficairy Update failed !");
	    	}
			
			AccountContributionResponse acr = new AccountContributionResponse(201, "Beneficiary updated.");
			acr.setExecutionChain("account-distrubution-service instance: " + port);
			return new ResponseEntity<>(acr, HttpStatusCode.valueOf(201));
		}
		
		AccountContributionResponse acr = new AccountContributionResponse(404, "Beneficiary not found.");
		acr.setExecutionChain("account-distrubution-service instance: " + port);
		return new ResponseEntity<>(acr, HttpStatusCode.valueOf(404)
    	);
    }
	
	// Liste des bénéficiaires d’un compte spécifique …
	@Retry(name = "account-contribution", fallbackMethod = "defaultBeneficiaryResponse")
	@GetMapping("/accounts/{account_number}/beneficiaries")
	public ResponseEntity<List<Beneficiary>> getAccounts(@PathVariable("account_number") String number) {
		logger.info("Account Contribution Service call received");
		
		Account account;
		try {			
			account = accountRepository.findByNumber(number);
		} catch (RuntimeException e) {
    		throw new RuntimeException("Account getting failed !");
    	}
		
		if(account != null) {
			return new ResponseEntity<List<Beneficiary>>(account.getBeneficaires(), HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<List<Beneficiary>>(
    		new ArrayList<>(), HttpStatusCode.valueOf(404)
    	);
	}
	
	// Distribution de la récompense à tous les bénéficiaires d’un compte spécifique
	@Retry(name = "account-contribution", fallbackMethod = "defaultACRResponse")
	@PutMapping("/accounts/{credit_card_number}/reward/{reward}")
    public ResponseEntity<AccountContributionResponse> distributeReward(
    		@PathVariable("credit_card_number") String number,
    		@PathVariable double reward
    ) {
		logger.info("Account Contribution Service call received");
		
		Optional<Account> optional;
		try {
			optional = accountRepository.findByCreditCard_Number(number);
		} catch (RuntimeException e) {
    		throw new RuntimeException("Account getting failed !");
    	}
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(
	    		new AccountContributionResponse(404, "Account not found."), HttpStatusCode.valueOf(404)
	    	);
		} else {
			Account account = optional.get();
			
			String port = environment.getProperty("local.server.port");
			
			if(account.isValid()) {
				
				System.out.print("lollllllllllll");
				
				account.setBenefits(account.getBenefits() + reward);
				List<Beneficiary> beneficiaries = account.getBeneficaires();
				
				if(distribution.distributeIsPossible(beneficiaries)) {
					distribution.distribute(beneficiaries, reward);
					
					try {
						accountRepository.save(account);
					} catch (RuntimeException e) {
						throw new RuntimeException("Distribution failed !");
					}
					
					AccountContributionResponse acr = new AccountContributionResponse(200, "Reward distributed.");
					acr.setExecutionChain("account-distrubution-service instance: " + port);
					return new ResponseEntity<>(acr, HttpStatusCode.valueOf(200));
				} else {
					AccountContributionResponse acr = new AccountContributionResponse(422, "Unprocessable distribution.");
					acr.setExecutionChain("account-distrubution-service instance: " + port);
					return new ResponseEntity<>(acr, HttpStatusCode.valueOf(422));
				}
			} else {
				
				AccountContributionResponse acr = new AccountContributionResponse(403, "Account is invalid..");
				acr.setExecutionChain("account-distrubution-service instance: " + port);
				return new ResponseEntity<>(acr, HttpStatusCode.valueOf(404));
			}
		}
    }
	
	// Méthode pour récupérer toutes les cartes de crédit
	@Retry(name = "account-contribution", fallbackMethod = "defaultCreditCardResponse")
	@GetMapping("/accounts/creditcards")
	public ResponseEntity<List<CreditCard>> getAllCreditCards() {
	    logger.info("Account Contribution Service call received for retrieving all credit cards");
	    List<CreditCard> creditCards = creditCardRepository.findAll();
	    return new ResponseEntity<>(creditCards, HttpStatusCode.valueOf(200));
	}
	
	// Méthode pour récupérer la carte de crédit liée à un compte spécifique
	@Retry(name = "account-contribution", fallbackMethod = "defaultCreditCardResponse")
	@PostMapping("/accounts/account-specific-credit-card")
	public ResponseEntity<CreditCard> getCreditCardForAccount(@RequestBody AccountContributionRequest request) {
	    logger.info("Account Contribution Service call received for retrieving credit card for specific account");
	    
	    // Récupérer le numéro de compte et le nom du body de la requête
	    String accountNumber = request.getAnumber();
	    // String ownerName = request.getName();
	    
	    // Récupérer le compte correspondant
	    Account account;
	    try {
	        account = accountRepository.findByNumber(accountNumber);
	    } catch (RuntimeException e) {
	        throw new RuntimeException("Account not found !");
	    }
	    
	    if (account != null) {
	        CreditCard creditCard = account.getCreditCard();
	        if (creditCard != null) {
	            return new ResponseEntity<>(creditCard, HttpStatusCode.valueOf(200));
	        } else {
	            throw new RuntimeException("Credit card not found for the specified account!");
	        }
	    } else {
	        throw new RuntimeException("Account not found !");
	    }
	}
}
