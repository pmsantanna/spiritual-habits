package model;

public class Habit {
    private int id;
    private String name;
    private String description;
    private String category;
    private boolean active;
    private int score;

    public Habit() {
    }

    public Habit(int id, String name, String description, boolean active, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
