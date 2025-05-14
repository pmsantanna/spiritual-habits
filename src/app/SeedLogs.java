package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.UserDayLog;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SeedLogs {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Lista de hábitos simulados (ids 1 a 5)
        int[] habitIds = {1, 2, 3, 4, 5};

        new File("logs").mkdirs();

        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            UserDayLog log = new UserDayLog(date);
            Map<Integer, Boolean> map = new HashMap<>();

            for (int id : habitIds) {
                // Simula o hábito como feito em dias pares, ou aleatoriamente
                boolean feito = (Math.random() > 0.3);
                map.put(id, feito);
            }

            log.setHabitsDone(map);

            File file = new File("logs/" + date + ".json");
            try {
                mapper.writeValue(file, log);
                System.out.println("Gerado: " + file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
