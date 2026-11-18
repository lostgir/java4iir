-- T04 : donnees fictives pour les tests manuels de US-01 (login/logout par role)
-- Mots de passe en clair (pour les tests) -> hash BCrypt correspondant entre parentheses :
--   admin      / Admin123!       (ADMINISTRATEUR, actif)
--   medecin    / Medecin123!     (MEDECIN, actif)
--   secretaire / Secretaire123!  (SECRETAIRE, actif)
--   inactif    / Inactif123!     (SECRETAIRE, inactif -> doit etre refuse malgre un mot de passe correct)

INSERT INTO utilisateur (nom_utilisateur, prenom_utilisateur, statut_utilisateur, login, mot_de_passe, actif) VALUES
    ('Bousseta', 'Hiba', 'ADMINISTRATEUR', 'admin', '$2a$10$d3jxblBOrS.GNvN9qxk47un92e23De42K0JI6ovcBB83i87KkiEDa', TRUE),
    ('Hajari', 'Ihssan', 'MEDECIN', 'medecin', '$2a$10$pEGgra2S8DSQEHzSIQkN/e7caqOFyz2ypDlit5cikkMbSMElwE/4W', TRUE),
    ('Rki', 'Hiba', 'SECRETAIRE', 'secretaire', '$2a$10$uwuOOUDfOJsyo03xvSlEUOUR8SZkbHIJNAQj4GsQATEavBuN.aznq', TRUE),
    ('Test', 'Inactif', 'SECRETAIRE', 'inactif', '$2a$10$uwuOOUDfOJsyo03xvSlEUOUR8SZkbHIJNAQj4GsQATEavBuN.aznq', FALSE);
