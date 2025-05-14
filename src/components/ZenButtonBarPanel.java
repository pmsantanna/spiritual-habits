package components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ZenButtonBarPanel extends JPanel {

    public ZenButtonBarPanel() {
        setOpaque(false);
        setBackground(Color.decode("#BFBDA6"));
        setLayout(new MigLayout("insets 0 10 0 10, gapx 12, align center", "[]", "[]"));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 10));
        setPreferredSize(new Dimension(395, 48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        int arc = 0;
        int shadow = 4;
        int w = getWidth() - shadow;
        int h = getHeight() - shadow;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // sombra
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillRoundRect(shadow, shadow, w, h, arc, arc);

        // fundo
        g2.setColor(getBackground());
        g2.setColor(Color.decode("#CDC8B0"));
        g2.fillRoundRect(0, 0, w, h, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
