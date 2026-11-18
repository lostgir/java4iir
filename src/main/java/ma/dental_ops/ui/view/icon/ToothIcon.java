package ma.dental_ops.ui.view.icon;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

/**
 * Silhouette stylisee d'une molaire (deux bosses de couronne, deux racines),
 * dessinee en vectoriel pour reproduire le logo DentalSoft sans asset externe.
 */
public class ToothIcon implements Icon {

    private final int width;
    private final int height;
    private final Color color;

    public ToothIcon(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(width * 0.06f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        float w = width;
        float h = height;

        Path2D.Float p = new Path2D.Float();
        p.moveTo(w * 0.28f, h * 0.02f);
        p.quadTo(w * 0.40f, h * 0.00f, w * 0.50f, h * 0.10f);
        p.quadTo(w * 0.60f, h * 0.00f, w * 0.72f, h * 0.02f);
        p.quadTo(w * 0.96f, h * 0.08f, w * 0.98f, h * 0.26f);
        p.quadTo(w * 1.00f, h * 0.42f, w * 0.88f, h * 0.52f);
        p.lineTo(w * 0.90f, h * 0.70f);
        p.quadTo(w * 0.90f, h * 0.90f, w * 0.74f, h * 0.98f);
        p.quadTo(w * 0.65f, h * 1.00f, w * 0.63f, h * 0.88f);
        p.lineTo(w * 0.58f, h * 0.62f);
        p.quadTo(w * 0.54f, h * 0.56f, w * 0.50f, h * 0.60f);
        p.quadTo(w * 0.46f, h * 0.56f, w * 0.42f, h * 0.62f);
        p.lineTo(w * 0.37f, h * 0.88f);
        p.quadTo(w * 0.35f, h * 1.00f, w * 0.26f, h * 0.98f);
        p.quadTo(w * 0.10f, h * 0.90f, w * 0.10f, h * 0.70f);
        p.lineTo(w * 0.12f, h * 0.52f);
        p.quadTo(w * 0.00f, h * 0.42f, w * 0.02f, h * 0.26f);
        p.quadTo(w * 0.04f, h * 0.08f, w * 0.28f, h * 0.02f);
        p.closePath();

        g2.draw(p);
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
