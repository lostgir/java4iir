package ma.oncf_ops;

import ma.oncf_ops.repository.api.UtilisateurRepository;
import ma.oncf_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.oncf_ops.ui.controller.UtilisateurController;

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
