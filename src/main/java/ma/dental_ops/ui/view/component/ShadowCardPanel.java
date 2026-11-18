package ma.dental_ops.ui.view.component;

import ma.dental_ops.ui.view.Theme;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Carte blanche arrondie avec ombre douce. Largeur fixe, hauteur selon le contenu.
 */
public class ShadowCardPanel extends JPanel {

    private static final int SHADOW = 22;
    private static final int SHADOW_OFFSET_Y = 6;

    private final int cardWidth;
    private final int radius;

    public ShadowCardPanel(int cardWidth, int radius) {
        this.cardWidth = cardWidth;
        this.radius = radius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(SHADOW + 34, SHADOW + 40, SHADOW + 26, SHADOW + 40));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.width = cardWidth + SHADOW * 2;
        return d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = SHADOW;
        int y = SHADOW;
        int w = getWidth() - SHADOW * 2;
        int h = getHeight() - SHADOW * 2;

        // Ombre
        for (int i = SHADOW; i > 0; i--) {
            int alpha = (int) (5 * (1 - (double) i / SHADOW)) + 1;
            g2.setColor(new Color(30, 90, 110, alpha));
            g2.fillRoundRect(x - i, y - i + SHADOW_OFFSET_Y, w + i * 2, h + i * 2,
                    radius + i * 2, radius + i * 2);
        }

        // Carte
        g2.setColor(new Color(255, 255, 255, 248));
        g2.fillRoundRect(x, y, w, h, radius * 2, radius * 2);

        // Bordure fine
        g2.setColor(Theme.CARD_BORDER);
        g2.drawRoundRect(x, y, w - 1, h - 1, radius * 2, radius * 2);

        g2.dispose();
        super.paintComponent(g);
    }
}
