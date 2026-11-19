package ma.dental_ops;

import ma.dental_ops.service.implementation.DashboardServiceImpl;
import ma.dental_ops.ui.controller.DashboardController;
import ma.dental_ops.ui.view.DashboardPanel;

import javax.swing.SwingUtilities;

public class DashboardApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardController controller =
                    new DashboardController(new DashboardServiceImpl());

            DashboardPanel view = new DashboardPanel(controller);
            view.setVisible(true);
        });
    }
}