USE dental_ops_test;

SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- 1. UTILISATEURS
-- =====================================================

INSERT INTO utilisateur
(nom_utilisateur, prenom_utilisateur, email, mot_de_passe, statut_utilisateur)
VALUES
    ('Admin', 'DentalSoft', 'admin@dentalsoft.ma',
     '$2a$10$FU8SjLKFkKCSPu9f.nr4P.ckEFeJnjVoHJmRSjkLNHVbar/Dfnjv6',
     'ADMINISTRATEUR'),

    ('Hajari', 'Ihssan', 'secretaire@dentalsoft.ma',
     '$2a$10$73.y0l2R2A/9XlQE1K73eumLOH7XmjMdMeBpzsxUkyGJc6ZFwK4NW',
     'SECRETAIRE'),

    ('Rachki', 'Hiba', 'medecin@dentalsoft.ma',
     '$2a$10$RLLLCQnMMurE4JQ9z1bPBeTMF6doDE0JQC.DNUQa43WAm/6CMzriW',
     'MEDECIN');

-- =====================================================
-- 2. SECRETAIRE
-- =====================================================

INSERT INTO secretaire (utilisateur_id, telephone)
SELECT id_utilisateur, '0600000001'
FROM utilisateur
WHERE email = 'secretaire@dentalsoft.ma';

-- =====================================================
-- 3. MEDECIN
-- =====================================================

INSERT INTO medecin (utilisateur_id, specialite, numero_ordre)
SELECT id_utilisateur, 'Chirurgie dentaire', 'ORDRE-DENT-001'
FROM utilisateur
WHERE email = 'medecin@dentalsoft.ma';

-- =====================================================
-- 4. PATIENTS
-- =====================================================

INSERT INTO patient
(nom, prenom, telephone, email, archive)
VALUES
    ('Alami', 'Youssef', '0612345678', 'youssef.alami@gmail.com', FALSE),
    ('Bennani', 'Sara', '0623456789', 'sara.bennani@gmail.com', FALSE),
    ('Idrissi', 'Omar', '0634567890', 'omar.idrissi@gmail.com', FALSE),
    ('Tazi', 'Salma', '0645678901', 'salma.tazi@gmail.com', FALSE),
    ('Amrani', 'Mehdi', '0656789012', 'mehdi.amrani@gmail.com', FALSE);

-- =====================================================
-- 5. DOSSIERS MEDICAUX
-- =====================================================

INSERT INTO dossier_medical (patient_id, medecin_id, date_creation)
SELECT p.id_patient, u.id_utilisateur, CURRENT_DATE
FROM patient p
         CROSS JOIN utilisateur u
WHERE u.email = 'medecin@dentalsoft.ma';

-- =====================================================
-- 6. RENDEZ-VOUS
-- =====================================================

INSERT INTO rendez_vous
(patient_id, date_rendez_vous, heure_rendez_vous, statut)
VALUES
    (1, '2026-11-12', '09:00:00', 'CONFIRME'),
    (2, '2026-11-12', '10:00:00', 'PLANIFIE'),
    (3, '2026-11-13', '11:00:00', 'PLANIFIE'),
    (4, '2026-11-14', '14:00:00', 'CONFIRME'),
    (5, '2026-11-15', '15:00:00', 'PLANIFIE');

-- =====================================================
-- 7. CONSULTATIONS
-- =====================================================

INSERT INTO consultation
(rendez_vous_id, date_consultation, observations)
VALUES
    (1, '2026-11-12', 'Contrôle dentaire général'),
    (2, '2026-11-12', 'Douleur dentaire'),
    (3, '2026-11-13', 'Consultation de routine');

-- =====================================================
-- 8. CHARGES
-- =====================================================

INSERT INTO charge
(titre, montant, date_charge, description, auteur_id)
VALUES
    ('Achat matériel', 1200.00, CURRENT_DATE, 'Achat de matériel dentaire', 1),
    ('Électricité', 450.00, CURRENT_DATE, 'Facture électricité du cabinet', 1),
    ('Produits médicaux', 800.00, CURRENT_DATE, 'Produits de soin', 1);

-- =====================================================
-- 9. ACTES
-- =====================================================

INSERT INTO acte
(libelle, description, prix, duree_minutes)
VALUES
    ('Détartrage', 'Nettoyage et détartrage dentaire', 300.00, 30),
    ('Extraction', 'Extraction dentaire simple', 500.00, 45),
    ('Consultation', 'Examen dentaire général', 150.00, 20),
    ('Soin carie', 'Traitement d une carie', 400.00, 40),
    ('Blanchiment', 'Blanchiment des dents', 1200.00, 60);

