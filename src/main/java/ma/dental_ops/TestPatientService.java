package ma.dental_ops;

import ma.dental_ops.domain.Patient;
import ma.dental_ops.domain.DossierMedical;

import ma.dental_ops.repository.api.PatientRepository;
import ma.dental_ops.repository.api.DossierMedicalRepository;

import ma.dental_ops.repository.jdbcMySql.PatientRepositoryImpl;
import ma.dental_ops.repository.jdbcMySql.DossierMedicalRepositoryImpl;

import ma.dental_ops.service.implementation.PatientServiceImpl;

import ma.dental_ops.dto.PatientSecretariatDTO;

import java.time.LocalDate;

public class TestPatientService {

    public static void main(String[] args) {

        System.out.println("--- Début des tests ---");

        // 1. Initialisation des vrais repositories JDBC
        PatientRepository patientRepo = new PatientRepositoryImpl();
        DossierMedicalRepository dossierRepo = new DossierMedicalRepositoryImpl();

        // 2. Initialisation du service
        PatientServiceImpl service =
                new PatientServiceImpl(patientRepo, dossierRepo);

        // ==========================================
        // TEST 1 : US-02 - Création du patient
        // ==========================================

        Patient patient = Patient.builder()
                .nom("TestTransaction")
                .prenom("Jean")
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .telephone("0612345678")
                .email("test.transaction@gmail.com")
                .build();

        // ID utilisateur existant : 1
        Patient resultat = service.creerPatientAvecDossier(patient, 1L);

        if (resultat != null && resultat.getId() != null) {
            System.out.println("✅ Test US-02 (Création) : RÉUSSI");
            System.out.println("ID Patient : " + resultat.getId());
        } else {
            System.out.println("❌ Test US-02 (Création) : ÉCHEC");
            return;
        }

        // ==========================================
        // TEST 2 : BNF-03 - Vérification du dossier
        // ==========================================

        DossierMedical dossier =
                dossierRepo.findByPatientId(resultat.getId());

        if (dossier != null) {
            System.out.println("✅ Test BNF-03 (Création du dossier médical) : RÉUSSI");
            System.out.println("ID Dossier : " + dossier.getId());
        } else {
            System.out.println("❌ Test BNF-03 : ÉCHEC");
        }

        // ==========================================
        // TEST 3 : US-03 - Archivage du patient
        // ==========================================

        service.archiverPatient(resultat.getId(), true);

        PatientSecretariatDTO patientArchive =
                service.getPatientPourSecretariat(resultat.getId());

        if (patientArchive != null && patientArchive.isArchive()) {
            System.out.println("✅ Test US-03 (Archivage) : RÉUSSI");
        } else {
            System.out.println("❌ Test US-03 (Archivage) : ÉCHEC");
        }

        // ==========================================
        // TEST 4 : BNF-03 - Vérification du ROLLBACK
        // ==========================================

        System.out.println("\n--- TEST ROLLBACK ---");

        Patient patientTest = Patient.builder()
                .nom("PatientRollback")
                .prenom("Test")
                .dateNaissance(LocalDate.of(1995, 5, 10))
                .telephone("0600000000")
                .email("rollback@gmail.com")
                .build();

        try {

            // ID médecin inexistant : doit provoquer une erreur SQL
            service.creerPatientAvecDossier(patientTest, -999L);

            System.out.println(
                    "❌ Test ROLLBACK : ÉCHEC (aucune erreur provoquée)"
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Erreur provoquée lors de la création du dossier."
            );

            Patient patientEnBase = patientRepo.findById(patientTest.getId());

            if (patientEnBase == null) {
                System.out.println("✅ Test ROLLBACK : RÉUSSI");
                System.out.println(
                        "Le patient n'a pas été conservé dans la base."
                );
            } else {
                System.out.println("❌ Test ROLLBACK : ÉCHEC");
                System.out.println(
                        "Le patient existe encore dans la base !"
                );
            }
        }

        System.out.println("\n--- Fin des tests ---");
    }
}