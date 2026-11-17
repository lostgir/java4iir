
package ma.dental_ops.ui.view;

import ma.dental_ops.config.ThemeConfig;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    public LoginWindow() {
        setTitle("DentalSoft - Connexion");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 40, 35, 40));

        JLabel title = new JLabel("DentalSoft", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(15, 85, 96));

        JLabel subtitle = new JLabel(
                "Connectez-vous à votre espace",
                SwingConstants.CENTER
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setBackground(Color.WHITE);
        header.add(title);
        header.add(subtitle);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JPanel form = new JPanel(new GridLayout(4, 1, 8, 8));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Nom d'utilisateur"));
        form.add(usernameField);
        form.add(new JLabel("Mot de passe"));
        form.add(passwordField);

        loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(14, 140, 151));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setBackground(Color.WHITE);
        center.add(form, BorderLayout.CENTER);
        center.add(loginButton, BorderLayout.SOUTH);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
