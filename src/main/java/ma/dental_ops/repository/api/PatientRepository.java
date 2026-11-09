package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Patient;
import java.util.List;

public interface PatientRepository {
    Patient save(Patient patient);
    Patient findById(Long id);
    List<Patient> findAll();

    // Ajout pour US-03 : Archiver un patient
    void updateArchiveStatus(Long id, boolean archive);

    // Ajout pour US-03 : Rechercher un patient
    List<Patient> searchByNomOrTelephone(String searchTerm);
}