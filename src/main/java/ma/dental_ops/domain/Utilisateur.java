package ma.dental_ops.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.dental_ops.common.enums.StatutUtilisateur;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Utilisateur {

    private Long idUtilisateur;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String email;
    private String motDePasse;
    private StatutUtilisateur statutUtilisateur;

}