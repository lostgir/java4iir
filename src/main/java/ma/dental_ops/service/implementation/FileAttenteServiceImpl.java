package ma.dental_ops.service.implementation;

import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import ma.dental_ops.repository.api.FileAttenteRepository;
import ma.dental_ops.service.api.FileAttenteService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FileAttenteServiceImpl implements FileAttenteService {
    private final FileAttenteRepository repository;

    public FileAttenteServiceImpl(FileAttenteRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override public void enregistrerArrivee(long rendezVousId, LocalDate jour) {
        verifier(rendezVousId, jour);
        repository.enregistrerArrivee(rendezVousId, jour);
    }

    @Override public void passerEnCours(long rendezVousId, LocalDate jour) {
        verifier(rendezVousId, jour);
        repository.passerEnCours(rendezVousId, jour);
    }

    @Override public List<PatientEnAttente> getFileAttente(LocalDate jour) {
        Objects.requireNonNull(jour, "La date est obligatoire");
        return List.copyOf(repository.findArriveesDuJour(jour));
    }

    @Override public List<RendezVousDuJour> getRendezVousDuJour(LocalDate jour) {
        Objects.requireNonNull(jour, "La date est obligatoire");
        return List.copyOf(repository.findRendezVousDuJour(jour));
    }

    private void verifier(long id, LocalDate jour) {
        if (id <= 0) throw new IllegalArgumentException("Identifiant de rendez-vous invalide");
        Objects.requireNonNull(jour, "La date est obligatoire");
    }
}
