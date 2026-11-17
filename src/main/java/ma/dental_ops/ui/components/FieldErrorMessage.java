
package ma.dental_ops.ui.components;

import java.awt.*;
import javax.swing.*;

public class FieldErrorMessage extends JLabel {

    public FieldErrorMessage(String message) {
        super(message);

        setFont(new Font("SansSerif", Font.PLAIN, 11));
        setForeground(new Color(229, 57, 53));
    }

    public void afficherErreur(String message) {
        setText(message);
        setVisible(true);
    }

    public void effacerErreur() {
        setText("");
        setVisible(false);
    }
}
