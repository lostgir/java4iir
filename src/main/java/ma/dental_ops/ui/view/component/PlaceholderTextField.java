package ma.dental_ops.ui.view.component;

import ma.dental_ops.ui.view.Theme;

import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PlaceholderTextField extends JTextField {

    private final String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && placeholder != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Theme.TEXT_PLACEHOLDER);
            g2.setFont(getFont());
            int textY = (getHeight() + g2.getFontMetrics().getAscent()) / 2 - 2;
            g2.drawString(placeholder, getInsets().left, textY);
            g2.dispose();
        }
    }
}
