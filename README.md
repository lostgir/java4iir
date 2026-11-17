 HibaRki

# 🦷 DentalSoft — Gestion d'un cabinet dentaire

## 1. Présentation du projet

**DentalSoft** est une application de bureau destinée à faciliter la gestion quotidienne d'un cabinet dentaire.

Elle permet de centraliser les informations des patients, de gérer les consultations, les rendez-vous et les charges du cabinet, ainsi que de suivre les indicateurs financiers et l'activité.

Le projet est développé en Java avec une interface graphique Swing, une connexion JDBC et une base de données MySQL.

## 2. Technologies utilisées

| Technologie | Version |
|---|---|
| Java JDK | 17 |
| Maven | 3.x (intégré à IntelliJ IDEA) |
| MySQL Server | 8.0 |
| MySQL Connector/J | 8.4.0 |
| Lombok | 1.18.46 |
| jBCrypt | 0.4 |
| JUnit | 3.8.1 |
| IDE | IntelliJ IDEA |

## 3. Prérequis

Avant de commencer, installer :

- JDK 17.
- MySQL Server 8.0.
- IntelliJ IDEA.
- Une connexion Internet pour télécharger les dépendances Maven.

Vérifier l'installation de Java :

```bash
java -version
```

## 4. Récupération du projet

Cloner le dépôt Git :

```bash
git clone https://github.com/lostgir/java4iir.git
```

Accéder au dossier :

```bash
cd java4iir
```

Ouvrir le projet dans IntelliJ IDEA.

Importer le projet comme projet Maven à partir du fichier `pom.xml`.

## 5. Installation et configuration de MySQL

### 5.1 Démarrer MySQL

Vérifier que le service MySQL est démarré.

Sous Windows PowerShell :

```powershell
Get-Service MySQL80
```

Le service doit être en état `Running`.

### 5.2 Créer la base de données

Ouvrir MySQL Workbench et se connecter au serveur local.

Créer la base de données :

