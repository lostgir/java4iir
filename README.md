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
