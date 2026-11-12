package ma.dental_ops.repository.api;

import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import java.time.LocalDate;
import java.util.List;

public interface FileAttenteRepository {
    void enregistrerArrivee(long rendezVousId, LocalDate jour);
    void passerEnCours(long rendezVousId, LocalDate jour);
    List<PatientEnAttente> findArriveesDuJour(LocalDate jour);
    List<RendezVousDuJour> findRendezVousDuJour(LocalDate jour);
}
