
package ma.dental_ops.ui.components;

import ma.dental_ops.utils.AppColors;

import javax.swing.*;
import java.awt.*;

public class PrimaryButton extends JButton {

    public PrimaryButton(String texte) {
        super(texte);

        setBackground(AppColors.TURQUOISE);
        setForeground(Color.WHITE);

        setFont(new Font("SansSerif", Font.BOLD, 13));
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(true);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(130, 38));
    }
}
