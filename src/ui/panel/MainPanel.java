package ui.panel;

import model.MetaConfig;
import ui.MainFrame;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;

// Lambda Function is used
public class MainPanel extends JPanel {
    private JButton playButton;
    private JButton configButton;
    private JButton highScoreButton;
    private JButton exitButton;

    public MainPanel() {
        MainFrame.MAIN_FRAME.showScreen("Main");
        this.initLayout();
    }

    private void initLayout() {
        // Set the layout as BoxLayout for vertical stacking with alignment
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Center alignment for buttons and labels
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add a title label
        JLabel titleLabel = UIUtils.getInstance().createLabel(
                "Main Menu", new Font("Arial", Font.BOLD, 30), Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the title
        this.add(Box.createVerticalStrut(20));  // Add spacing above title
        this.add(titleLabel);

        // Create and add buttons with some spacing in between
        this.add(Box.createVerticalStrut(30));  // Add space below title
        this.playButton = UIUtils.getInstance().createButton("Play");
        this.add(playButton);
        playButton.addActionListener((e) -> {
            // Fetch the singleton instance of MetaConfig
            MetaConfig config = MetaConfig.getInstance();
            // Show the "Play" screen on MainFrame
            MainFrame.MAIN_FRAME.showScreen("Play", config.getFieldWidth(), config.getFieldHeight(), config.isExtendModeEnabled());
        });

        this.add(Box.createVerticalStrut(20));  // Add space between buttons
        this.configButton = UIUtils.getInstance().createButton("Configuration");
        this.add(configButton);
        // Lambda Function
        configButton.addActionListener(e -> MainFrame.MAIN_FRAME.showScreen("Configuration"));

        this.add(Box.createVerticalStrut(20));
        this.highScoreButton = UIUtils.getInstance().createButton("High Scores");
        this.add(highScoreButton);

        // Action listener for high score button
        this.highScoreButton.addActionListener(e -> {
            // Show the "HighScore" screen on MainFrame
            MainFrame.MAIN_FRAME.showScreen("High Score Panel");
        });

        this.add(Box.createVerticalStrut(20));
        this.exitButton = UIUtils.getInstance().createButton("Exit");
        this.add(exitButton);

        // Set action for exit button
        this.setExitButton();
    }

    private void setExitButton() {
        this.exitButton.addActionListener((e) -> {
            MainFrame.MAIN_FRAME.confirmExit();
        });
    }
}
