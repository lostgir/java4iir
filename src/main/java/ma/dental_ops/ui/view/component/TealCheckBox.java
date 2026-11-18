package ma.dental_ops.ui.view.component;

import ma.dental_ops.ui.view.Theme;
import ma.dental_ops.ui.view.icon.CheckIcon;

import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 * Case a cocher stylisee : carre arrondi rempli en teal avec coche blanche
 * quand selectionnee, bordure grise sinon (cf. "Se souvenir de moi").
 */
public class TealCheckBox extends JCheckBox {

    private static final int BOX = 18;

    public TealCheckBox(String text) {
        super(text);
        setFont(Theme.FONT_CHECK);
        setForeground(Theme.TEXT_LABEL);
        setIconTextGap(8);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setIcon(new BoxIcon(false));
        setSelectedIcon(new BoxIcon(true));
    }

    private static class BoxIcon implements javax.swing.Icon {
        private final boolean selected;

        BoxIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.translate(x, y);

            if (selected) {
                g2.setColor(Theme.TEAL_ACCENT);
                g2.fill(new RoundRectangle2D.Float(0, 0, BOX, BOX, 5, 5));
                new CheckIcon(BOX - 4, Color.WHITE).paintIcon(c, g2, 2, 2);
            } else {
                g2.setColor(Theme.BORDER);
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, BOX - 1, BOX - 1, 5, 5));
            }

            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return BOX;
        }

        @Override
        public int getIconHeight() {
            return BOX;
        }
    }
}