-- =====================================================
-- 10. CONSULTATION_ACTE
-- =====================================================

INSERT INTO consultation_acte
(consultation_id, acte_id, prix_applique)
VALUES
(1, 1, 300.00),
(1, 3, 150.00),
(2, 4, 400.00),
(3, 3, 150.00);
-- =====================================================
-- 11. MEDICAMENTS
-- =====================================================

INSERT INTO medicament
(nom, description, dosage, forme, stock)
VALUES
    ('Paracétamol', 'Antalgique', '1000 mg', 'Comprimé', 100),
    ('Amoxicilline', 'Antibiotique', '500 mg', 'Gélule', 80),
    ('Ibuprofène', 'Anti-inflammatoire', '400 mg', 'Comprimé', 60),
    ('Chlorhexidine', 'Bain de bouche antiseptique', '0.12%', 'Solution', 40);

-- =====================================================
-- 12. ORDONNANCES
-- =====================================================

INSERT INTO ordonnance (consultation_id, date_creation)
VALUES
    (1, CURRENT_DATE),
    (2, CURRENT_DATE);

-- =====================================================
-- 13. ORDONNANCE_MEDICAMENT
-- =====================================================

INSERT INTO ordonnance_medicament
(ordonnance_id, medicament_id, quantite, posologie)
VALUES
    (1, 1, 10, '1 comprimé si douleur'),
    (2, 2, 14, '1 gélule matin et soir');

-- =====================================================
-- 14. FACTURES
-- =====================================================

INSERT INTO facture
(patient_id, date_facture, montant_total, statut)
VALUES
    (1, CURRENT_DATE, 450.00, 'PAYEE'),
    (2, CURRENT_DATE, 400.00, 'NON_PAYEE');

-- =====================================================
-- 15. DETAIL_FACTURE
-- =====================================================

INSERT INTO detail_facture
(facture_id, acte_id, quantite, prix_unitaire)
VALUES
    (1, 1, 1, 300.00),
    (1, 3, 1, 150.00),
    (2, 4, 1, 400.00);

-- =====================================================
-- 16. PAIEMENTS
-- =====================================================

INSERT INTO paiement
(facture_id, montant, date_paiement, mode_paiement, statut)
VALUES
    (1, 450.00, CURRENT_DATE, 'ESPECES', 'PAYE'),
    (2, 200.00, CURRENT_DATE, 'CARTE', 'PAYE');

-- =====================================================
-- 17. ALLERGIES
-- =====================================================

INSERT INTO allergie (nom, description)
VALUES
    ('Pénicilline', 'Allergie aux antibiotiques de type pénicilline'),
    ('Latex', 'Allergie au latex'),
    ('Anesthésiques', 'Réaction aux anesthésiques locaux');

-- =====================================================
-- 18. PATIENT_ALLERGIE
-- =====================================================

INSERT INTO patient_allergie (patient_id, allergie_id)
VALUES
    (1, 1),
    (3, 2),
    (5, 3);

-- =====================================================
-- 19. NOTIFICATIONS
-- =====================================================

INSERT INTO notification
(utilisateur_id, titre, message, date_creation, lu)
VALUES
    (1, 'Bienvenue', 'Bienvenue sur DentalSoft', CURRENT_TIMESTAMP, FALSE),
    (2, 'Nouveau rendez-vous', 'Un nouveau rendez-vous a été enregistré', CURRENT_TIMESTAMP, FALSE),
    (3, 'Nouvelle consultation', 'Une consultation est planifiée', CURRENT_TIMESTAMP, FALSE);

-- =====================================================
-- 20. HISTORIQUE_ACTION
-- =====================================================

INSERT INTO historique_action
(utilisateur_id, action, description, date_action)
VALUES
    (1, 'CREATION', 'Création du compte administrateur', CURRENT_TIMESTAMP),
    (2, 'CREATION', 'Création du compte secrétaire', CURRENT_TIMESTAMP),
    (3, 'CREATION', 'Création du compte médecin', CURRENT_TIMESTAMP);

SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================
-- VERIFICATION
-- =====================================================

SHOW TABLES;

SELECT * FROM utilisateur;
SELECT * FROM patient;
SELECT * FROM dossier_medical;
SELECT * FROM acte;
SELECT * FROM medicament;
SELECT * FROM rendez_vous;
SELECT * FROM facture;