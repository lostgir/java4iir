
package ma.dental_ops.ui.components;

import ma.dental_ops.utils.AppColors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SecondaryButton extends JButton {

    public SecondaryButton(String texte) {
        super(texte);

        setBackground(Color.WHITE);
        setForeground(AppColors.PETROL_BLUE);

        setFont(new Font("SansSerif", Font.BOLD, 13));
        setFocusPainted(false);
        setOpaque(true);

        setBorder(new LineBorder(AppColors.TURQUOISE, 1, true));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(130, 38));
    }
}
