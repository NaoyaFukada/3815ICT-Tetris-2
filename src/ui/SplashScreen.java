package ui;

import util.UIUtils; // Importing the UIUtils class
import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        // Create the main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE); // Set the background color for the content panel

        // Set the size of the splash screen
        int width = 600;
        int height = 450;

        // Center the splash screen on the screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        this.setBounds(x, y, width, height);

        // Load the image securely and set its size using UIUtils
        JLabel imageLabel = UIUtils.getInstance().createImageLabel("/resources/images/splash_image.jpg", 600);

        // Create and set up the splash screen text with enhanced styles using UIUtils
        JLabel titleLabel = UIUtils.getInstance().createLabel("Tetris Game", new Font("Arial", Font.BOLD, 40), new Color(0x3B3B98));
        JLabel groupLabel = UIUtils.getInstance().createLabel("Group35: Ryota Ando & Naoya Fukada", new Font("Arial", Font.ITALIC, 14), Color.GRAY);
        JLabel courseLabel = UIUtils.getInstance().createLabel("Course Code: 3815ICT Software Design", new Font("Arial", Font.PLAIN, 14), Color.GRAY);

        // Create a panel to hold the text labels with centered alignment
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        // Center align labels
        groupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add labels and space between them
        textPanel.add(Box.createVerticalGlue()); // Push content to the center
        textPanel.add(groupLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between labels
        textPanel.add(courseLabel);
        textPanel.add(Box.createVerticalGlue()); // Push content to the center

        // Create a title panel with centered alignment and custom background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0xF3F3F3));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding around title

        // Create a main content panel to center everything vertically
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.add(titlePanel, BorderLayout.NORTH);
        mainContentPanel.add(imageLabel, BorderLayout.CENTER);
        mainContentPanel.add(textPanel, BorderLayout.SOUTH);

        // Add the main content panel to the content panel
        contentPanel.add(mainContentPanel, BorderLayout.CENTER);

        // Set the content panel as the content pane of the JWindow
        this.setContentPane(contentPanel);

        // Make the splash screen visible
        this.setVisible(true);

        // Keep the splash screen visible for the specified duration
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Hide and dispose the splash screen after the duration
        this.setVisible(false);
        this.dispose();
    }
}