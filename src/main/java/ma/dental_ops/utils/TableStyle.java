
package ma.dental_ops.utils;

import ma.dental_ops.ui.styles.AppColors;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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

        // Lignes alternées
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column);

                        if (isSelected) {
                            component.setBackground(AppColors.LIGHT_TURQUOISE);
                            component.setForeground(AppColors.DARK_BLUE);
                        } else {
                            int modelRow = table.convertRowIndexToModel(row);

                            component.setBackground(
                                    modelRow % 2 == 0
                                            ? Color.WHITE
                                            : AppColors.BACKGROUND
                            );

                            component.setForeground(AppColors.DARK_BLUE);
                        }

                        return component;
                    }
                };

        table.setDefaultRenderer(Object.class, renderer);
        table.setDefaultRenderer(Number.class, renderer);

        // En-tête du tableau
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
