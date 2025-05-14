package controller;

import db.DatabaseHelper;
import model.Habit;
import model.UserDayLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitController {
    private final DatabaseHelper db;
    private List<Habit> habits;
    private UserDayLog todayLog;

    public HabitController() {
        db = new DatabaseHelper();
        db.initDatabase();
        habits = db.loadHabits();
        todayLog = db.loadLogForDate(LocalDate.now().toString());
    }

    public void addHabit(Habit habit) {
        if (!(habits instanceof ArrayList)) {
            habits = new ArrayList<>(habits);
        }

        int nextId = habits.stream().mapToInt(Habit::getId).max().orElse(0) + 1;
        habit.setId(nextId);
        habits.add(habit);

        db.saveHabits(habits);
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public boolean isHabitDone(Habit habit) {
        return todayLog.isHabitDone(habit.getId());
    }

    public void toggleHabit(Habit habit) {
        boolean current = todayLog.isHabitDone(habit.getId());
        todayLog.setHabitDone(habit.getId(), !current);
        db.saveUserLog(todayLog);
    }

    public void reloadFromDisk() {
        habits = db.loadHabits();
        todayLog = db.loadLogForDate(LocalDate.now().toString());
    }

    public void removeHabit(Habit habit) {
        if (habits.removeIf(h -> h.getId() == habit.getId())) {
            db.saveHabits(habits);
        }
    }
}
