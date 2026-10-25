package ma.oncf_ops.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.oncf_ops.common.enums.StatutUtilisateur;

//Java Bean
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Utilisateur {

    private Long idUtilisateur;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private StatutUtilisateur statutUtilisateur;

}
