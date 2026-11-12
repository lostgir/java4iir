package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.DossierMedical;
import ma.dental_ops.domain.Patient;
import ma.dental_ops.dto.DossierMedecinDTO;
import ma.dental_ops.dto.PatientSecretariatDTO;
import ma.dental_ops.repository.api.DossierMedicalRepository;
import ma.dental_ops.repository.api.PatientRepository;
import ma.dental_ops.service.api.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DossierMedicalRepository dossierRepository;

    public PatientServiceImpl(PatientRepository patientRepository, DossierMedicalRepository dossierRepository) {
        this.patientRepository = patientRepository;
        this.dossierRepository = dossierRepository;
    }

    @Override
    public Patient creerPatientAvecDossier(Patient patient, Long medecinId) {
        // T05 : Création du patient
        Patient patientSauve = patientRepository.save(patient);

        // T05 : Création du dossier médical (RG-01)
        DossierMedical dossier = DossierMedical.builder()
                .patientId(patientSauve.getId())
                .medecinId(medecinId)
                .build();
        dossierRepository.save(dossier);

        return patientSauve;
    }

    @Override
    public PatientSecretariatDTO getPatientPourSecretariat(Long patientId) {
        Patient p = patientRepository.findById(patientId);
        if (p == null) {
            throw new RuntimeException("Patient introuvable");
        }

        return PatientSecretariatDTO.builder()
                .id(p.getId())
                .nom(p.getNom())
                .prenom(p.getPrenom())
                .dateNaissance(p.getDateNaissance())
                .telephone(p.getTelephone())
                .email(p.getEmail())
                .archive(p.isArchive())
                .build();
    }

    @Override
    public DossierMedecinDTO getDossierPourMedecin(Long patientId) {
        Patient p = patientRepository.findById(patientId);
        if (p == null) {
            throw new RuntimeException("Patient introuvable");
        }

        DossierMedical d = dossierRepository.findByPatientId(patientId);
        if (d == null) {
            throw new RuntimeException("Dossier introuvable");
        }

        return DossierMedecinDTO.builder()
                .patientId(p.getId())
                .nom(p.getNom())
                .prenom(p.getPrenom())
                .dateNaissance(p.getDateNaissance())
                .dossierId(d.getId())
                .dateCreationDossier(d.getDateCreation())
                .medecinReferentId(d.getMedecinId())
                .build();
    }

    @Override
    public void archiverPatient(Long patientId, boolean archive) {
        Patient p = patientRepository.findById(patientId);
        if (p == null) {
            throw new RuntimeException("Patient introuvable pour l'archivage");
        }
        patientRepository.updateArchiveStatus(patientId, archive);
    }

    @Override
    public List<PatientSecretariatDTO> rechercherPatients(String terme) {
        List<Patient> patients = patientRepository.searchByNomOrTelephone(terme);
        // On transforme la liste en DTO
        return patients.stream().map(p -> PatientSecretariatDTO.builder()
                .id(p.getId())
                .nom(p.getNom())
                .prenom(p.getPrenom())
                .dateNaissance(p.getDateNaissance())
                .telephone(p.getTelephone())
                .email(p.getEmail())
                .archive(p.isArchive())
                .build()).toList();
    }
}