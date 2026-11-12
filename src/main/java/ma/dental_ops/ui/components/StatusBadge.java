
package ma.dental_ops.ui.components;

import ma.dental_ops.utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBadge extends JLabel {

    public StatusBadge(String statut) {
        super(statut);

        setFont(new Font("SansSerif", Font.BOLD, 12));
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new EmptyBorder(5, 12, 5, 12));

        switch (statut.toUpperCase()) {
            case "CONFIRME":
            case "CONFIRMÉ":
                setBackground(new Color(220, 245, 230));
                setForeground(AppColors.SUCCESS);
                break;

            case "EN ATTENTE":
                setBackground(new Color(255, 242, 220));
                setForeground(AppColors.WARNING);
                break;

            case "ANNULE":
            case "ANNULÉ":
                setBackground(new Color(255, 225, 225));
                setForeground(AppColors.DANGER);
                break;

            default:
                setBackground(AppColors.LIGHT_TURQUOISE);
                setForeground(AppColors.PETROL_BLUE);
        }
    }
}
