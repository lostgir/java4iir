
package ma.dental_ops.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierMedical {

    private Long id;
    private Long patientId;
    private Long medecinId;
    private LocalDateTime dateCreation;
}
