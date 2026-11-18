package ma.dental_ops.service.api;

import java.time.LocalDate;

public interface DashboardService {

    long getNombreRendezVous(LocalDate dateDebut, LocalDate dateFin);

    long getNombrePatientsRecus(LocalDate dateDebut, LocalDate dateFin);

    long getNombreConsultations(LocalDate dateDebut, LocalDate dateFin);

    long getNombreRendezVousDuJour();
}