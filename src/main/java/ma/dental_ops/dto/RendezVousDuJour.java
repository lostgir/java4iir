package ma.dental_ops.dto;

import java.time.LocalTime;

public record RendezVousDuJour(long rendezVousId, long patientId, String nom,
                               String prenom, LocalTime heure, String statut) { }
