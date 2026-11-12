package ma.dental_ops.dto;

import java.time.LocalDateTime;

public record PatientEnAttente(long rendezVousId, long patientId, String nom,
                               String prenom, LocalDateTime heureArrivee) { }
