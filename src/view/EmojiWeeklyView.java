package view;

import components.ZenCardPanel;
import components.ZenFrame;
import db.DatabaseHelper;
import model.Habit;
import model.UserDayLog;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EmojiWeeklyView extends ZenFrame {

    public EmojiWeeklyView() {
        super(createMainPanel());
        setSize(900, 500);
        setLocationRelativeTo(null);
    }

    private static JPanel createMainPanel() {
        DatabaseHelper db = new DatabaseHelper();

        ZenCardPanel mainPanel = new ZenCardPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel weekPanel = new JPanel(new MigLayout("wrap 7, gapx 40, insets 20", "[center]"));
        weekPanel.setOpaque(false);

        List<Habit> habits = db.loadHabits();
        int totalWeekScore = 0;

        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            UserDayLog log = db.loadLogForDate(date.toString());

            int dayScore = 0;
            for (Habit h : habits) {
                if (log.isHabitDone(h.getId())) {
                    dayScore += h.getScore();
                }
            }

            totalWeekScore += dayScore;

            String emoji = getEmojiByScore(dayScore);
            String day = date.getDayOfWeek().toString().substring(0, 3).toUpperCase();

            JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
            emojiLabel.setFont(new Font("SansSerif", Font.PLAIN, 48));
            emojiLabel.setForeground(getColorByEmoji(emoji));
            emojiLabel.setOpaque(false);

            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            dayLabel.setForeground(new Color(80, 80, 80));
            dayLabel.setOpaque(false);

            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setOpaque(false);
            dayPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
            dayPanel.add(emojiLabel, BorderLayout.CENTER);
            dayPanel.add(dayLabel, BorderLayout.SOUTH);

            weekPanel.add(dayPanel);
        }

        int avgScore = totalWeekScore / 7;
        String weeklyEmoji = getEmojiByScore(avgScore);

        JLabel summaryLabel = new JLabel("Semana: " + weeklyEmoji, SwingConstants.CENTER);
        summaryLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        summaryLabel.setForeground(getColorByEmoji(weeklyEmoji));
        summaryLabel.setOpaque(false);

        JLabel messageLabel = new JLabel(getMotivationalPhrase(avgScore), SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
        messageLabel.setForeground(new Color(70, 70, 70));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        messageLabel.setOpaque(false);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.add(summaryLabel, BorderLayout.NORTH);
        footerPanel.add(messageLabel, BorderLayout.SOUTH);

        JPanel contentPanel = new JPanel(new MigLayout("fill, wrap 1", "[center]", "push[]10[]10push"));
        contentPanel.setOpaque(false);
        contentPanel.add(weekPanel);
        contentPanel.add(footerPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private static String getEmojiByScore(int score) {
        if (score >= 5) return "😄";
        if (score >= 2) return "🙂";
        if (score >= 0) return "😐";
        if (score >= -2) return "🙁";
        return "😞";
    }

    private static Color getColorByEmoji(String emoji) {
        return switch (emoji) {
            case "😄" -> Color.decode("#ece9df");
            case "🙂", "😐" -> new Color(70, 70, 70);     // cinza escuro
            case "🙁", "😞" -> new Color(160, 160, 160);  // cinza claro
            default -> Color.BLACK;
        };
    }

    private static String getMotivationalPhrase(int avgScore) {
        if (avgScore >= 5) return "🌟 Você teve uma semana iluminada! Continue assim.";
        if (avgScore >= 2) return "✨ Bons passos foram dados. Mantenha sua prática.";
        if (avgScore >= 0) return "🌱 Você está evoluindo. Cada escolha conta.";
        if (avgScore >= -2) return "💡 Perceber já é um passo. A semana nova é uma chance.";
        return "🕊️ Cuide de si com carinho. O recomeço começa agora.";
    }
}