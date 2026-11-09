package ma.dental_ops.service.api;

import ma.dental_ops.domain.Patient;
import ma.dental_ops.dto.DossierMedecinDTO;
import ma.dental_ops.dto.PatientSecretariatDTO;
import java.util.List;

public interface PatientService {
    // T05 : Création atomique
    Patient creerPatientAvecDossier(Patient patient, Long medecinId);

    // T07 : Récupération selon le rôle
    PatientSecretariatDTO getPatientPourSecretariat(Long patientId);
    DossierMedecinDTO getDossierPourMedecin(Long patientId);

    // Ajout pour US-03
    void archiverPatient(Long patientId, boolean archive);
    List<PatientSecretariatDTO> rechercherPatients(String terme);
}