package ma.dental_ops.ui.view.icon;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class EyeIcon implements Icon {

    private final int size;
    private final java.awt.Color color;
    private final boolean crossed;

    public EyeIcon(int size, java.awt.Color color, boolean crossed) {
        this.size = size;
        this.color = color;
        this.crossed = crossed;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.6f));

        int w = size;
        int h = (int) (size * 0.6);
        int top = (size - h) / 2;

        g2.draw(new Ellipse2D.Float(0, top, w, h));
        int pupil = (int) (size * 0.26);
        g2.fillOval(w / 2 - pupil / 2, size / 2 - pupil / 2, pupil, pupil);

        if (crossed) {
            g2.draw(new java.awt.geom.Line2D.Float(0, size, w, 0));
        }

        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
