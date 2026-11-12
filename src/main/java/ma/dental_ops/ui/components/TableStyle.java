
package ma.dental_ops.utils;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableStyle {

    public static void appliquerStyle(JTable table) {

        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(38);

        table.setBackground(Color.WHITE);
        table.setForeground(AppColors.DARK_BLUE);

        table.setSelectionBackground(AppColors.LIGHT_TURQUOISE);
        table.setSelectionForeground(AppColors.DARK_BLUE);

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();

        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(AppColors.LIGHT_TURQUOISE);
        header.setForeground(AppColors.PETROL_BLUE);
        header.setPreferredSize(new Dimension(0, 42));

        header.setReorderingAllowed(false);
    }

    private TableStyle() {
    }
}
