# LudoTech
Application de gestion de ludothèque (projet de 5ème semestre à Polytech Montpellier)

## Architecture de l'application
- **db** Répertoire contenant la base de données
- **lib** Répertoire contenant les librairies utilisées
- **res** Répertoire contenant le fichier de paramètres et la traduction des textes affichés
- **src** Répertoire contenant le code source de l'application
 - **launcher** Lanceur de l'application
 - **backend** Système de traitement des données
    - **DAOs** Objets d'accès à la base de données *(Data Access Object)*
    - **POJOs** Représentation des données manipulées *(Plain Old Java Object)*
    - **exceptions** Exceptions soulevées par les services à destination de l'interface utilisateur
    - **services** Fonctionnalités de traitement des données (code métier et intelligent)
    - **tests** Tests unitaires sur les fonctionnalités proposées
 - **frontend** Interface avec l'utilisateur
    - **book** MVC de l'onglet de réservation
    - **borrow** MVC de l'onglet de prêt
    - **catalog** MVC de l'onglet de gestion des jeux
    - **login** MVC de la connexion à l'application
    - **members** MVC de l'onglet de gestion des adhérents
    - **parameters** MVC de l'onglet d'édition des paramètres de l'application
    - **profile** MVC de l'onglet du profil de l'utilisateur
    - **utils** Classes fournissant des outils pour la création de l'interface utilisateur
    - **LudoTechApplication.java** Fenêtre de l'application
    - **MainController.java** Contrôleur principal affichant et gérant onglets
