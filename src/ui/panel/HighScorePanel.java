package ui.panel;

import ui.MainFrame;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class HighScorePanel extends JPanel {

    public HighScorePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertically align the labels
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title
        add(titleLabel);

        // Placeholder for high scores
        add(new JLabel("1. Player1 - 1000"));
        add(new JLabel("2. Player2 - 900"));
        add(new JLabel("3. Player3 - 800"));

        add(Box.createVerticalGlue());

        // a back button to return to the main screen
        JButton backButton = UIUtils.getInstance().createButton("Back to Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        // Add action listener to switch back to the main screen
        backButton.addActionListener(e -> {
            MainFrame.MAIN_FRAME.showScreen("Main");
        });

        add(backButton);

        add(Box.createVerticalStrut(20));

    }
}
