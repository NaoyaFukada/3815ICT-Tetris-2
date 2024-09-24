package ui.panel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ui.MainFrame;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class HighScorePanel extends JPanel {

    public HighScorePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertically align the labels
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title
        add(titleLabel);

        // Load high scores from JSON file
        List<HighScoreEntry> highScores = loadHighScores();

        // Display high scores
        if (highScores != null) {
            for (HighScoreEntry entry : highScores) {
                add(new JLabel(entry.getPlayerName() + " - " + entry.getScore()));
            }
        } else {
            add(new JLabel("No high scores available."));
        }

        add(Box.createVerticalGlue()); // Push content to the top for better alignment

        // a back button to return to the main screen
        JButton backButton = UIUtils.getInstance().createButton("Back to Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        // Add action listener to switch back to the main screen
        backButton.addActionListener(e -> MainFrame.MAIN_FRAME.showScreen("Main"));
        add(backButton);
        add(Box.createVerticalStrut(20)); // Add some space below the button
    }

    // Method to load high scores from JSON
    private List<HighScoreEntry> loadHighScores() {
        Gson gson = new Gson();
        try {
            // Load the file from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/highscores.json");

            if (inputStream == null) {
                System.out.println("Could not find the highscores.json file.");
                return null;
            }

            InputStreamReader reader = new InputStreamReader(inputStream);
            Type highScoreListType = new TypeToken<List<HighScoreEntry>>() {}.getType();
            List<HighScoreEntry> highScores = gson.fromJson(reader, highScoreListType);

            // Debugging: Print the list of high scores
            if (highScores != null) {
                System.out.println("High scores loaded: ");
                for (HighScoreEntry entry : highScores) {
                    System.out.println(entry.getPlayerName() + " - " + entry.getScore());
                }
            } else {
                System.out.println("No high scores found in the file.");
            }

            return highScores;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Inner class to represent a high score entry
    static class HighScoreEntry {
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
