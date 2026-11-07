package ma.dental_ops.service.implementation;

import ma.dental_ops.repository.api.RendezVousRepository;
import ma.dental_ops.repository.api.ConsultationRepository;
import ma.dental_ops.repository.jdbcMySql.RendezVousRepositoryImpl;
import ma.dental_ops.repository.jdbcMySql.ConsultationRepositoryImpl;
import ma.dental_ops.service.api.DashboardService;

import java.time.LocalDate;

public class DashboardServiceImpl implements DashboardService {

    private final RendezVousRepository rendezVousRepository;
    private final ConsultationRepository consultationRepository;

    public DashboardServiceImpl() {
        this.rendezVousRepository = new RendezVousRepositoryImpl();
        this.consultationRepository = new ConsultationRepositoryImpl();
    }

    @Override
    public long getNombreRendezVous(LocalDate dateDebut, LocalDate dateFin) {
        return rendezVousRepository.compterRendezVous(dateDebut, dateFin);
    }

    @Override
    public long getNombrePatientsRecus(LocalDate dateDebut, LocalDate dateFin) {
        return rendezVousRepository.compterPatientsRecus(dateDebut, dateFin);
    }

    @Override
    public long getNombreConsultations(LocalDate dateDebut, LocalDate dateFin) {
        return consultationRepository.compterConsultations(dateDebut, dateFin);
    }
    @Override
    public long getNombreRendezVousDuJour() {
        return rendezVousRepository.compterRendezVousDuJour(LocalDate.now());
    }
}