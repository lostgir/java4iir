package ma.dental_ops;

import ma.dental_ops.domain.DossierMedical;
import ma.dental_ops.domain.Patient;
import ma.dental_ops.repository.api.DossierMedicalRepository;
import ma.dental_ops.repository.api.PatientRepository;
import ma.dental_ops.service.implementation.PatientServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestPatientService {
    public static void main(String[] args) {
        System.out.println("--- Début des tests ---");

        // 1. Faux Repository Patient (en mémoire, pas de BDD pour le test)
        PatientRepository fakePatientRepo = new PatientRepository() {
            private List<Patient> patients = new ArrayList<>();
            private long currentId = 1;

            @Override
            public Patient save(Patient patient) {
                patient.setId(currentId++);
                patients.add(patient);
                return patient;
            }

            @Override
            public Patient findById(Long id) {
                return patients.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
            }

            @Override
            public List<Patient> findAll() { return patients; }

            @Override
            public void updateArchiveStatus(Long id, boolean archive) {
                Patient p = findById(id);
                if (p != null) p.setArchive(archive);
            }

            @Override
            public List<Patient> searchByNomOrTelephone(String searchTerm) {
                return patients.stream()
                        .filter(p -> p.getNom().contains(searchTerm) || p.getTelephone().contains(searchTerm))
                        .toList();
            }
        };

        // 2. Faux Repository Dossier (en mémoire)
        DossierMedicalRepository fakeDossierRepo = new DossierMedicalRepository() {
            private List<DossierMedical> dossiers = new ArrayList<>();
            private long currentId = 100;

            @Override
            public DossierMedical save(DossierMedical dossier) {
                dossier.setId(currentId++);
                dossiers.add(dossier);
                return dossier;
            }

            @Override
            public DossierMedical findByPatientId(Long patientId) {
                return dossiers.stream().filter(d -> d.getPatientId().equals(patientId)).findFirst().orElse(null);
            }
        };

        // 3. Initialisation du service
        PatientServiceImpl service = new PatientServiceImpl(fakePatientRepo, fakeDossierRepo);

        // === TEST 1 (US-02) : Création ===
        Patient p1 = Patient.builder()
                .nom("Dupont")
                .prenom("Jean")
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .telephone("0612345678")
                .build();
        Patient resultat = service.creerPatientAvecDossier(p1, 10L);

        if (resultat != null && resultat.getId() == 1L) {
            System.out.println("✅ Test US-02 (Création) : RÉUSSI");
        } else {
            System.out.println("❌ Test US-02 (Création) : ÉCHEC");
        }

        // === TEST 2 (US-03) : Récupération Secrétaire ===
        System.out.println("Patient vu par secrétaire : " + service.getPatientPourSecretariat(1L).getNom());

        // === TEST 3 (US-03) : Archivage ===
        service.archiverPatient(1L, true);
        if (service.getPatientPourSecretariat(1L).isArchive()) {
            System.out.println("✅ Test US-03 (Archivage) : RÉUSSI");
        } else {
            System.out.println("❌ Test US-03 (Archivage) : ÉCHEC");
        }

        System.out.println("--- Fin des tests ---");
    }
}