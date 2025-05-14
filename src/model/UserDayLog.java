package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UserDayLog {
    private LocalDate date;
    private Map<Integer, Boolean> habitsDone;

    public UserDayLog() {
        this.habitsDone = new HashMap<>();
    }

    public UserDayLog(LocalDate date) {
        this.date = date;
        this.habitsDone = new HashMap<>();
    }

    public void setHabitDone(int habitId, boolean done) {
        habitsDone.put(habitId, done);
    }

    public boolean isHabitDone(int habitId) {
        return habitsDone.getOrDefault(habitId, false);
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Integer, Boolean> getHabitsDone() {
        return habitsDone;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHabitsDone(Map<Integer, Boolean> habitsDone) {
        this.habitsDone = habitsDone;
    }
}
