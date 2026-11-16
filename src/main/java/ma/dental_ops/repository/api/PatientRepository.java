package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Patient;

import java.sql.Connection;
import java.util.List;

public interface PatientRepository {
    Patient save(Patient patient);
 HibaRki

    // Création du patient dans une transaction existante
    Patient save(Patient patient, Connection connection);


 main
    Patient findById(Long id);
    List<Patient> findAll();

 HibaRki
    // US-03 : Archiver un patient
    void updateArchiveStatus(Long id, boolean archive);

    // US-03 : Rechercher un patient

    // Ajout pour US-03 : Archiver un patient
    void updateArchiveStatus(Long id, boolean archive);

    // Ajout pour US-03 : Rechercher un patient
 main
    List<Patient> searchByNomOrTelephone(String searchTerm);
