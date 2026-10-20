# Système de Gestion de Salle de Sport

Application web de gestion de stock et de ventes pour une salle de sport.

## Fonctionnalités

- Authentification (Admin / Employé)
- Tableau de bord (ventes, produits les plus vendus)
- Gestion des adhérents (CRUD pour Admin)
- Carte adhérent avec solde et historique des recharges
- Gestion des produits (CRUD pour Admin)
- Gestion des ventes
- Gestion des rôles et des permissions

## Technologies

- Back-end : Spring Boot, Spring Data JPA
- Front-end : HTML, CSS, JavaScript
- Base de données : MySQL
- Documentation API : Swagger
- Conteneurisation : Docker

## Prérequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé **et lancé**
  (l'icône Docker doit indiquer "Running")

## Lancer le projet

1. Télécharger le projet (bouton vert **Code → Download ZIP**) puis le décompresser
2. Ouvrir un terminal dans le dossier du projet
3. Exécuter :

```bash
docker compose up --build
```

## Accès

| Service | URL |
|---|---|
| Site web | http://localhost |
| API (Swagger) | http://localhost:8080/swagger-ui/index.html |

## Comptes de test

| Rôle | Utilisateur | Mot de passe |
|---|---|---|
| ADMIN | admin | admin |
| EMPLOYEE | employee | employee |

## Base de données

MySQL (utilisateur `gym` / mot de passe `gym`). La base `gym_management`
et ses tables sont créées automatiquement.
