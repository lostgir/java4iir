
package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Patient;

import java.sql.Connection;
import java.util.List;

public interface PatientRepository {

    Patient save(Patient patient);

    // Création du patient dans une transaction existante
    Patient save(Patient patient, Connection connection);

    Patient findById(Long id);

    List<Patient> findAll();

    // Archivage et désarchivage du patient
    void updateArchiveStatus(Long id, boolean archive);

    // Recherche par nom ou téléphone
    List<Patient> searchByNomOrTelephone(String searchTerm);
}
