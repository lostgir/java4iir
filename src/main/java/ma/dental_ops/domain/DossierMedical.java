
package ma.dental_ops.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierMedical {

    private Long idDossier;
    private Long idPatient;
    private String antecedentsMedicaux;
    private String allergies;
    private String traitements;
    private String observations;
    private LocalDate dateCreation;
}
