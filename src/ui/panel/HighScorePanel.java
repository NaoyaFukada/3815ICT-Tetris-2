package ui.panel;

import util.HighScoreManager;
import util.HighScoreManager.ScoreEntry;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScorePanel extends AbstractPanel {

    public HighScorePanel() {
        super("High Scores");
        initContentPanel();
    }

    @Override
    protected void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertically align the labels

        contentPanel.add(Box.createVerticalStrut(20)); // Add some space below the title

        // Load high scores from HighScoreManager
        HighScoreManager highScoreManager = HighScoreManager.getInstance();
        List<ScoreEntry> highScores = highScoreManager.getHighScores();

        // Get an instance of UIUtils
        UIUtils uiUtils = UIUtils.getInstance();

        // Display high scores
        if (highScores != null && !highScores.isEmpty()) {
            int rank = 1;
            for (ScoreEntry entry : highScores) {
                JLabel scoreLabel = uiUtils.createScoreLabel(rank, entry.getName(), entry.getScore());
                contentPanel.add(scoreLabel);
                contentPanel.add(Box.createVerticalStrut(10));
                rank++;
            }
        } else {
            JLabel noScoresLabel = uiUtils.createNoScoresLabel();
            contentPanel.add(noScoresLabel);
        }

        contentPanel.add(Box.createVerticalStrut(20)); // Add some space below the scores

        // Clear Scores button
        JButton clearButton = uiUtils.createButton("Clear Scores");
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        // Add action listener to clear the high scores
        clearButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all high scores?", "Clear High Scores", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                highScoreManager.clearScores();
                // Update the display
                contentPanel.removeAll();
                initContentPanel();
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        contentPanel.add(clearButton);
    }

}