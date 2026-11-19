package ma.dental_ops.ui.view.component;

import ma.dental_ops.ui.view.Theme;

import javax.swing.JButton;
import java.awt.Cursor;
import java.util.Map;

/**
 * Texte cliquable souligne (ex. "Mot de passe oublie ?").
 */
public class LinkLabel extends JButton {

    public LinkLabel(String text) {
        super("<html><u>" + text + "</u></html>");
        setForeground(Theme.TEAL_ACCENT);
        setFont(Theme.FONT_LINK);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
