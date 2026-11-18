package ma.dental_ops.ui.controller;

import ma.dental_ops.service.api.DashboardService;
import java.time.LocalDate;

public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    public long afficherNombreRendezVous(
            LocalDate dateDebut, LocalDate dateFin) {
        return service.getNombreRendezVous(dateDebut, dateFin);
    }

    public long afficherNombrePatientsRecus(
            LocalDate dateDebut, LocalDate dateFin) {
        return service.getNombrePatientsRecus(dateDebut, dateFin);
    }

    public long afficherNombreConsultations(
            LocalDate dateDebut, LocalDate dateFin) {
        return service.getNombreConsultations(dateDebut, dateFin);
    }
    public long afficherNombreRendezVousDuJour() {
        return service.getNombreRendezVousDuJour();
    }
}