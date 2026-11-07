package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Patient;
import java.util.List;

public interface PatientRepository {

    Patient save(Patient patient);

    Patient findById(Long id);

    List<Patient> findAll();
}