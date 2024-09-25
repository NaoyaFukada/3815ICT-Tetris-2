package ui.panel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class HighScoreManager {
    private static HighScoreManager instance;
    private List<HighScoreEntry> highScores;

    // Private constructor to prevent instantiation
    private HighScoreManager() {
        loadHighScores();  // Load the scores once when the instance is created
    }

    // Static method to get the single instance of HighScoreManager
    public static HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    // Method to load high scores from the JSON file
    private void loadHighScores() {
        Gson gson = new Gson();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/highscores.json");

            if (inputStream == null) {
                System.out.println("Could not find the highscores.json file.");
                return;
            }

            InputStreamReader reader = new InputStreamReader(inputStream);
            Type highScoreListType = new TypeToken<List<HighScoreEntry>>() {}.getType();
            highScores = gson.fromJson(reader, highScoreListType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Public method to get the list of high scores
    public List<HighScoreEntry> getHighScores() {
        return highScores;
    }

    // Inner class to represent a high score entry
    public static class HighScoreEntry {
        private String playerName;
        private int score;

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }
    }
}
