package ma.dental_ops.repository.api;

import ma.dental_ops.domain.DossierMedical;

public interface DossierMedicalRepository {
    DossierMedical save(DossierMedical dossier);
    DossierMedical findByPatientId(Long patientId);
}