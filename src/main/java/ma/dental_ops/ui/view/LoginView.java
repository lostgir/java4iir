package ma.dental_ops.ui.view;

import ma.dental_ops.ui.view.component.InputField;
import ma.dental_ops.ui.view.component.LinkLabel;
import ma.dental_ops.ui.view.component.RoundedButton;
import ma.dental_ops.ui.view.component.ShadowCardPanel;
import ma.dental_ops.ui.view.component.TealCheckBox;
import ma.dental_ops.ui.view.icon.ArrowIcon;
import ma.dental_ops.ui.view.icon.LockIcon;
import ma.dental_ops.ui.view.icon.MailIcon;
import ma.dental_ops.ui.view.icon.ToothIcon;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Ecran E-07 : Connexion (US-01 / T12). Reproduit la maquette DentalSoft
 * (carte blanche centree, logo dent, champs email/mot de passe, case
 * "se souvenir de moi", lien mot de passe oublie, bouton "Se connecter").
 */
public class LoginView extends JFrame {

    private static final int CARD_WIDTH = 470;
    private static final int CARD_RADIUS = 22;

    private final InputField emailField;
    private final InputField passwordField;
    private final TealCheckBox rememberMe;
    private final LinkLabel forgotPasswordLink;
    private final RoundedButton loginButton;
    private final JLabel errorLabel;

    private int rowIndex = 0;

    public LoginView() {
        super("DentalSoft - Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(820, 680));
        setLocationRelativeTo(null);

        LoginBackgroundPanel background = new LoginBackgroundPanel();
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        ShadowCardPanel card = new ShadowCardPanel(CARD_WIDTH, CARD_RADIUS);
        card.setLayout(new GridBagLayout());

        JLabel toothIcon = new JLabel(new ToothIcon(62, 72, Theme.TEAL_ICON));
        toothIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("DentalSoft", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEAL_DARK);

        JLabel subtitle = new JLabel("Gestion de cabinet dentaire", SwingConstants.CENTER);
        subtitle.setFont(Theme.FONT_SUBTITLE);
        subtitle.setForeground(Theme.TEXT_SUBTITLE);

        JLabel emailLabel = sectionLabel("Adresse e-mail");
        emailField = new InputField(new MailIcon(18, Theme.TEXT_MUTED), "votre@exemple.com", false);

        JLabel passwordLabel = sectionLabel("Mot de passe");
        passwordField = new InputField(new LockIcon(18, Theme.TEXT_MUTED), "••••••••", true);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(Theme.FONT_FOOTER);
        errorLabel.setForeground(Theme.ERROR);

        Box optionsRow = Box.createHorizontalBox();
        rememberMe = new TealCheckBox("Se souvenir de moi");
        rememberMe.setSelected(true);
        forgotPasswordLink = new LinkLabel("Mot de passe oublié ?");
        optionsRow.add(rememberMe);
        optionsRow.add(Box.createHorizontalGlue());
        optionsRow.add(forgotPasswordLink);
        optionsRow.setPreferredSize(new Dimension(1, 28));

        loginButton = new RoundedButton("Se connecter", 10);
        loginButton.setIcon(new ArrowIcon(18, Color.WHITE));
        loginButton.setIconTextGap(10);
        loginButton.setBackground(Theme.LOGIN_BUTTON);
        loginButton.setHoverBackground(Theme.LOGIN_BUTTON_HOVER);
        loginButton.setPreferredSize(new Dimension(1, 46));

        JSeparator separator = new JSeparator();
        separator.setForeground(Theme.SEPARATOR);
        separator.setPreferredSize(new Dimension(1, 1));

        JLabel footer = new JLabel("Accès réservé au personnel autorisé", SwingConstants.CENTER);
        footer.setFont(Theme.FONT_FOOTER);
        footer.setForeground(Theme.TEXT_MUTED);

        addRow(card, toothIcon, 0);
        addRow(card, title, 12);
        addRow(card, subtitle, 2);
        addRow(card, emailLabel, 28);
        addRow(card, emailField, 8);
        addRow(card, passwordLabel, 18);
        addRow(card, passwordField, 8);
        addRow(card, errorLabel, 6);
        addRow(card, optionsRow, 10);
        addRow(card, loginButton, 18);
        addRow(card, separator, 22);
        addRow(card, footer, 14);

        background.add(card, new GridBagConstraints());
    }

    private void addRow(JPanel card, Component component, int topGap) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = rowIndex++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(topGap, 0, 0, 0);
        card.add(component, gbc);
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.TEXT_LABEL);
        return label;
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public boolean isRememberMe() {
        return rememberMe.isSelected();
    }

    public RoundedButton getLoginButton() {
        return loginButton;
    }

    public LinkLabel getForgotPasswordLink() {
        return forgotPasswordLink;
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    public void clearError() {
        errorLabel.setText(" ");
    }
}
