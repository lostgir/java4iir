package ma.dental_ops.ui.view.icon;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;

public class LockIcon implements Icon {

    private final int size;
    private final java.awt.Color color;

    public LockIcon(int size, java.awt.Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.6f));

        int bodyW = (int) (size * 0.78);
        int bodyH = (int) (size * 0.55);
        int bodyX = (size - bodyW) / 2;
        int bodyY = size - bodyH - 1;

        int shackleD = (int) (size * 0.62);
        int shackleX = (size - shackleD) / 2;
        g2.draw(new Arc2D.Float(shackleX, 0, shackleD, shackleD * 1.3f, 0, 180, Arc2D.OPEN));

        g2.fill(new RoundRectangle2D.Float(bodyX, bodyY, bodyW, bodyH, 3, 3));

        g2.setColor(java.awt.Color.WHITE);
        g2.fillOval(bodyX + bodyW / 2 - 2, bodyY + bodyH / 2 - 3, 4, 4);

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
