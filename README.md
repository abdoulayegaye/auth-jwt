# Gestion de l'authentification et de l'autorisation des utilisateurs

Ce projet est une application Spring Boot conçue pour gérer l'authentification et l'autorisation des utilisateurs. 
L'application utilise Spring Security pour sécuriser les endpoints et permettre une gestion efficace des rôles et des permissions des utilisateurs.

## Fonctionnalités

- **Authentification des utilisateurs** : Implémentation de l'authentification basée sur JWT (JSON Web Token) pour sécuriser les API REST.
- **Autorisation des utilisateurs** : Gestion des rôles et des permissions avec Spring Security pour restreindre l'accès aux différentes ressources en fonction des rôles.
- **Enregistrement des utilisateurs** : API REST pour l'inscription des nouveaux utilisateurs avec validation des données.
- **Gestion des rôles** : Attribution des rôles aux utilisateurs (ex. ADMIN, USER) avec des privilèges spécifiques pour chaque rôle.
- **Génération de code OTP** : Génération de code OTP(One-Time Password) pour la validation d'utilisateur lors de la connexion, la réinitialisation de mot de passe, etc.
- **Réinitialisation du mot de passe** : Implémentation d'un système de réinitialisation de mot de passe via email.
- **Documentation Swagger** : Implémentation de Swagger pour une bonne documentation et une bonne exploration des APIs.

## Prérequis

- JDK 17 ou +
- Maven 3.6 ou +
- Un IDE compatible avec Java (IntelliJ IDEA, Eclipse, etc.)

## Installation

1. Clonez le dépôt :
   
       git clone https://github.com/abdoulayegaye/auth-jwt.git
       cd auth-jwt
   
2. Compilez et packagez le projet avec Maven :

       mvn clean install
   
3. Exécutez l'application :

       mvn spring-boot:run

   
## Configuration

    L'application utilise un fichier application.properties ou application.yml pour la configuration. 
    Les paramètres principaux à configurer sont :
    
    Base de données : Configurez la connexion à la base de données (par défaut, MySQL est utilisé).
    Sécurité : Configurez les paramètres de sécurité comme le secret JWT, l'expiration des tokens, etc.
    Email : Configurez les propriétés pour l'envoi d'emails si la fonctionnalité de réinitialisation de mot de passe est activée.
    Exemple de configuration (application.properties) :

    # MySQL settings
    spring.datasource.url=jdbc:mysql://localhost:3306/auth_jwt_db
    spring.datasource.username=root
    spring.datasource.password=
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.database=mysql
    spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
    spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

    # JWT settings
    jwt.secret=your-secret-key
    jwt.expirationMs=86400000

    # Email settings
    spring.mail.host=smtp.example.com
    spring.mail.port=587
    spring.mail.username=your-email@example.com
    spring.mail.password=your-email-password

    # OPEN API settings
    springdoc.api-docs.path=/v3/api-docs
    springdoc.swagger-ui.path=/swagger-ui.html
    springdoc.swagger-ui.persist-authorization=true


## Endpoints API

Voici une liste des principaux endpoints exposés par l'application :

- **POST /api/v1/account/users** : Inscription d'un nouvel utilisateur.
- **POST /api/v1/auth/login** : Authentification de l'utilisateur et génération d'un token JWT.
- **GET /api/v1/account/users** : Accès aux informations des utilisateurs (réservé aux administrateurs).
- **POST /api/v1/auth/login-validate-otp** : Validation de code OTP.
- **POST /api/v1/auth/reset-password** : Demande de réinitialisation de mot de passe.

## Technologies utilisées

- **Spring Boot** : Cadre principal pour le développement de l'application.
- **Spring Security** : Gestion de l'authentification et de l'autorisation.
- **JWT** : Gestion des tokens pour l'authentification.
- **Hibernate** : ORM utilisé pour la gestion des données persistantes.
- **MySQL** : Base de données.
- **Swagger** : Documentation et Exploration des APIs.
  
## Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request pour proposer des améliorations ou signaler des bugs.

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus d'informations.
