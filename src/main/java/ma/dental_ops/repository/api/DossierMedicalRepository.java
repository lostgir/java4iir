package ma.dental_ops.repository.api;

import ma.dental_ops.domain.DossierMedical;
import java.sql.Connection;

public interface DossierMedicalRepository {
    DossierMedical save(DossierMedical dossier);
    DossierMedical findByPatientId(Long patientId);
    DossierMedical save(DossierMedical dossier, Connection connection);

}