package ui.panel;

import ui.panel.AbstractPanel;
import ui.panel.HighScoreManager;

import javax.swing.*;
import java.awt.*;

public class HighScorePanel extends AbstractPanel {

    public HighScorePanel() {
        super("High Scores");  // Call the AbstractPanel constructor for the title
        initContentPanel();    // Initialize specific content for this panel
    }

    @Override
    protected void initContentPanel() {
        // Set layout for the content panel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Get the high scores from the singleton
        HighScoreManager highScoreManager = HighScoreManager.getInstance();
        if (highScoreManager.getHighScores() != null) {
            for (HighScoreManager.HighScoreEntry entry : highScoreManager.getHighScores()) {
                contentPanel.add(new JLabel(entry.getPlayerName() + " - " + entry.getScore()));
            }
        } else {
            contentPanel.add(new JLabel("No high scores available."));
        }

        // Add any additional custom components or functionality if needed
    }
}

