package ma.dental_ops.ui.view.component;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {

    private final int arc;
    private final Color background;
    private final Color shadow;

    public RoundedPanel(int arc, Color background) {
        this.arc = arc;
        this.background = background;
        this.shadow = new Color(0, 0, 0, 25);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(shadow);
        g2.fill(new RoundRectangle2D.Float(3, 6, w - 6, h - 6, arc, arc));

        g2.setColor(background);
        g2.fill(new RoundRectangle2D.Float(0, 0, w - 4, h - 8, arc, arc));

        g2.dispose();
        super.paintComponent(g);
    }
}
