
package ma.dental_ops.config;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.UIManager;
import java.awt.Font;

public final class ThemeConfig {

    private ThemeConfig() {
        // Classe utilitaire
    }

    public static void applyTheme() {

        // Activer le thème clair FlatLaf
        FlatLightLaf.setup();

        // Police générale
        UIManager.put(
                "defaultFont",
                new Font("SansSerif", Font.PLAIN, 14)
        );

        // Angles arrondis
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("TextComponent.arc", 10);

        // Style des boutons
        UIManager.put("Button.focusWidth", 0);
        UIManager.put("Component.focusWidth", 1);
    }
}
