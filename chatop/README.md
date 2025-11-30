# Chatop - API de gestion de locations immobilières

API REST développée avec Spring Boot permettant de gérer des annonces de locations immobilières et la messagerie entre utilisateurs.

Ce projet constitue le backend de l'application Chatop. Le frontend ainsi qu'un script SQL d'initialisation de la base de données sont fournis séparément.

## Fonctionnalités

- Authentification JWT (inscription, connexion)
- Gestion des annonces de location (CRUD)
- Système de messagerie entre utilisateurs
- Upload d'images pour les annonces
- Documentation Swagger/OpenAPI

## Prérequis

- **Java 25** ou supérieur
- **Maven 3.8+**
- **MySQL 8.0+**
- Le frontend Chatop (fourni séparément)
- Le script SQL d'initialisation (fourni séparément)

## Installation

### 1. Cloner le projet

```bash
git clone <url-du-repository>
cd chatop
```

### 2. Configurer la base de données MySQL

1. Créez une base de données MySQL :

```sql
CREATE DATABASE rental_db;
```

2. Exécutez le script SQL fourni pour initialiser les tables :

```bash
mysql -u votre_utilisateur -p rental_db < script.sql
```

### 3. Configurer l'application

1. Copiez le fichier d'exemple de configuration :

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

2. Éditez le fichier `application.properties` avec vos paramètres :

```properties
# Connexion à la base de données
spring.datasource.url=jdbc:mysql://localhost:3306/rental_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=VOTRE_NOM_UTILISATEUR
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

### 4. Configurer la clé de sécurité JWT (Important)

La clé secrète utilisée pour signer les tokens JWT est actuellement définie en dur dans le code source :

**Fichier :** `src/main/java/com/api/chatop/chatop/config/SecurityConfig.java`

```java
private final String jwtKey = "MP7MHZa5d21uYBLJa8eLa9Tp7kCQdXoShAvt370vd0i";
```

**Il est fortement recommandé de remplacer cette clé par votre propre clé secrète**, surtout en environnement de production. La clé doit être :
- Une chaîne de caractères aléatoire
- D'au moins 32 caractères
- Gardée secrète et non versionnée dans Git

### 5. Compiler et lancer l'application

```bash
# Compiler le projet
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

L'API sera accessible sur : `http://localhost:3001`

## Documentation de l'API

Une fois l'application lancée, la documentation Swagger est disponible à :

- **Interface Swagger UI :** http://localhost:3001/swagger-ui.html
- **Spécification OpenAPI :** http://localhost:3001/v3/api-docs

## Endpoints principaux

### Authentification (`/api/auth`)

| Méthode | Endpoint | Description | Authentification |
|---------|----------|-------------|------------------|
| POST | `/api/auth/register` | Inscription | Non |
| POST | `/api/auth/login` | Connexion | Non |
| GET | `/api/auth/me` | Profil utilisateur connecté | Oui |

### Locations (`/api/rentals`)

| Méthode | Endpoint | Description | Authentification |
|---------|----------|-------------|------------------|
| GET | `/api/rentals` | Liste des locations | Oui |
| GET | `/api/rentals/{id}` | Détail d'une location | Oui |
| POST | `/api/rentals` | Créer une location | Oui |
| PUT | `/api/rentals/{id}` | Modifier une location | Oui |

### Messages (`/api/messages`)

| Méthode | Endpoint | Description | Authentification |
|---------|----------|-------------|------------------|
| POST | `/api/messages` | Envoyer un message | Oui |

## Structure du projet

```
chatop/
├── src/main/java/com/api/chatop/chatop/
│   ├── config/          # Configuration (Security, ModelMapper)
│   ├── controller/      # Contrôleurs REST
│   ├── dto/             # Objets de transfert (request/response)
│   ├── mapper/          # Mappers Entity <-> DTO
│   ├── model/           # Entités JPA
│   ├── repository/      # Repositories JPA
│   ├── security/        # Services de sécurité
│   └── service/         # Services métier
└── src/main/resources/
    └── application.properties
```

## Technologies utilisées

- **Spring Boot 3.5.7**
- **Spring Security** avec OAuth2 Resource Server
- **Spring Data JPA** avec Hibernate
- **MySQL** comme base de données
- **JWT** (JSON Web Tokens) pour l'authentification
- **Swagger/OpenAPI** pour la documentation
- **Lombok** pour réduire le code boilerplate
- **ModelMapper** pour le mapping DTO/Entity