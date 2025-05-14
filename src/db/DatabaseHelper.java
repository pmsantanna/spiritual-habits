package db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Habit;
import model.UserDayLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class DatabaseHelper {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String logDir = "logs";

    public void initDatabase() {
        new File(logDir).mkdirs();
    }

    public DatabaseHelper() {
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Habit> loadHabits() {
        try {
            File file = new File("habits.json");
            if (!file.exists()) return new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            Habit[] array = mapper.readValue(file, Habit[].class);
            return new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public UserDayLog loadLogForDate(String dateStr) {
        try {
            File file = new File(logDir + "/" + dateStr + ".json");
            if (!file.exists()) {
                return new UserDayLog(LocalDate.parse(dateStr));
            }
            return mapper.readValue(file, UserDayLog.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new UserDayLog(LocalDate.parse(dateStr));
        }
    }

    public void saveUserLog(UserDayLog log) {
        try {
            File file = new File(logDir + "/" + log.getDate().toString() + ".json");
            mapper.writeValue(file, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveHabits(List<Habit> habits) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            File file = new File("habits.json");
            mapper.writeValue(file, habits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
