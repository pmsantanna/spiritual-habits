package view;

import db.DatabaseHelper;
import model.Habit;
import model.UserDayLog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklySummaryView extends JFrame {

    private final DatabaseHelper db = new DatabaseHelper();

    public WeeklySummaryView() {
        setTitle("Resumo Semanal");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ChartPanel chartPanel = new ChartPanel(createChart());
        panel.add(chartPanel, BorderLayout.CENTER);

        setContentPane(panel);
    }

    private JFreeChart createChart() {
        List<Habit> habits = db.loadHabits();
        Map<Integer, Integer> habitCount = new HashMap<>();

        for (Habit h : habits) {
            habitCount.put(h.getId(), 0);
        }

        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            UserDayLog log = db.loadLogForDate(date.toString());

            for (Map.Entry<Integer, Boolean> entry : log.getHabitsDone().entrySet()) {
                if (entry.getValue()) {
                    habitCount.put(entry.getKey(), habitCount.getOrDefault(entry.getKey(), 0) + 1);
                }
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Habit h : habits) {
            int count = habitCount.getOrDefault(h.getId(), 0);
            dataset.addValue(count, "Feitos", h.getName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "", "", "", dataset
        );

        var plot = chart.getCategoryPlot();

        chart.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.removeLegend();

        Font font = new Font("SansSerif", Font.PLAIN, 13);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        plot.getDomainAxis().setLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);

        var renderer = new org.jfree.chart.renderer.category.BarRenderer();
        renderer.setSeriesPaint(0, new Color(100, 100, 100));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(false);
        plot.setRenderer(renderer);

        return chart;
    }
}