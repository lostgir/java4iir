
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

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String cin;
    private String passeport;
    private String email;
    private String telephone;
    private String adresse;
    private String assurance;
    private boolean archive;
}
