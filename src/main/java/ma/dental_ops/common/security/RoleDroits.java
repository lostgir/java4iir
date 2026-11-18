package ma.dental_ops.common.security;

import ma.dental_ops.common.enums.Droit;
import ma.dental_ops.common.enums.StatutUtilisateur;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Table de correspondance role -> droits accordes (RBAC statique).
 */
public final class RoleDroits {

    private static final Map<StatutUtilisateur, Set<Droit>> DROITS_PAR_ROLE = new EnumMap<>(StatutUtilisateur.class);

    static {
        DROITS_PAR_ROLE.put(StatutUtilisateur.ADMINISTRATEUR, EnumSet.allOf(Droit.class));
        DROITS_PAR_ROLE.put(StatutUtilisateur.MEDECIN, EnumSet.of(
                Droit.GERER_PATIENTS,
                Droit.CONSULTER_TABLEAU_BORD
        ));
        DROITS_PAR_ROLE.put(StatutUtilisateur.SECRETAIRE, EnumSet.of(
                Droit.GERER_PATIENTS
        ));
    }

    private RoleDroits() {
    }

    public static boolean accorde(StatutUtilisateur role, Droit droit) {
        return role != null && DROITS_PAR_ROLE.getOrDefault(role, Set.of()).contains(droit);
    }
}
