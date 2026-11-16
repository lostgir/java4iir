package ma.dental_ops.repository.api;

import java.time.LocalDate;

public interface ConsultationRepository {

    long compterConsultations(LocalDate dateDebut, LocalDate dateFin);
}