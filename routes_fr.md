## Routes
### Account Contribution Service

#### Récupérer tous les comptes
- **URL** : `GET /account-contribution/accounts`
- **Description** : Cette route permet de récupérer tous les comptes.
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Liste de tous les comptes

#### Récupérer un compte spécifique
- **URL** : `GET /account-contribution/accounts/{account_number}`
- **Description** : Cette route permet de récupérer un compte spécifique en fonction de son numéro de compte.
- **Paramètres de chemin** :
  - `account_number` : Numéro du compte
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails du compte demandé

#### Créer un nouveau compte
- **URL** : `POST /account-contribution/accounts`
- **Description** : Cette route permet de créer un nouveau compte.
- **Corps de la requête** : Données du compte à créer
- **Réponse en cas de succès** :
  - Statut : 201 Created
  - Corps de la réponse : Détails du compte créé

#### Ajouter un bénéficiaire à un compte
- **URL** : `POST /account-contribution/accounts/{account_number}/beneficiaries`
- **Description** : Cette route permet d'ajouter un bénéficiaire à un compte spécifique.
- **Paramètres de chemin** :
  - `account_number` : Numéro du compte
- **Corps de la requête** : Données du bénéficiaire à ajouter
- **Réponse en cas de succès** :
  - Statut : 201 Created
  - Corps de la réponse : Détails du bénéficiaire ajouté

#### Mettre à jour un bénéficiaire
- **URL** : `PUT /account-contribution/accounts/beneficiaries/{id}`
- **Description** : Cette route permet de mettre à jour les détails d'un bénéficiaire.
- **Paramètres de chemin** :
  - `id` : Identifiant du bénéficiaire
- **Corps de la requête** : Nouvelles données du bénéficiaire
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails du bénéficiaire mis à jour

#### Récupérer tous les bénéficiaires d'un compte
- **URL** : `GET /account-contribution/accounts/{account_number}/beneficiaries`
- **Description** : Cette route permet de récupérer tous les bénéficiaires associés à un compte spécifique.
- **Paramètres de chemin** :
  - `account_number` : Numéro du compte
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Liste de tous les bénéficiaires du compte demandé

#### Mettre à jour une récompense liée à un compte
- **URL** : `PUT /account-contribution/accounts/{credit_card_number}/reward/{reward}`
- **Description** : Cette route permet de mettre à jour une récompense associée à un compte en utilisant le numéro de carte de crédit. Il effectue la distribution de la récompense aux bénéficiares associés si le total de toutes les parts des bénéficiares fait 100%.
- **Paramètres de chemin** :
  - `credit_card_number` : Numéro de carte de crédit
  - `reward` : Récompense à mettre à jour
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails de la récompense mise à jour

#### Récupérer toutes les cartes de crédit
- **URL** : `GET /account-contribution/accounts/creditcards`
- **Description** : Cette route permet de récupérer toutes les cartes de crédit associées aux comptes.
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Liste de toutes les cartes de crédit

#### Récupérer une carte de crédit spécifique
- **URL** : `GET /account-contribution/accounts/account-specific-credit-card`
- **Description** : Cette route permet de récupérer une carte de crédit spécifique associée à un compte.
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails de la carte de crédit demandée

### Benefit Calculation Service

#### Récupérer un avantage
- **URL** : `GET /benefit-calculation/v3/{merchantNumber}/{diningAmount}`
- **Description** : Cette route permet de calculer un avantage (le bénéfice qu'on se fait) en fonction du numéro du commerçant et du montant du dîner.
- **Paramètres de chemin** :
  - `merchantNumber` : Numéro du commerçant
  - `diningAmount` : Montant du dîner
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails de l'avantage calculé

### Benefit Restaurant Service

#### Récupérer un commerçant (restaurant)
- **URL** : `GET /benefit-restaurant/merchants/{merchant_number}`
- **Description** : Cette route permet de récupérer un commerçant spécifique en fonction de son numéro de commerçant.
- **Paramètres de chemin** :
  - `merchant_number` : Numéro du commerçant
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails du commerçant demandé

#### Récupérer tous les commerçants (restaurants)
- **URL** : `GET /benefit-restaurant/all`
- **Description** : Cette route permet de récupérer tous les commerçants.
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Liste de tous les commerçants

#### Ajouter un commerçant (restaurant)
- **URL** : `POST /benefit-restaurant/add`
- **Description** : Cette route permet d'ajouter un nouveau commerçant.
- **Corps de la requête** : Données du commerçant à ajouter
- **Réponse en cas de succès** :
  - Statut : 201 Created
  - Corps de la réponse : Détails du commerçant ajouté

#### Suspendre un commerçant (restaurant)
- **URL** : `PATCH /benefit-restaurant/suspend/{id}`
- **Description** : Cette route permet de suspendre un commerçant spécifique en fonction de son identifiant.
- **Paramètres de chemin** :
  - `id` : Identifiant du commerçant
- **Réponse en cas de succès** :
  - Statut : 204 No Content

#### Mettre à jour un commerçant (restaurant)

- **URL** : `PUT /benefit-restaurant/update/{id}`
- **Description** : Cette route permet de mettre à jour les détails d'un commerçant spécifique en fonction de son identifiant.
- **Paramètres de chemin** :
  - `id` : Identifiant du commerçant
- **Corps de la requête** : Nouvelles données du commerçant
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails du commerçant mis à jour

#### Mettre à jour partiellement un commerçant (restaurant)
- **URL** : `PATCH /benefit-restaurant/update/{id}`
- **Description** : Cette route permet de mettre à jour partiellement les détails d'un commerçant spécifique en fonction de son identifiant.
- **Paramètres de chemin** :
  - `id` : Identifiant du commerçant
- **Corps de la requête** : Nouvelles données partielles du commerçant
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails du commerçant mis à jour

### Reward Manager Service

#### Récupérer une récompense
- **URL** : `GET /reward-manager/rewards/{confirmationNumber}`
- **Description** : Cette route permet de récupérer une récompense spécifique en fonction de son numéro de confirmation.
- **Paramètres de chemin** :
  - `confirmationNumber` : Numéro de confirmation de la récompense
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails de la récompense demandée

#### Récupérer toutes les récompenses
- **URL** : `GET /reward-manager/rewards`
- **Description** : Cette route permet de récupérer toutes les récompenses.
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Liste de toutes les récompenses

#### Récupérer une récompense (version 2)
- **URL** : `GET /reward-manager/rewards/v2/{id}`
- **Description** : Cette route permet de récupérer une récompense spécifique en fonction de son identifiant.
- **Paramètres de chemin** :
  - `id` : Identifiant de la récompense
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails de la récompense demandée

#### Créer une nouvelle récompense
- **URL** : `POST /reward-manager/rewards`
- **Description** : Cette route permet de créer une nouvelle récompense.
- **Corps de la requête** : Données de la récompense à créer
- **Réponse en cas de succès** :
  - Statut : 201 Created
  - Corps de la réponse : Détails de la récompense créée

#### Distribuer une récompense aux bénéficiaires
- **URL** : `POST /reward-manager/rewards/{confirmationNumber}/distribute/{credit_card_number}`
- **Description** : Cette route permet de distribuer une récompense aux bénéficiaires en fonction du numéro de confirmation et du numéro de carte de crédit.
- **Paramètres de chemin** :
  - `confirmationNumber` : Numéro de confirmation de la récompense
  - `credit_card_number` : Numéro de carte de crédit
- **Réponse en cas de succès** :
  - Statut : 200 OK
  - Corps de la réponse : Détails de la distribution de récompense