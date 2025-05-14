package view;

import com.formdev.flatlaf.util.Animator;
import components.*;
import controller.HabitController;
import model.Habit;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class MainView extends JPanel {

    private final HabitController controller;

    private ZenCardPanel card;

    JPanel checkboxContent = new JPanel(new MigLayout("wrap 1, gapy 10", "[grow]"));

    public MainView() {
        controller = new HabitController();
        setLayout(new MigLayout("wrap 1, insets 5", "[grow]"));
        setOpaque(false);
        initUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Image texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("/textures/textures.png"))).getImage();

        // Clip apenas no centro da tela (evitar cobrir as sombras)
        int margemInterna = 10;
        Shape safeArea = new Rectangle(
                margemInterna,
                margemInterna,
                getWidth() - (2 * margemInterna),
                getHeight() - (2 * margemInterna)
        );

        g2.setClip(safeArea);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.07f));

        int w = getWidth();
        int h = getHeight();
        int tw = texture.getWidth(null);
        int th = texture.getHeight(null);

        for (int x = 0; x < w; x += tw) {
            for (int y = 0; y < h; y += th) {
                g2.drawImage(texture, x, y, null);
            }
        }

        g2.dispose();
    }

    private void initUI() {
        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow]", "[]10[]"));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JLabel title = new JLabel("Hábitos do Dia");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, "growx");

        Font zenFont = new Font("Segoe UI", Font.PLAIN, 16);
        title.setFont(zenFont);

        card = new ZenCardPanel();
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(600, 400));
        card.removeAll();

        checkboxContent.setOpaque(false);

        reloadHabits();

        card.revalidate();
        card.repaint();

        ZenButtonBarPanel buttonBar = new ZenButtonBarPanel();

        JButton btn1 = new JButton("Criar Hábito");
        JButton btn2 = new JButton("Resumo");
        JButton btn3 = new JButton("Fechar");

        Color normal = Color.decode("#CDC8B0");
        Color hover = Color.decode("#4E4C43");

        // Estilizar os botões
        for (JButton b : List.of(btn1, btn2, btn3)) {
            b.setFocusPainted(false);
            b.setBackground(Color.decode("#B4AF9A"));
            b.setBackground(Color.decode("#CDC8B0"));
            b.setForeground(new Color(40, 40, 40));
            b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));

            applyZenHoverEffect(b, normal, hover);
        }

        buttonBar.add(btn1);
        buttonBar.add(btn2);
        buttonBar.add(btn3);

        btn1.addActionListener(e -> {
            JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
            HabitFormPanel form = new HabitFormPanel(dialog, habit -> {
                controller.addHabit(habit);
                controller.reloadFromDisk();
                reloadHabits();
                dialog.dispose();
            });
            dialog.setUndecorated(true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        btn2.addActionListener(e -> {
            new EmojiWeeklyView().setVisible(true);
        });

        btn3.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) window.dispose();
        });

        JScrollPane innerScroll = new JScrollPane(checkboxContent);
        innerScroll.setBorder(null);
        innerScroll.setOpaque(false);
        innerScroll.getViewport().setOpaque(false);
        innerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        innerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Scroll suave
        innerScroll.getVerticalScrollBar().setUnitIncrement(16);

        int scrollAlturaMinima = 400;

        checkboxContent.setPreferredSize(null);
        innerScroll.setPreferredSize(new Dimension(1, scrollAlturaMinima));
        innerScroll.setMinimumSize(new Dimension(1, scrollAlturaMinima));
        innerScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        innerScroll.getVerticalScrollBar().setUnitIncrement(16);
        innerScroll.setBorder(null);
        innerScroll.setOpaque(false);
        innerScroll.getViewport().setOpaque(false);

        innerScroll.setVerticalScrollBar(new ZenScrollBar());

        card.setLayout(new BorderLayout());
        card.add(innerScroll, BorderLayout.CENTER);

        panel.add(card, "align center, growx");
        panel.add(buttonBar, "align center, growx");

        add(panel, "growy, pushy, align center");
    }

    private Icon createZenCheckboxIcon(Color fillColor) {
        int size = 20;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);
        g2.fillRect(0, 0, size, size);

        g2.dispose();
        return new ImageIcon(image);
    }

    private void animateCheckboxFill(JCheckBox checkBox) {
        Animator animator = new Animator(200, fraction -> {
            int shade = (int) (255 - (fraction * 215));
            Color fillColor = new Color(shade, shade, shade);
            Icon icon = createZenCheckboxIcon(fillColor);
            checkBox.setIcon(icon);
        });

        animator.setResolution(10);
        animator.start();
    }

    private JPanel createCheckboxForHabit(Habit habit) {
        Color base = Color.decode("#C0BCA5");
        Color hover = Color.decode("#DAD5BA");
        Color checked = new Color(40, 40, 40);

        Icon uncheckedIcon = createZenCheckboxIcon(base);
        Icon hoverIcon = createZenCheckboxIcon(hover);
        Icon checkedIcon = createZenCheckboxIcon(checked);

        JCheckBox checkBox = new JCheckBox(habit.getName());
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setForeground(new Color(40, 40, 40));
        checkBox.setOpaque(false);
        checkBox.setFocusPainted(false);
        checkBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        checkBox.setIcon(uncheckedIcon);
        checkBox.setSelectedIcon(checkedIcon);
        checkBox.setPressedIcon(checkedIcon);
        checkBox.setIconTextGap(12);
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        checkBox.setSelected(controller.isHabitDone(habit));

        checkBox.addActionListener(e -> {
            controller.toggleHabit(habit);

            if (checkBox.isSelected()) {
                animateCheckboxFill(checkBox);
            } else {
                checkBox.setIcon(uncheckedIcon);
            }
        });

        checkBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!checkBox.isSelected()) {
                    checkBox.setIcon(hoverIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!checkBox.isSelected()) {
                    checkBox.setIcon(uncheckedIcon);
                }
            }
        });

        JButton removeButton = new JButton();
        removeButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/trash.png"))));

        removeButton.setContentAreaFilled(false);
        removeButton.setBorderPainted(false);
        removeButton.setFocusPainted(false);
        removeButton.setOpaque(false);
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeButton.setToolTipText("Remover hábito");

        removeButton.setVisible(false);

        removeButton.addActionListener(e -> {
            boolean confirm = ZenConfirmDialog.show(SwingUtilities.getWindowAncestor(this),
                    "Deseja realmente excluir o hábito \"" + habit.getName() + "\"?");

            if (confirm) {
                controller.removeHabit(habit);
                reloadHabits();
            }
        });

        MouseAdapter hoverEffect = new MouseAdapter() {
            Timer hideTimer;

            @Override
            public void mouseEntered(MouseEvent e) {
                if (hideTimer != null && hideTimer.isRunning()) {
                    hideTimer.stop();
                }
                removeButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hideTimer != null && hideTimer.isRunning()) {
                    hideTimer.stop();
                }

                hideTimer = new Timer(100, evt -> removeButton.setVisible(false));
                hideTimer.setRepeats(false);
                hideTimer.start();
            }
        };


        JPanel linePanel = new JPanel(new BorderLayout());
        linePanel.setOpaque(false);
        linePanel.add(checkBox, BorderLayout.CENTER);
        linePanel.add(removeButton, BorderLayout.EAST);

        checkBox.addMouseListener(hoverEffect);
        linePanel.addMouseListener(hoverEffect);
        removeButton.addMouseListener(hoverEffect);

        return linePanel;
    }

    private void playZenClick() {
        try {
            Toolkit.getDefaultToolkit().beep();

            // alternativa: som customizado com wav (ex: "click.wav" na pasta resources)
            // AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/click.wav"));
            // Clip clip = AudioSystem.getClip();
            // clip.open(audioIn);
            // clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reloadHabits() {
        checkboxContent.removeAll();
        for (Habit h : controller.getHabits()) {
            JPanel item = createCheckboxForHabit(h);
            checkboxContent.add(item, "growx");
        }

        checkboxContent.setPreferredSize(new Dimension(1,
                Math.max(checkboxContent.getPreferredSize().height, 180)));

        checkboxContent.revalidate();
        checkboxContent.repaint();
    }

    private void applyZenHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.setBackground(normalColor);

        Color corOriginal = button.getForeground();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setForeground(Color.decode("#D8D2BA"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
                button.setForeground(corOriginal);
            }
        });
    }
}