## Routes
### Account Contribution Service

#### Get all accounts
- **URL** : `GET /account-contribution/accounts`
- **Description** : This route retrieves all accounts.
- **Successful Response** :
  - Status : 200 OK
  - Response Body : List of all accounts

#### Get a specific account
- **URL** : `GET /account-contribution/accounts/{account_number}`
- **Description** : This route retrieves a specific account based on its account number.
- **Path Parameters** :
  - `account_number` : Account number
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the requested account

#### Create a new account
- **URL** : `POST /account-contribution/accounts`
- **Description** : This route creates a new account.
- **Request Body** : Data of the account to create
- **Successful Response** :
  - Status : 201 Created
  - Response Body : Details of the created account

#### Add a beneficiary to an account
- **URL** : `POST /account-contribution/accounts/{account_number}/beneficiaries`
- **Description** : This route adds a beneficiary to a specific account.
- **Path Parameters** :
  - `account_number` : Account number
- **Request Body** : Data of the beneficiary to add
- **Successful Response** :
  - Status : 201 Created
  - Response Body : Details of the added beneficiary

#### Update a beneficiary
- **URL** : `PUT /account-contribution/accounts/beneficiaries/{id}`
- **Description** : This route updates the details of a beneficiary.
- **Path Parameters** :
  - `id` : Beneficiary ID
- **Request Body** : New data of the beneficiary
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the updated beneficiary

#### Get all beneficiaries of an account
- **URL** : `GET /account-contribution/accounts/{account_number}/beneficiaries`
- **Description** : This route retrieves all beneficiaries associated with a specific account.
- **Path Parameters** :
  - `account_number` : Account number
- **Successful Response** :
  - Status : 200 OK
  - Response Body : List of all beneficiaries of the requested account

#### Update a reward linked to an account
- **URL** : `PUT /account-contribution/accounts/{credit_card_number}/reward/{reward}`
- **Description** : This route updates a reward associated with an account using the credit card number. It distributes the reward to associated beneficiaries if the total of all beneficiaries' shares is 100%.
- **Path Parameters** :
  - `credit_card_number` : Credit card number
  - `reward` : Reward to update
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the updated reward

#### Get all credit cards
- **URL** : `GET /account-contribution/accounts/creditcards`
- **Description** : This route retrieves all credit cards associated with accounts.
- **Successful Response** :
  - Status : 200 OK
  - Response Body : List of all credit cards

#### Get a specific credit card
- **URL** : `GET /account-contribution/accounts/account-specific-credit-card`
- **Description** : This route retrieves a specific credit card associated with an account.
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the requested credit card

### Benefit Calculation Service

#### Get a benefit
- **URL** : `GET /benefit-calculation/v3/{merchantNumber}/{diningAmount}`
- **Description** : This route calculates a benefit (profit) based on the merchant number and the dining amount.
- **Path Parameters** :
  - `merchantNumber` : Merchant number
  - `diningAmount` : Dining amount
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the calculated benefit

### Benefit Restaurant Service

#### Get a merchant (restaurant)
- **URL** : `GET /benefit-restaurant/merchants/{merchant_number}`
- **Description** : This route retrieves a specific merchant based on its merchant number.
- **Path Parameters** :
  - `merchant_number` : Merchant number
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the requested merchant

#### Get all merchants (restaurants)
- **URL** : `GET /benefit-restaurant/all`
- **Description** : This route retrieves all merchants.
- **Successful Response** :
  - Status : 200 OK
  - Response Body : List of all merchants

#### Add a merchant (restaurant)
- **URL** : `POST /benefit-restaurant/add`
- **Description** : This route adds a new merchant.
- **Request Body** : Data of the merchant to add
- **Successful Response** :
  - Status : 201 Created
  - Response Body : Details of the added merchant

#### Suspend a merchant (restaurant)
- **URL** : `PATCH /benefit-restaurant/suspend/{id}`
- **Description** : This route suspends a specific merchant based on its ID.
- **Path Parameters** :
  - `id` : Merchant ID
- **Successful Response** :
  - Status : 204 No Content

#### Update a merchant (restaurant)
- **URL** : `PUT /benefit-restaurant/update/{id}`
- **Description** : This route updates the details of a specific merchant based on its ID.
- **Path Parameters** :
  - `id` : Merchant ID
- **Request Body** : New data of the merchant
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the updated merchant

#### Partially update a merchant (restaurant)
- **URL** : `PATCH /benefit-restaurant/update/{id}`
- **Description** : This route partially updates the details of a specific merchant based on its ID.
- **Path Parameters** :
  - `id` : Merchant ID
- **Request Body** : Partial new data of the merchant
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the updated merchant

### Reward Manager Service

#### Get a reward
- **URL** : `GET /reward-manager/rewards/{confirmationNumber}`
- **Description** : This route retrieves a specific reward based on its confirmation number.
- **Path Parameters** :
  - `confirmationNumber` : Confirmation number of the reward
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the requested reward

#### Get all rewards
- **URL** : `GET /reward-manager/rewards`
- **Description** : This route retrieves all rewards.
- **Successful Response** :
  - Status : 200 OK
  - Response Body : List of all rewards

#### Get a reward (version 2)
- **URL** : `GET /reward-manager/rewards/v2/{id}`
- **Description** : This route retrieves a specific reward based on its ID.
- **Path Parameters** :
  - `id` : Reward ID
- **Successful Response** :
  - Status : 

200 OK
  - Response Body : Details of the requested reward

#### Create a new reward
- **URL** : `POST /reward-manager/rewards`
- **Description** : This route creates a new reward.
- **Request Body** : Data of the reward to create
- **Successful Response** :
  - Status : 201 Created
  - Response Body : Details of the created reward

#### Distribute a reward to beneficiaries
- **URL** : `POST /reward-manager/rewards/{confirmationNumber}/distribute/{credit_card_number}`
- **Description** : This route distributes a reward to beneficiaries based on the confirmation number and the credit card number.
- **Path Parameters** :
  - `confirmationNumber` : Confirmation number of the reward
  - `credit_card_number` : Credit card number
- **Successful Response** :
  - Status : 200 OK
  - Response Body : Details of the reward distribution