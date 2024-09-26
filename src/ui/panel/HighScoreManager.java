package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

// this class exists for the singleton design example

public class HighScoreManager {
    private static HighScoreManager instance;
    private static final String FILE_NAME = System.getProperty("user.home") + File.separator + "tetris_highscores.json";
    private static final int MAX_ENTRIES = 10;
    private List<ScoreEntry> highScores;

    // Private constructor to prevent direct instantiation
    private HighScoreManager() {
        loadScores();
    }

    // Public method to get the single instance of HighScoreManager
    public static synchronized HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    private void loadScores() {
        File file = new File(FILE_NAME);
        Gson gson = new Gson();
        Type scoreListType = new TypeToken<ArrayList<ScoreEntry>>() {}.getType();

        if (file.exists()) {
            // Read from the file system
            try (Reader reader = new FileReader(file)) {
                highScores = gson.fromJson(reader, scoreListType);
                if (highScores == null) {
                    highScores = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
                highScores = new ArrayList<>();
            }
        } else {
            // Read from the classpath resource
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/highscores.json")) {
                if (inputStream != null) {
                    try (Reader reader = new InputStreamReader(inputStream)) {
                        highScores = gson.fromJson(reader, scoreListType);
                        if (highScores == null) {
                            highScores = new ArrayList<>();
                        }
                    }
                } else {
                    System.out.println("Could not find the highscores.json file in the classpath.");
                    highScores = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
                highScores = new ArrayList<>();
            }
        }
    }

    public synchronized void saveScores() {
        try {
            File file = new File(FILE_NAME);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (Writer writer = new FileWriter(file)) {
                Gson gson = new Gson();
                gson.toJson(highScores, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<ScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }

    public synchronized void addScore(String name, int score) {
        if (score <= 0) return;
        highScores.add(new ScoreEntry(name, score));
        highScores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        if (highScores.size() > MAX_ENTRIES) {
            highScores = highScores.subList(0, MAX_ENTRIES);
        }
        saveScores();
    }

    public synchronized void clearScores() {
        highScores.clear();
        saveScores();
    }

    public synchronized boolean isHighScore(int score) {
        if (highScores.size() < MAX_ENTRIES) return true;
        return score > highScores.get(highScores.size() - 1).getScore();
    }

    // Inner class for score entries
    public static class ScoreEntry {
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