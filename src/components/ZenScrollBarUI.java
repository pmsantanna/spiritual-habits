package components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ZenScrollBarUI extends BasicScrollBarUI {

    private static final Color THUMB_COLOR = new Color(28, 28, 28);
    private static final Color TRACK_COLOR = new Color(205, 200, 176);

    @Override
    protected void configureScrollBarColors() {
        thumbColor = THUMB_COLOR;
        trackColor = TRACK_COLOR;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setVisible(false);
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (!c.isEnabled() || thumbBounds.width > thumbBounds.height) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Base do thumb (preenchimento escuro)
        g2.setColor(new Color(28, 28, 28));
        g2.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);

        // Borda clara interna (visual sutil de 1px)
        g2.setColor(new Color(255, 255, 255, 80));
        g2.drawRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1);

        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fundo da trilha
        g2.setColor(new Color(205, 200, 176));
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

        int shadowSize = 86; // mais largo para difusão

        // Função auxiliar para sombra difusa
        for (int i = 0; i < shadowSize; i++) {
            float fade = 1f - ((float) i / shadowSize);
            int alpha = (int) (40 * fade * fade); // curva para suavidade realista
            Color shadow = new Color(0, 0, 0, alpha);

            // Esquerda
            g2.setColor(shadow);
            g2.fillRect(trackBounds.x - i, trackBounds.y - i, 1, trackBounds.height + (2 * i));

            // Direita
            g2.fillRect(trackBounds.x + trackBounds.width + i - 1, trackBounds.y - i, 1, trackBounds.height + (2 * i));

            // Topo
            g2.fillRect(trackBounds.x - i, trackBounds.y - i, trackBounds.width + (2 * i), 1);

            // Base
            g2.fillRect(trackBounds.x - i, trackBounds.y + trackBounds.height + i - 1, trackBounds.width + (2 * i), 1);
        }

        g2.dispose();
    }
}
