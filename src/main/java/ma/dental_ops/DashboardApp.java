package ma.dental_ops;

import ma.dental_ops.config.ApplicationContext;
import ma.dental_ops.service.api.DashboardService;
import ma.dental_ops.ui.controller.DashboardController;
import ma.dental_ops.ui.view.DashboardView;

import javax.swing.SwingUtilities;

public class DashboardApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardController controller = new DashboardController(
                    ApplicationContext.getInstance().getBean(DashboardService.class));

            DashboardView view = new DashboardView(controller);
            view.setVisible(true);
        });
    }
}
