package ui.panel;

import util.HighScoreManager;
import util.HighScoreManager.ScoreEntry;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScorePanel extends AbstractPanel {

    private HighScoreManager highScoreManager;

    public HighScorePanel() {
        super("High Scores");
        highScoreManager = HighScoreManager.getInstance();
        initContentPanel();
    }

    @Override
    protected void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertically align the labels
        contentPanel.removeAll(); // Clear existing content

        contentPanel.add(Box.createVerticalStrut(20)); // Add some space below the title

        // Load high scores from HighScoreManager
        List<ScoreEntry> highScores = highScoreManager.getHighScores();

        // Get an instance of UIUtils
        UIUtils uiUtils = UIUtils.getInstance();

        // Display high scores
        if (highScores != null && !highScores.isEmpty()) {
            int rank = 1;
            for (ScoreEntry entry : highScores) {
                JLabel scoreLabel = new JLabel(String.format("%d. %s - %d", rank, entry.getName(), entry.getScore()));
                scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(scoreLabel);
                contentPanel.add(Box.createVerticalStrut(10));
                rank++;
            }
        } else {
            JLabel noScoresLabel = new JLabel("No high scores available.");
            noScoresLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                initContentPanel();
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        contentPanel.add(clearButton);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            // Refresh the content when the panel becomes visible
            initContentPanel();
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
}