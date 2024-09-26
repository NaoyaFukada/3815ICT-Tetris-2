package util;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreManager {
    private static HighScoreManager instance;
    private static final String FILE_PATH = "resources/data/highscores.json";
    private static final int MAX_ENTRIES = 10;
    private List<ScoreEntry> highScores;

    // Private constructor to enforce Singleton pattern
    private HighScoreManager() {
        loadScores();
    }

    // Public method to get the Singleton instance
    public static synchronized HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    // Loads high scores from user file if exists; otherwise, from default resource
    private void loadScores() {
        Gson gson = new Gson();
        Type scoreListType = new TypeToken<ArrayList<ScoreEntry>>() {}.getType();

        File userFile = new File(FILE_PATH);
        // Load from user file
        try (Reader reader = new FileReader(userFile)) {
            highScores = gson.fromJson(reader, scoreListType);
            if (highScores == null) {
                highScores = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            highScores = new ArrayList<>();
        }
    }

    // Saves high scores to the user file
    public synchronized void saveScores() {
        try {
            File userFile = new File(FILE_PATH);
            File parentDir = userFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (Writer writer = new FileWriter(userFile)) {
                Gson gson = new Gson();
                gson.toJson(highScores, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Returns a copy of high scores
    public synchronized List<ScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }

    // Adds a new high score if it qualifies
    public synchronized void addScore(String name, int score) {
        if (score <= 0) return; // Only positive scores
        highScores.add(new ScoreEntry(name, score));
        highScores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        if (highScores.size() > MAX_ENTRIES) {
            highScores = highScores.subList(0, MAX_ENTRIES);
        }
        saveScores();
    }

    // Clears all high scores
    public synchronized void clearScores() {
        highScores.clear();
        saveScores();
    }

    // Checks if a score qualifies as a high score
    public synchronized boolean isHighScore(int score) {
        if (score <= 0) return false;
        if (highScores.size() < MAX_ENTRIES) return true;
        return score > highScores.get(highScores.size() - 1).getScore();
    }

    // Inner class representing a high score entry
    public static class ScoreEntry {
        @SerializedName("playerName")
        private String name;
        private int score;

        public ScoreEntry() {}

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() { return name; }
        public int getScore() { return score; }
    }
}
