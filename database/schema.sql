-- =====================================================
-- DENTALSOFT - SCHEMA.SQL
-- T04 : Création des 20 tables
-- Moteur : InnoDB
-- Encodage : utf8mb4
-- =====================================================

CREATE DATABASE IF NOT EXISTS dental_ops_test
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE dental_ops_test;



-- =====================================================
-- 1. UTILISATEUR
-- =====================================================

CREATE TABLE IF NOT EXISTS utilisateur (
                                           id_utilisateur BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           nom_utilisateur VARCHAR(100) NOT NULL,
    prenom_utilisateur VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    statut_utilisateur ENUM(
                               'ADMINISTRATEUR',
                               'REGULATEUR',
                               'SECRETAIRE',
                               'MEDECIN'
                           ) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 2. PATIENT
-- =====================================================

CREATE TABLE IF NOT EXISTS patient (
                                       id_patient BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    email VARCHAR(150),
    archive BOOLEAN DEFAULT FALSE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 3. MEDECIN
-- =====================================================

CREATE TABLE IF NOT EXISTS medecin (
                                       id_medecin BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       utilisateur_id BIGINT NOT NULL UNIQUE,
                                       specialite VARCHAR(100),
    numero_ordre VARCHAR(50) UNIQUE,
    CONSTRAINT fk_medecin_utilisateur
    FOREIGN KEY (utilisateur_id)
    REFERENCES utilisateur(id_utilisateur)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 4. SECRETAIRE
-- =====================================================

CREATE TABLE IF NOT EXISTS secretaire (
                                          id_secretaire BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          utilisateur_id BIGINT NOT NULL UNIQUE,
                                          telephone VARCHAR(20),
    CONSTRAINT fk_secretaire_utilisateur
    FOREIGN KEY (utilisateur_id)
    REFERENCES utilisateur(id_utilisateur)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 5. DOSSIER MEDICAL
-- =====================================================

CREATE TABLE IF NOT EXISTS dossier_medical (
                                               id_dossier BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               patient_id BIGINT NOT NULL UNIQUE,
                                               medecin_id BIGINT NOT NULL,
                                               date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                               CONSTRAINT fk_dossier_patient
                                               FOREIGN KEY (patient_id)
    REFERENCES patient(id_patient),
    CONSTRAINT fk_dossier_medecin
    FOREIGN KEY (medecin_id)
    REFERENCES utilisateur(id_utilisateur)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 6. RENDEZ-VOUS
-- =====================================================

CREATE TABLE IF NOT EXISTS rendez_vous (
                                           id_rendez_vous BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           patient_id BIGINT NOT NULL,
                                           date_rendez_vous DATE NOT NULL,
                                           heure_rendez_vous TIME NOT NULL,
                                           statut ENUM(
                                           'PLANIFIE',
                                           'CONFIRME',
                                           'ANNULE',
                                           'TERMINE'
) NOT NULL DEFAULT 'PLANIFIE',
    INDEX idx_rdv_date (date_rendez_vous),
    CONSTRAINT fk_rdv_patient
    FOREIGN KEY (patient_id)
    REFERENCES patient(id_patient)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 7. CONSULTATION
-- =====================================================

CREATE TABLE IF NOT EXISTS consultation (
                                            id_consultation BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            rendez_vous_id BIGINT NOT NULL UNIQUE,
                                            date_consultation DATE NOT NULL,
                                            observations TEXT,
                                            CONSTRAINT fk_consultation_rdv
                                            FOREIGN KEY (rendez_vous_id)
    REFERENCES rendez_vous(id_rendez_vous),
    INDEX idx_consultation_date (date_consultation)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 8. CHARGE
-- =====================================================

CREATE TABLE IF NOT EXISTS charge (
                                      id_charge BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      titre VARCHAR(150) NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    date_charge DATE NOT NULL,
    description TEXT,
    auteur_id BIGINT NOT NULL,
    CONSTRAINT fk_charge_utilisateur
    FOREIGN KEY (auteur_id)
    REFERENCES utilisateur(id_utilisateur),
    CONSTRAINT chk_montant_positif CHECK (montant > 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 9. ACTE
-- =====================================================

CREATE TABLE IF NOT EXISTS acte (
                                    id_acte BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    libelle VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    prix DECIMAL(10,2) NOT NULL,
    duree_minutes INT,
    CONSTRAINT chk_acte_prix CHECK (prix >= 0),
    CONSTRAINT chk_acte_duree CHECK (duree_minutes IS NULL OR duree_minutes > 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 10. CONSULTATION_ACTE
-- =====================================================

CREATE TABLE IF NOT EXISTS consultation_acte (
                                                 id_consultation_acte BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 consultation_id BIGINT NOT NULL,
                                                 acte_id BIGINT NOT NULL,
                                                 quantite INT NOT NULL DEFAULT 1,
                                                 prix_applique DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_ca_consultation
    FOREIGN KEY (consultation_id)
    REFERENCES consultation(id_consultation),
    CONSTRAINT fk_ca_acte
    FOREIGN KEY (acte_id)
    REFERENCES acte(id_acte),
    CONSTRAINT chk_ca_quantite CHECK (quantite > 0),
    CONSTRAINT chk_ca_prix CHECK (prix_applique >= 0),
    UNIQUE KEY uk_consultation_acte (consultation_id, acte_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 11. MEDICAMENT
-- =====================================================

CREATE TABLE IF NOT EXISTS medicament (
                                          id_medicament BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          nom VARCHAR(150) NOT NULL,
    description TEXT,
    dosage VARCHAR(100),
    forme VARCHAR(100),
    stock INT NOT NULL DEFAULT 0,
    CONSTRAINT chk_medicament_stock CHECK (stock >= 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 12. ORDONNANCE
-- =====================================================

CREATE TABLE IF NOT EXISTS ordonnance (
                                          id_ordonnance BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          consultation_id BIGINT NOT NULL,
                                          date_ordonnance DATE NOT NULL,
                                          instructions TEXT,
                                          CONSTRAINT fk_ordonnance_consultation
                                          FOREIGN KEY (consultation_id)
    REFERENCES consultation(id_consultation)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 13. ORDONNANCE_MEDICAMENT
-- =====================================================

CREATE TABLE IF NOT EXISTS ordonnance_medicament (
                                                     id_ordonnance_medicament BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                     ordonnance_id BIGINT NOT NULL,
                                                     medicament_id BIGINT NOT NULL,
                                                     posologie VARCHAR(255) NOT NULL,
    duree VARCHAR(100),
    quantite INT NOT NULL,
    CONSTRAINT fk_om_ordonnance
    FOREIGN KEY (ordonnance_id)
    REFERENCES ordonnance(id_ordonnance),
    CONSTRAINT fk_om_medicament
    FOREIGN KEY (medicament_id)
    REFERENCES medicament(id_medicament),
    CONSTRAINT chk_om_quantite CHECK (quantite > 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 14. FACTURE
-- =====================================================

CREATE TABLE IF NOT EXISTS facture (
                                       id_facture BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       patient_id BIGINT NOT NULL,
                                       consultation_id BIGINT,
                                       date_facture DATE NOT NULL,
                                       montant_total DECIMAL(10,2) NOT NULL,
    statut ENUM('EN_ATTENTE','PAYEE','ANNULEE')
    NOT NULL DEFAULT 'EN_ATTENTE',
    CONSTRAINT fk_facture_patient
    FOREIGN KEY (patient_id)
    REFERENCES patient(id_patient),
    CONSTRAINT fk_facture_consultation
    FOREIGN KEY (consultation_id)
    REFERENCES consultation(id_consultation),
    CONSTRAINT chk_facture_montant CHECK (montant_total >= 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 15. DETAIL_FACTURE
-- =====================================================

CREATE TABLE IF NOT EXISTS detail_facture (
                                              id_detail BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              facture_id BIGINT NOT NULL,
                                              designation VARCHAR(200) NOT NULL,
    quantite INT NOT NULL DEFAULT 1,
    prix_unitaire DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detail_facture
    FOREIGN KEY (facture_id)
    REFERENCES facture(id_facture),
    CONSTRAINT chk_detail_quantite CHECK (quantite > 0),
    CONSTRAINT chk_detail_prix CHECK (prix_unitaire >= 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 16. PAIEMENT
-- =====================================================

CREATE TABLE IF NOT EXISTS paiement (
                                        id_paiement BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        facture_id BIGINT,
                                        montant DECIMAL(10,2) NOT NULL,
    date_paiement DATE NOT NULL,
    mode_paiement ENUM('ESPECES','CARTE','VIREMENT') NOT NULL,
    statut ENUM('PAYE','ANNULE') NOT NULL DEFAULT 'PAYE',
    INDEX idx_paiement_date (date_paiement),
    CONSTRAINT fk_paiement_facture
    FOREIGN KEY (facture_id)
    REFERENCES facture(id_facture),
    CONSTRAINT chk_paiement_montant CHECK (montant > 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 17. ALLERGIE
-- =====================================================

CREATE TABLE IF NOT EXISTS allergie (
                                        id_allergie BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nom VARCHAR(150) NOT NULL UNIQUE,
    description TEXT
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 18. PATIENT_ALLERGIE
-- =====================================================

CREATE TABLE IF NOT EXISTS patient_allergie (
                                                id_patient_allergie BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                patient_id BIGINT NOT NULL,
                                                allergie_id BIGINT NOT NULL,
                                                severite ENUM('FAIBLE','MODEREE','ELEVEE') DEFAULT 'MODEREE',
    observations TEXT,
    CONSTRAINT fk_pa_patient
    FOREIGN KEY (patient_id)
    REFERENCES patient(id_patient),
    CONSTRAINT fk_pa_allergie
    FOREIGN KEY (allergie_id)
    REFERENCES allergie(id_allergie),
    UNIQUE KEY uk_patient_allergie (patient_id, allergie_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 19. NOTIFICATION
-- =====================================================

CREATE TABLE IF NOT EXISTS notification (
                                            id_notification BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            utilisateur_id BIGINT NOT NULL,
                                            titre VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lu BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_notification_utilisateur
    FOREIGN KEY (utilisateur_id)
    REFERENCES utilisateur(id_utilisateur)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 20. HISTORIQUE_ACTION
-- =====================================================

CREATE TABLE IF NOT EXISTS historique_action (
                                                 id_historique BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 utilisateur_id BIGINT,
                                                 action VARCHAR(150) NOT NULL,
    description TEXT,
    date_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historique_utilisateur
    FOREIGN KEY (utilisateur_id)
    REFERENCES utilisateur(id_utilisateur)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- FIN DU SCHEMA : 20 TABLES
-- =====================================================