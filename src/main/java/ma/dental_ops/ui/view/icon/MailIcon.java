package ma.dental_ops.ui.view.icon;

import javax.swing.Icon;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public class MailIcon implements Icon {

    private final int size;
    private final java.awt.Color color;

    public MailIcon(int size, java.awt.Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.setColor(color);
        g2.setStroke(new java.awt.BasicStroke(1.6f));

        int w = size;
        int h = (int) (size * 0.72);
        int top = (size - h) / 2;

        g2.draw(new RoundRectangle2D.Float(0, top, w, h, 3, 3));

        Path2D flap = new Path2D.Float();
        flap.moveTo(1, top + 2);
        flap.lineTo(w / 2.0, top + h * 0.58);
        flap.lineTo(w - 1, top + 2);
        g2.draw(flap);

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
