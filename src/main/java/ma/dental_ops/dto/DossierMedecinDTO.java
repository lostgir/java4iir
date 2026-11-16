package ma.dental_ops.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class DossierMedecinDTO {
    private Long patientId;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Long dossierId;
    private LocalDateTime dateCreationDossier;
    private Long medecinReferentId;
}