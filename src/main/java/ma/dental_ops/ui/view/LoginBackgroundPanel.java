package ma.dental_ops.ui.view;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 * Fond de l'ecran de connexion : degrade, vagues, arc, courbe fine,
 * grilles de points et grande dent en filigrane.
 */
public class LoginBackgroundPanel extends JPanel {

    public LoginBackgroundPanel() {
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int w = getWidth();
        int h = getHeight();

        // 1. Degrade de base
        g2.setPaint(new GradientPaint(0, 0, new Color(0xF1, 0xF9, 0xFC),
                w * 0.3f, h, new Color(0xD8, 0xEE, 0xF6)));
        g2.fillRect(0, 0, w, h);

        // 2. Voile clair en haut a droite
        Path2D topRight = new Path2D.Double();
        topRight.moveTo(w * 0.55, 0);
        topRight.curveTo(w * 0.68, h * 0.30, w * 0.85, h * 0.18, w, h * 0.22);
        topRight.lineTo(w, 0);
        topRight.closePath();
        g2.setColor(new Color(205, 235, 246, 110));
        g2.fill(topRight);

        // 3. Vagues du bas
        Path2D wave1 = new Path2D.Double();
        wave1.moveTo(0, h * 0.50);
        wave1.curveTo(w * 0.22, h * 0.42, w * 0.38, h * 0.92, w * 0.62, h * 0.86);
        wave1.curveTo(w * 0.80, h * 0.81, w * 0.92, h * 0.66, w, h * 0.58);
        wave1.lineTo(w, h);
        wave1.lineTo(0, h);
        wave1.closePath();
        g2.setPaint(new GradientPaint(0, (float) (h * 0.5), new Color(190, 228, 242, 120),
                0, h, new Color(165, 215, 236, 150)));
        g2.fill(wave1);

        Path2D wave2 = new Path2D.Double();
        wave2.moveTo(0, h * 0.66);
        wave2.curveTo(w * 0.25, h * 0.58, w * 0.40, h * 1.00, w * 0.70, h * 0.95);
        wave2.curveTo(w * 0.86, h * 0.92, w * 0.95, h * 0.80, w, h * 0.74);
        wave2.lineTo(w, h);
        wave2.lineTo(0, h);
        wave2.closePath();
        g2.setColor(new Color(160, 214, 236, 90));
        g2.fill(wave2);

        Path2D wave3 = new Path2D.Double();
        wave3.moveTo(0, h * 0.82);
        wave3.curveTo(w * 0.30, h * 0.76, w * 0.55, h * 1.02, w, h * 0.90);
        wave3.lineTo(w, h);
        wave3.lineTo(0, h);
        wave3.closePath();
        g2.setColor(new Color(150, 208, 232, 70));
        g2.fill(wave3);

        // 4. Grande dent en filigrane
        double toothH = h * 0.48;
        Shape tooth = toothShape(w * 0.87, h * 0.47, toothH, Math.toRadians(-12));
        g2.setStroke(new BasicStroke((float) (toothH * 0.065), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(255, 255, 255, 120));
        g2.draw(tooth);

        // 5. Lignes fines
        g2.setStroke(new BasicStroke(1.6f));
        g2.setColor(new Color(110, 195, 222, 150));

        double r = w * 0.20;
        double cx = w * 0.04;
        double cy = -h * 0.02;
        g2.draw(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));

        Path2D line = new Path2D.Double();
        line.moveTo(0, h * 0.57);
        line.curveTo(w * 0.22, h * 0.55, w * 0.27, h * 0.98, w * 0.55, h * 0.97);
        line.curveTo(w * 0.76, h * 0.96, w * 0.86, h * 0.72, w, h * 0.64);
        g2.draw(line);

        // pastille sur l'arc
        double a = Math.toRadians(48);
        double px = cx + r * Math.cos(a);
        double py = cy + r * Math.sin(a);
        g2.setColor(new Color(178, 224, 240));
        g2.fill(new Ellipse2D.Double(px - 11, py - 11, 22, 22));

        // 6. Grilles de points
        drawDots(g2, (int) (w * 0.045), (int) (h * 0.12), 4, 4, 26, 4, new Color(150, 205, 228, 150));
        drawDots(g2, (int) (w * 0.165), (int) (h * 0.42), 7, 4, 18, 3, new Color(150, 205, 228, 110));
        drawDots(g2, (int) (w * 0.865), (int) (h * 0.78), 5, 5, 22, 4, new Color(150, 205, 228, 150));

        g2.dispose();
    }

    private static void drawDots(Graphics2D g2, int x, int y, int cols, int rows,
                                 int gap, int radius, Color color) {
        g2.setColor(color);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g2.fillOval(x + col * gap - radius, y + row * gap - radius, radius * 2, radius * 2);
            }
        }
    }

    private static Shape toothShape(double cx, double cy, double height, double rotation) {
        Path2D p = new Path2D.Double();
        p.moveTo(50, 18);
        p.curveTo(40, 8, 22, 4, 12, 14);
        p.curveTo(0, 26, 4, 48, 14, 62);
        p.curveTo(20, 72, 22, 90, 26, 104);
        p.curveTo(28, 110, 36, 110, 38, 102);
        p.curveTo(41, 90, 44, 78, 50, 76);
        p.curveTo(56, 78, 59, 90, 62, 102);
        p.curveTo(64, 110, 72, 110, 74, 104);
        p.curveTo(78, 90, 80, 72, 86, 62);
        p.curveTo(96, 48, 100, 26, 88, 14);
        p.curveTo(78, 4, 60, 8, 50, 18);
        p.closePath();

        double scale = height / 110.0;
        AffineTransform t = new AffineTransform();
        t.translate(cx, cy);
        t.rotate(rotation);
        t.scale(scale, scale);
        t.translate(-50, -55);
        return t.createTransformedShape(p);
    }
}
