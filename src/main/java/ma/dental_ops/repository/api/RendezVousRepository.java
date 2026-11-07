package ma.dental_ops.repository.api;

import java.time.LocalDate;

public interface RendezVousRepository {

    long compterRendezVous(LocalDate dateDebut, LocalDate dateFin);

    long compterPatientsRecus(LocalDate dateDebut, LocalDate dateFin);

    long compterRendezVousDuJour(LocalDate date);
}