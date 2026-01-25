# Système de Gestion de Salle de Sport

Application web de gestion d’une salle de sport.

## Fonctionnalités

- Authentification (Admin / Employé)
- Tableau de bord (ventes, produits les plus vendus)
- Gestion des adhérents (CRUD pour Admin)
- Carte adhérent avec solde et historique des recharges
- Gestion des produits (CRUD pour Admin)
- Gestion des ventes
- Gestion des rôles et des permissions
- Déconnexion

## Technologies utilisées

- Back-end : Spring Boot, Spring Data JPA
- Front-end : HTML, CSS, JavaScript
- Base de données : MySQL (initialisée automatiquement via Docker)
- Documentation API : Swagger
- Conteneurisation : Docker
- Gestion de version : Git, GitHub

## Lancer le projet avec Docker

1. Cloner le projet
2. Ouvrir un terminal à la racine du projet
3. Lancer la commande : docker compose up --build
   
## Tester le projet

- **Site Web (Front-end)** : [http://localhost](http://localhost)  
  > Pour se connecter et tester l'application comme un utilisateur normal.

- **API Back-end (Swagger)** : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
  > Pour explorer et tester les endpoints de l'API (GET, POST, PUT, DELETE).

### Comptes utilisateurs déjà créés

| Rôle     | Utilisateur | Mot de passe |
|----------|------------|-------------|
| ADMIN    | admin      | admin       |
| EMPLOYEE | employee   | employee    |

### Base de données MySQL

- Utilisateur : `gym` / Mot de passe : `gym`  
> La base `gym_management` et les tables sont créées automatiquement.

