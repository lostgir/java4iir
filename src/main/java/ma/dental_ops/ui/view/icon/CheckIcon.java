package ma.dental_ops.ui.view.icon;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

public class CheckIcon implements Icon {

    private final int size;
    private final java.awt.Color color;

    public CheckIcon(int size, java.awt.Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Path2D check = new Path2D.Float();
        check.moveTo(size * 0.18f, size * 0.52f);
        check.lineTo(size * 0.42f, size * 0.76f);
        check.lineTo(size * 0.85f, size * 0.22f);
        g2.draw(check);

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
