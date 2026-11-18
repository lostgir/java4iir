package ma.dental_ops.ui.view.icon;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

public class ArrowIcon implements Icon {

    private final int size;
    private final java.awt.Color color;

    public ArrowIcon(int size, java.awt.Color color) {
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

        int midY = size / 2;
        g2.drawLine(0, midY, size - 2, midY);

        Path2D head = new Path2D.Float();
        head.moveTo(size * 0.55f, midY - size * 0.35f);
        head.lineTo(size - 1, midY);
        head.lineTo(size * 0.55f, midY + size * 0.35f);
        g2.draw(head);

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
