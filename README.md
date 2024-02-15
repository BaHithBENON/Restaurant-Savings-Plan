# Restaurant-Savings-Plan

---------------

## Description

- EN: Microservices project in Java + Spring Boot to create a Restaurant Savings Plan system

## Prerequisites
- Java development environment **(Example: Eclipse or IntelliJ IDEA)**
- Java framework **SpringBoot**
- **Git** environment

## Exploration
In this directory, we observe five parts.
- **Centralization Configurations**
    It contains the git directory to centralize the **.properties** files of each microservice.
    * It is a local directory. So, you need to initialize git by doing **git init** then **git commit -m "your description"**
- **Server**
    Here we have the central server of the system. Please put in its **.properties** configuration file the path to your git centralization directory.
- **Microservices**
    Here, we have all the microservices that act independently of each other. Each microservice can be started without being influenced by another.
- **Load Balancing**
    Here, we have a **Gateway** that will serve as a gateway to manage API calls between microservice instances. If you start *3 instances* of the *reward-manager-service* microservice, Load Balancing will manage your request on the available instance rather than waiting for a single instance to do all the work, which would delay transactions. The **Eureka Registry** will register your services, their names, and everything else, then provide it to the **Gateway** for exploitation. It's like a bridge where requests will be launched, and each microservice will fetch what concerns it among the API calls, then return responses.

So, to use your microservices in a *client application*, you just have to use the routes provided by each microservice but through the *Gateway*.

### Ports

Open the *.properties* configuration files of each microservice to view and/or change the default port corresponding to that microservice.

    Default port of benefit-restaurant-service: 8100
    Default port of benefit-calculation-service: 8200
    Default port of reward-manager-service: 8300
    Default port of account-contribution-service: 8400
    Default port of gateway: 8765

### Startup Order

- ***spring-cloud-config-server***: This is the starting point for managing configurations and maintaining microservices.
- ***eureka-registry-naming-server***: To register the microservices.
- ***spring-cloud-gateway***: to serve as a gateway between clients and microservices through Eureka Registry
- ***The microservices***: They act independently of each other.

### Client (Test)
You can use **Postman** or **ThunderClient** to test the routes. Obviously, you can use whatever you want.

- Postman: https://www.postman.com/
- ThunderClient: https://www.thunderclient.com/

### Test Mobile Client Application:
A **Flutter** mobile application is developed to interact with the system. You can compile the code to visually test the results. 

Git repository: https://github.com/BaHithBENON/Restaurant-Savings-Plan---ClientApp.git

**Note:** If you use the application as provided, do not change any configuration ports, otherwise modify the routes in the client application to coordinate your calls.

**Desktop Tip:** Compile the desktop version and resize the window to the width of a mobile phone.


---------------


## Description

- FR : Projet de microservices en Java + Spring Boot pour créer un système de plan d'épargne restaurant

## Pré-requis
- Environnement de développement Java **(Exemple : Eclipse ou IntelliJ IDEA)**
- Framework Java **SpringBoot**
- Environnement **Git**

## Exploration
Dans ce répertoire, nous observons cinq parties.
- **Centralization Configurations**
    Il contient le répertoire git permettant de centraliser les fichiers **.properties** de chaque microservice.
    * C'est un répertoire local. Donc il faut initialiser git en faisant **git init** puis **git commit -m "votre description"**
- **Server**
    Nous avons ici le serveur central du système. Veuillez mettre dans son fichier de configuration **.properties**, le chemin vers votre répertoire de centralisation git.
- **Micro services**
    Ici, nous avons tous les micro-services qui agissent indépendamment des autres. Chaque micro-service peut être démarré sans être influencé par un autre.
- **Load Balancing**
    Ici, nous avons un **Gateway** qui servira de passerelle afin de gérer les appels API entre les instances des microservices. Si vous démarrez *3 instances* du microservice *reward-manager-service*, le Load Balancing permettra de gérer votre requête sur l'instance disponible plutôt que d'attendre qu'une seule instance fasse tout le travail, ce qui retarderait les transactions. Le **Eureka Registry** va enregistrer vos services, leurs noms et tout ce qui va avec puis le fournir au **Gateway** pour l'exploitation. Il est comme un pont dans lequel les requêtes seront lancées et chaque microservice ira récupérer ce qui le concerne parmi les appels API, puis retourner des réponses.

Donc pour utiliser vos microservices dans une *application client*, vous n'aurez qu'à utiliser les routes fournies par chaque microservice mais à travers le *Gateway*.

### Les ports

Ouvrez les fichiers de configurations *.properties* de chaque microservice pour voir et/ou changer le port par défaut correspondant à ce microservice.

    Port par défaut de benefit-restaurant-service: 8100
    Port par défaut de benefit-calculation-service: 8200
    Port par défaut de reward-manager-service: 8300
    Port par défaut de account-contribution-service: 8400
    Port par défaut de gateway: 8765

### Ordre de démarrage 

- ***spring-cloud-config-server*** : C'est le point de départ pour gérer les configurations et maintenir les microservices.
- ***eureka-registry-naming-server*** : Pour enregistrer les microservices.
- ***spring-cloud-gateway*** : pour servir de passerelle entre les clients et microservices à travers Eureka Registry
- ***Les microservices*** : Ils agissent indépendamment des autres.

### Client (Test)
Vous pouvez utiliser **Postman** ou **ThunderClient** pour tester les routes. Évidemment, vous pouvez utiliser ce que vous voulez.

- Postman : https://www.postman.com/
- ThunderClient : https://www.thunderclient.com/

### Application Mobile Client de Test :
Une application mobile **Flutter** est développée pour interagir avec le système. Vous pouvez compiler le code afin de tester visuellement les résultats. 

Répertoire git : https://github.com/BaHithBENON/Restaurant-Savings-Plan---ClientApp.git

**NB :** Si vous utilisez l'application telle que fournie, ne changez aucun port de configuration, sinon modifiez les routes dans l'application client afin de coordonner vos appels.

**Astuce Desktop :** Compilez la version desktop et réduisez la fenêtre à la largeur d'un téléphone mobile.
