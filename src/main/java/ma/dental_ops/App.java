package ma.dental_ops;

import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.dental_ops.ui.controller.UtilisateurController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UtilisateurRepository user = new UtilisateurRepositoryImplt();
        UtilisateurController controller = new UtilisateurController();


    }
}
