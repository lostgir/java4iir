package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.service.api.AuthService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginView extends JFrame {
    private final AuthService authService;
    private final JTextField email = new JTextField(24);
    private final JPasswordField motDePasse = new JPasswordField(24);

    public LoginView(AuthService authService) {
        this.authService = authService;
        setTitle("DentalSoft - Connexion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 230);
        setLocationRelativeTo(null);
        construire();
    }

    private void construire() {
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBorder(BorderFactory.createEmptyBorder(24, 24, 12, 24));
        form.add(new JLabel("Email")); form.add(email);
        form.add(new JLabel("Mot de passe")); form.add(motDePasse);
        JButton connexion = new JButton("Se connecter");
        connexion.addActionListener(e -> connecter());
        motDePasse.addActionListener(e -> connecter());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT)); footer.add(connexion);
        add(form, BorderLayout.CENTER); add(footer, BorderLayout.SOUTH);
    }

    private void connecter() {
        char[] secret = motDePasse.getPassword();
        try {
            Utilisateur utilisateur = authService.authentifier(email.getText(), secret);
            if (utilisateur == null) {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.", "Connexion refusée", JOptionPane.ERROR_MESSAGE);
                motDePasse.setText("");
                return;
            }
            new MainView(utilisateur).setVisible(true);
            dispose();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Connexion impossible : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(secret, '\0');
        }
    }
}
