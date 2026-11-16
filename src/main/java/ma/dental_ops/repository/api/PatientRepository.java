package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Patient;
import ma.dental_ops.dto.PageResult;

import java.sql.Connection;
import java.util.List;

public interface PatientRepository {

    Patient save(Patient patient);

    // Création du patient dans une transaction existante
    Patient save(Patient patient, Connection connection);

    Patient findById(Long id);

    List<Patient> findAll();

    // US-03 : Archiver un patient
    void updateArchiveStatus(Long id, boolean archive);

    // US-03 : Rechercher un patient
    List<Patient> searchByNomOrTelephone(String searchTerm);

    PageResult<Patient> searchPage(String searchTerm, int page, int pageSize);

}
