
package ma.dental_ops.ui.components;

import ma.dental_ops.utils.AppColors;

import javax.swing.*;
import java.awt.*;

public class ScreenHeader extends JPanel {

    public ScreenHeader(String titre, String description) {

        setLayout(new BorderLayout(0, 5));
        setBackground(AppColors.BACKGROUND);

        JLabel labelTitre = new JLabel(titre);
        labelTitre.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelTitre.setForeground(AppColors.DARK_BLUE);

        JLabel labelDescription = new JLabel(description);
        labelDescription.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelDescription.setForeground(Color.GRAY);

        add(labelTitre, BorderLayout.NORTH);
        add(labelDescription, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    }
}
