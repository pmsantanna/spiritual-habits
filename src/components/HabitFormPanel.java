package components;

import model.Habit;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class HabitFormPanel extends JPanel {

    public HabitFormPanel(JDialog dialog, Consumer<Habit> onSave) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(480, 500));

        Font zenFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(1, 40));
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

        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeBtn.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeBtn.setForeground(Color.decode("#D7D1B7"));
            }
        });

        closeBtn.addActionListener(e -> dialog.dispose());
        topBar.add(closeBtn, BorderLayout.EAST);

        ZenCardPanel content = new ZenCardPanel();
        content.setLayout(new MigLayout("wrap 2, insets 20", "[right][grow]"));
        content.setOpaque(false);

        JLabel titleLabel = new JLabel("Criar Novo Hábito");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(40, 40, 40));

        JTextField nameField = new JTextField();
        nameField.setFont(zenFont);

        JTextArea descriptionArea = new JTextArea(3, 20);
        descriptionArea.setFont(zenFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(180, 175, 154)));
        descScroll.setOpaque(false);
        descScroll.getViewport().setOpaque(false);

        String[] categorias = {"Saúde", "Mente", "Gratidão", "Espiritualidade", "Outro"};
        JComboBox<String> categoryBox = new JComboBox<>(categorias);
        categoryBox.setFont(zenFont);

        SpinnerNumberModel scoreModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner scoreSpinner = new JSpinner(scoreModel);
        ((JSpinner.DefaultEditor) scoreSpinner.getEditor()).getTextField().setFont(zenFont);

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        for (JButton b : new JButton[]{saveButton, cancelButton}) {
            b.setFocusPainted(false);
            b.setBackground(Color.decode("#CDC8B0"));
            b.setForeground(new Color(40, 40, 40));
            b.setFont(zenFont);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        }

        content.add(titleLabel, "span 2, align center, gapbottom 20");

        content.add(new JLabel("Nome:"), "gapright 10");
        content.add(nameField, "growx");

        content.add(new JLabel("Descrição:"), "gapright 10, gaptop 10, aligny top");
        content.add(descScroll, "growx, gaptop 10, height 60::150");

        content.add(new JLabel("Categoria:"), "gapright 10, gaptop 10");
        content.add(categoryBox, "growx, gaptop 10");

        content.add(new JLabel("Pontuação:"), "gapright 10, gaptop 10");
        content.add(scoreSpinner, "growx, gaptop 10");

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(cancelButton);
        buttonRow.add(saveButton);

        content.add(buttonRow, "span 2, gaptop 20, align center");

        cancelButton.addActionListener(e -> dialog.dispose());

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String desc = descriptionArea.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            int score = (Integer) scoreSpinner.getValue();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do hábito é obrigatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Habit habit = new Habit();
            habit.setName(name);
            habit.setDescription(desc);
            habit.setCategory(category);
            habit.setScore(score);

            onSave.accept(habit);
        });

        add(topBar, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }
}
