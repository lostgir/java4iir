package ma.dental_ops.service.api;

import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import java.time.LocalDate;
import java.util.List;

public interface FileAttenteService {
    void enregistrerArrivee(long rendezVousId, LocalDate jour);
    void passerEnCours(long rendezVousId, LocalDate jour);
    List<PatientEnAttente> getFileAttente(LocalDate jour);
    List<RendezVousDuJour> getRendezVousDuJour(LocalDate jour);
}
