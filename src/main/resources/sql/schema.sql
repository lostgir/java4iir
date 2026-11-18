-- T04 : schema de la base dental_ops_db (Sprint 1 - US-01)
CREATE TABLE IF NOT EXISTS utilisateur (
    id_utilisateur     BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_utilisateur    VARCHAR(100) NOT NULL,
    prenom_utilisateur VARCHAR(100) NOT NULL,
    statut_utilisateur VARCHAR(30)  NOT NULL,
    login              VARCHAR(50)  NOT NULL UNIQUE,
    mot_de_passe       VARCHAR(60)  NOT NULL,
    actif              BOOLEAN      NOT NULL DEFAULT TRUE
);