```sql
CREATE DATABASE dental_ops_test
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Sélectionner la base :

```sql
USE dental_ops_test;
```

### 5.3 Créer les tables

Dans IntelliJ, ouvrir le fichier :

`database/schema.sql`

Exécuter le script dans MySQL Workbench sur la base de données sélectionnée.

Le script crée les 20 tables et leurs contraintes :

- Clés primaires (PK).
- Clés étrangères (FK).
- Contraintes UNIQUE.
- Contraintes CHECK.
- Moteur InnoDB.
- Encodage UTF-8 (`utf8mb4`).

### 5.4 Insérer les données fictives

Ouvrir le fichier :

`database/seed.sql`

Exécuter le script après la création du schéma.

Ce script contient des données fictives destinées aux tests et à la démonstration.

**Important :** exécuter le script de données une seule fois sur une base vierge pour éviter les doublons.

## 6. Configuration de l'application

Ouvrir :

`src/main/resources/application.properties`

Configurer la connexion à MySQL :

```properties
DataSource.url=jdbc:mysql://127.0.0.1:3306/dental_ops_test
DataSource.user=root
DataSource.pass=VOTRE_MOT_DE_PASSE
```

Remplacer `VOTRE_MOT_DE_PASSE` par le mot de passe MySQL configuré sur votre machine.

Ne jamais publier un vrai mot de passe dans GitHub.

La base configurée dans cette URL doit correspondre à celle qui contient les tables créées par `schema.sql`.

## 7. Compilation et lancement

### 7.1 Charger les dépendances

Dans IntelliJ IDEA, ouvrir le panneau Maven et actualiser le projet.

### 7.2 Nettoyer le projet

Dans Maven → Lifecycle → clean.

### 7.3 Compiler le projet

Dans Maven → Lifecycle → compile.

La compilation doit se terminer par :

```text
BUILD SUCCESS
```

### 7.4 Lancer l'application

Dans IntelliJ IDEA, ouvrir la classe principale :

`ma.dental_ops.App`

Exécuter la méthode :

```java
public static void main(String[] args){}
```

La fenêtre principale DentalSoft doit s'afficher.

## 8. Comptes de démonstration

Les comptes de démonstration sont créés par le script `seed.sql`.

| Rôle | Email | Mot de passe |
|---|---|---|
| Administrateur | À confirmer | À confirmer |
| Secrétaire | À confirmer | À confirmer |
| Médecin | À confirmer | À confirmer |

Les mots de passe enregistrés dans la base doivent être hachés avec BCrypt.

Les identifiants définitifs doivent être vérifiés avant la publication de ce README.

## 9. Scénario de référence

Pour vérifier le fonctionnement de l'application :

1. Démarrer le serveur MySQL.
2. Vérifier que la base de données est accessible.
3. Lancer DentalSoft depuis IntelliJ IDEA.
4. Accéder au menu principal.
5. Ouvrir le tableau de bord d'activité.
6. Consulter les indicateurs d'activité.
7. Accéder à la gestion des charges.
8. Vérifier les données enregistrées dans MySQL.

Ce scénario permet de vérifier le démarrage de l'application et l'accès aux principales fonctionnalités.

## 10. Bilan du sprint S1

### Objectif du sprint

Le sprint S1 avait pour objectif de mettre en place les principales fonctionnalités de gestion d'un cabinet dentaire, ainsi que la structure de la base de données.

### Réalisations

- Mise en place de l'architecture Java.
- Gestion des utilisateurs et des rôles.
- Gestion des patients et des dossiers médicaux.
- Gestion des rendez-vous et des consultations.
- Gestion des charges.
- Tableau de bord d'activité.
- Indicateurs financiers.
- Création du schéma SQL et des données fictives.
- Tests de compilation et de lancement de l'application.
- Documentation de l'installation et de l'utilisation.

### Contributions des membres

L'équipe est composée de six membres.

| Membre | Contributions |
|---|---|
| Hiba Bousseta | À compléter |
| Hiba Rachki | À compléter |
| Houda Rachki | À compléter |
| Ihssan Hajari | À compléter |
| Achraf Ouazzane | À compléter |
| Taha chibane| À compléter |

### Conclusion du sprint

Le sprint S1 a permis de construire les bases fonctionnelles et techniques de DentalSoft.

L'application peut être compilée et lancée dans l'environnement de développement. La documentation facilite la préparation de l'environnement et la prise en main du projet.

Les prochaines améliorations porteront sur la validation des fonctionnalités, les tests et l'amélioration de l'expérience utilisateur.

---

**Projet : DentalSoft**  
**Sprint : S1**  
**Équipe : 6 membres**  
**Dépôt GitHub :** https://github.com/lostgir/java4iir

# DentalSoft (java4iir)

Application desktop (Swing) de gestion de cabinet dentaire, avec base de donnees MySQL.

## Prerequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installe et lance
- Un **JRE/JDK 25** en local pour lancer l'application (l'UI Swing s'affiche en natif, pas dans un conteneur)

## 1. Demarrer la base de donnees

```bash
docker compose up -d
```

Cela lance uniquement MySQL 8.4 (le service `build` est derriere un profil et n'est pas demarre par
cette commande) et execute automatiquement `src/main/resources/sql/schema.sql` puis `seed.sql`
(uniquement au premier demarrage). La base est accessible sur `127.0.0.1:3307`, comme attendu par
`src/main/resources/application.properties`.

Comptes de test crees par le seed :

| Login        | Mot de passe     | Role           | Actif |
|--------------|------------------|----------------|-------|
| admin        | Admin123!        | ADMINISTRATEUR | oui   |
| medecin      | Medecin123!      | MEDECIN        | oui   |
| secretaire   | Secretaire123!   | SECRETAIRE     | oui   |
| inactif      | Inactif123!      | SECRETAIRE     | non (connexion refusee) |

Pour tout repartir de zero (supprime les donnees et rejoue les scripts SQL) :

```bash
docker compose down -v
docker compose up -d
```

## 2. Compiler le projet

Pour que tout le monde compile avec exactement le meme JDK (25) et la meme version de Maven,
sans devoir les installer en local :

```bash
docker compose --profile build run --rm build
```

Le `.jar` est genere directement dans `target/` sur la machine (le dossier du projet est monte
dans le conteneur), comme pour un build local classique.

Pour verifier les versions utilisees par l'image :

```bash
docker compose run --rm build mvn -v
```

## 3. Lancer l'application

L'application est une UI Swing : elle se lance en natif sur la machine (pas dans Docker), et se
connecte a la base MySQL qui tourne dans le conteneur.

```bash
mvn exec:java -Dexec.mainClass="ma.dental_ops.App"
```

ou via votre IDE, en executant directement la classe `App`.

## Depannage

- **"Impossible de se connecter a la base de donnees"** : verifiez que le conteneur `mysql` tourne
  (`docker compose ps`) et qu'aucun autre service n'occupe deja le port `3307`.
- **Les comptes de test n'existent pas** : le volume MySQL existait deja avant l'ajout des scripts
  d'init. Faites `docker compose down -v` puis `docker compose up -d` pour repartir propre.
   main
