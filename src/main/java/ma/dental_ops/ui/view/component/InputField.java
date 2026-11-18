package ma.dental_ops.ui.view.component;

import ma.dental_ops.ui.view.Theme;
import ma.dental_ops.ui.view.icon.EyeIcon;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

/**
 * Champ de saisie borde arrondi avec icone a gauche, repliquant le style des
 * inputs "Adresse e-mail" / "Mot de passe" de la maquette.
 */
public class InputField extends JPanel {

    private final JTextField field;
    private PlaceholderPasswordField passwordField;

    public InputField(Icon leadingIcon, String placeholder, boolean password) {
        super(new BorderLayout(8, 0));
        setOpaque(true);
        setBackground(Theme.CARD_BG);
        setBorder(new RoundedLineBorder(Theme.BORDER, 10));

        JLabel icon = new JLabel(leadingIcon);
        add(icon, BorderLayout.WEST);

        if (password) {
            passwordField = new PlaceholderPasswordField(placeholder);
            passwordField.setEchoChar('•');
            field = passwordField;
        } else {
            field = new PlaceholderTextField(placeholder);
        }
        field.setBorder(null);
        field.setFont(Theme.FONT_FIELD);
        field.setOpaque(false);
        add(field, BorderLayout.CENTER);

        if (password) {
            JButton toggle = new JButton(new EyeIcon(18, Theme.TEXT_MUTED, false));
            toggle.setBorderPainted(false);
            toggle.setContentAreaFilled(false);
            toggle.setFocusPainted(false);
            toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
            toggle.addActionListener(e -> {
                boolean hidden = passwordField.getEchoChar() != 0;
                passwordField.setEchoChar(hidden ? (char) 0 : '•');
                toggle.setIcon(new EyeIcon(18, Theme.TEXT_MUTED, !hidden));
            });
            add(toggle, BorderLayout.EAST);
        }

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        setPreferredSize(new Dimension(1, 46));
    }

    public JTextField getField() {
        return field;
    }

    public String getText() {
        return passwordField != null
                ? new String(passwordField.getPassword())
                : field.getText();
    }
}
