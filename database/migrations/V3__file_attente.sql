USE dental_ops_test;

ALTER TABLE rendez_vous
    MODIFY statut ENUM('PLANIFIE','CONFIRME','ANNULE','TERMINE','ARRIVE','EN_COURS')
        NOT NULL DEFAULT 'PLANIFIE',
    ADD COLUMN heure_arrivee TIMESTAMP NULL;

CREATE INDEX idx_rdv_file_attente
    ON rendez_vous (date_rendez_vous, statut, heure_arrivee, id_rendez_vous);

