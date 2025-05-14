package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ZenFrame extends JFrame {

    private Point initialClick;

    public ZenFrame(JPanel content) {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 0;
                int shadow = 6;
                int width = getWidth();
                int height = getHeight();

                // sombra (com offset positivo)
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillRoundRect(shadow, shadow, width - shadow * 2, height - shadow * 2, arc, arc);

                // fundo (dentro da sombra)
                g2.setColor(Color.decode("#BFBDA6"));
                g2.fillRoundRect(0, 0, width - shadow * 2, height - shadow * 2, arc, arc);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };

        root.setBorder(BorderFactory.createEmptyBorder(0, -16, 11, 11));
        root.setLayout(new BorderLayout());
        root.setOpaque(false);

        JPanel contentWithTopBar = new JPanel(new BorderLayout());
        contentWithTopBar.setOpaque(false);

        JPanel topBar = createTopBar();

        contentWithTopBar.add(topBar, BorderLayout.NORTH);
        contentWithTopBar.add(content, BorderLayout.CENTER);

        topBar.setBackground(Color.decode("#383530"));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(topBar, BorderLayout.NORTH);
        wrapper.add(content, BorderLayout.CENTER);

        // Permitir arrastar a janela ao clicar no root
        root.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick); // registrar o clique
            }
        });

        root.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // mover a janela com base no deslocamento
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;

                setLocation(X, Y);
            }
        });

        root.add(wrapper, BorderLayout.CENTER);
        setContentPane(root);

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JButton createCloseButton() {
        JButton closeBtn = new JButton("✕");
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        closeBtn.setForeground(Color.decode("#D7D1B7"));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.setToolTipText("Fechar");

        closeBtn.setBorder(null);
        closeBtn.setPreferredSize(new Dimension(30, 30));
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setOpaque(false);

        // Efeito ao passar o mouse
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                closeBtn.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                closeBtn.setForeground(Color.decode("#D7D1B7"));
            }
        });

        closeBtn.addActionListener(e -> fadeAndClose());

        return closeBtn;
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(true);
        topBar.setPreferredSize(new Dimension(1, 40)); // limite de altura
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding sutil
        topBar.setBackground(Color.decode("#4B483F"));

        JButton closeBtn = createCloseButton();
        topBar.add(closeBtn, BorderLayout.EAST);

        return topBar;
    }

    private void fadeAndClose() {
        new Thread(() -> {
            try {
                for (float opacity = 1.0f; opacity >= 0; opacity -= 0.05f) {
                    float clamped = Math.max(opacity, 0f);
                    SwingUtilities.invokeLater(() -> setOpacity(clamped));
                    Thread.sleep(15);
                }
                dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
