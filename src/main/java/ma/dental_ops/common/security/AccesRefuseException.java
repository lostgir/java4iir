package ma.dental_ops.common.security;

/**
 * Levée par la couche Service quand l'utilisateur n'est pas connecté
 * ou que son rôle n'accorde pas le droit demandé (BNF-01).
 */
public class AccesRefuseException extends SecurityException {

    public AccesRefuseException(String message) {
        super(message);
    }
}
