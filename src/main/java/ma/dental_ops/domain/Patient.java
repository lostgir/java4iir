
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
public class Patient {

    private Long idPatient;
    private String nomPatient;
    private String prenomPatient;
    private String telephone;
    private String email;
    private String adresse;
    private LocalDate dateNaissance;
}
