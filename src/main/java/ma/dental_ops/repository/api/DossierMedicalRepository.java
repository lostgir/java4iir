package ma.dental_ops.repository.api;

import ma.dental_ops.domain.DossierMedical;
 HibaRki
import java.sql.Connection;

 main

public interface DossierMedicalRepository {
    DossierMedical save(DossierMedical dossier);
    DossierMedical findByPatientId(Long patientId);
 HibaRki
    DossierMedical save(DossierMedical dossier, Connection connection);


 main
}