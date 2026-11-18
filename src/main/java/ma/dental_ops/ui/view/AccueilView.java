package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.ui.view.component.RoundedButton;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Ecran protege affiche apres connexion. Sert a demontrer CA-02 : la
 * deconnexion ferme la Session et ramene a l'ecran de connexion (E-07), qui
 * exigera une nouvelle authentification pour revenir ici.
 */
public class AccueilView extends JFrame {

    private final RoundedButton logoutButton;

    public AccueilView(Utilisateur utilisateur) {
        super("DentalSoft - Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        content.setBackground(Theme.BG_TOP);
        setContentPane(content);

        JLabel welcome = new JLabel(
                "<html><h2>Bienvenue, " + utilisateur.getPrenomUtilisateur() + " " + utilisateur.getNomUtilisateur()
                        + "</h2>Role : " + utilisateur.getStatutUtilisateur() + "</html>",
                SwingConstants.CENTER);
        welcome.setForeground(Theme.TEAL_DARK);
        content.add(welcome, BorderLayout.CENTER);

        logoutButton = new RoundedButton("Se déconnecter", 12);
        logoutButton.setPreferredSize(new Dimension(200, 48));
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.setOpaque(false);
        south.add(logoutButton);
        content.add(south, BorderLayout.SOUTH);
    }

    public RoundedButton getLogoutButton() {
        return logoutButton;
    }
}
