//package ui.panel;
//
//import ui.MainFrame;
//import util.UIUtils;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class HighScorePanel extends JPanel {
//
//    public HighScorePanel() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertically align the labels
//        JLabel titleLabel = new JLabel("High Scores");
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title
//        add(titleLabel);
//
//        // Get the high scores from the singleton
//        HighScoreManager highScoreManager = HighScoreManager.getInstance();
//        if (highScoreManager.getHighScores() != null) {
//            for (HighScoreManager.HighScoreEntry entry : highScoreManager.getHighScores()) {
//                add(new JLabel(entry.getPlayerName() + " - " + entry.getScore()));
//            }
//        } else {
//            add(new JLabel("No high scores available."));
//        }
//
//        add(Box.createVerticalGlue()); // Push content to the top for better alignment
//
//        // a back button to return to the main screen
//        JButton backButton = UIUtils.getInstance().createButton("Back to Main Menu");
//        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
//
//        // Add action listener to switch back to the main screen
//        backButton.addActionListener(e -> MainFrame.MAIN_FRAME.showScreen("Main"));
//        add(backButton);
//        add(Box.createVerticalStrut(20)); // Add some space below the button
//    }
//}

package ui.panel;

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

