package util;

public class ScoreEntry {
    private String name;
    private int score;

    public ScoreEntry() {}

    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getScore() { return score; }
}