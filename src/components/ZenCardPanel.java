package components;

import javax.swing.*;
import java.awt.*;

public class ZenCardPanel extends JPanel {

    public ZenCardPanel() {
        setOpaque(false);
        setBackground(Color.decode("#DAD4BB"));
        setPreferredSize(new Dimension(480, getPreferredSize().height));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Textura
        Image texture = new ImageIcon(getClass().getResource("/textures/textures.png")).getImage();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f));

        // Tiling da textura
        int w = getWidth();
        int h = getHeight();
        for (int x = 0; x < w; x += texture.getWidth(null)) {
            for (int y = 0; y < h; y += texture.getHeight(null)) {
                g2.drawImage(texture, x, y, null);
            }
        }

        g2.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
