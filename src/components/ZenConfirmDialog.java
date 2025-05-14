package components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class ZenConfirmDialog extends JDialog {

    private boolean confirmed = false;

    public ZenConfirmDialog(Window parent, String message) {
        super(parent);
        setUndecorated(true);
        setModal(true);
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));

        JPanel content = new JPanel(new MigLayout("wrap 1, center", "[grow, center]", "[]20[]"));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.decode("#CDC8B0"));
        content.setPreferredSize(new Dimension(460, 200));
        content.setOpaque(true);

        JLabel icon = new JLabel("🗑️", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        content.add(icon);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        content.add(label, "gapbottom 10");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttons.setOpaque(false);

        JButton btnYes = new JButton("Sim");
        JButton btnNo = new JButton("Cancelar");

        for (JButton btn : List.of(btnYes, btnNo)) {
            btn.setFocusPainted(false);
            btn.setBackground(Color.decode("#BFBDA6"));
            btn.setForeground(new Color(40, 40, 40));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        btnYes.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        btnNo.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttons.add(btnYes);
        buttons.add(btnNo);
        content.add(buttons);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(createTopBar(), BorderLayout.NORTH);
        wrapper.add(content, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(true);
        topBar.setPreferredSize(new Dimension(1, 36));
        topBar.setBackground(Color.decode("#4B483F"));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JButton closeBtn = new JButton("✕");
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        closeBtn.setForeground(Color.decode("#D7D1B7"));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.setBorder(null);
        closeBtn.setPreferredSize(new Dimension(30, 30));
        closeBtn.setOpaque(false);

        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeBtn.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeBtn.setForeground(Color.decode("#D7D1B7"));
            }
        });

        closeBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        topBar.add(closeBtn, BorderLayout.EAST);
        return topBar;
    }


    public static boolean show(Window parent, String message) {
        ZenConfirmDialog dialog = new ZenConfirmDialog(parent, message);
        dialog.setVisible(true);
        return dialog.confirmed;
    }
}
