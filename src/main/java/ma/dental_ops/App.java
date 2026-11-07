package ma.dental_ops;

import ma.dental_ops.service.implementation.ChargeServiceImpl;
import ma.dental_ops.ui.controller.ChargeController;
import ma.dental_ops.ui.view.ChargeView;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChargeController controller =
                    new ChargeController(new ChargeServiceImpl());

            ChargeView view = new ChargeView(controller);
            view.setVisible(true);
        });
    }
}