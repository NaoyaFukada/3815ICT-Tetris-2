package util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// Used Design Pattern:
// Singleton Design Pattern (Lazy Initialization)
// Facade Design Pattern (Provides simple easy to use interface to abstract complexities from other files)
public class UIUtils {
    // Singleton instance
    private static UIUtils instance;

    // Private constructor to prevent instantiation
    private UIUtils() {}

    // Static method to get the singleton instance
    public static UIUtils getInstance() {
        if (instance == null) {
            instance = new UIUtils();
        }
        return instance;
    }

    // Methods

    // Create GridBagConstraints
    public GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();  // Create a new GridBagConstraints object
        gbc.fill = GridBagConstraints.HORIZONTAL;          // Set the fill property to stretch horizontally
        gbc.insets = new Insets(5, 5, 5, 5);               // Set padding (5 pixels on all sides)
        return gbc;                                        // Return the configured GridBagConstraints
    }


    // Method to create labels
    public JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public JLabel createNormalLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    // Method to create a centered label with custom font and size for status (Music and Sound)
    public JLabel createStatusLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER); // Center the text
        label.setFont(font);
        return label;
    }

    public JLabel createValueLabel(int value) {
        return new JLabel(String.valueOf(value));
    }

    public JLabel createValueLabel(String value) {
        return new JLabel(value);
    }

    // Method to create high score labels
    public JLabel createScoreLabel(int rank, String name, int score) {
        String text = rank + ". " + name + " - " + score;
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        return label;
    }

    // Method to create a "No High Scores" label
    public JLabel createNoScoresLabel() {
        JLabel label = new JLabel("No high scores available.");
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Method to create buttons
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 50));  // Adjust size
        button.setMaximumSize(new Dimension(300, 50));  // Ensure consistent size
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the button
        button.setFont(new Font("Arial", Font.PLAIN, 18));  // Set button font
        return button;
    }

    // Method to create slider
    public JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(min, max, value);
        slider.setPreferredSize(new Dimension(300, 40));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    // Method to create checkbox
    public JCheckBox createCheckBox(boolean isSelected) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(isSelected);
        return checkBox;
    }

    // Method to create an image label with secure loading and scaling
    public JLabel createImageLabel(String imagePath, int width) {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            URL imageUrl = getClass().getResource(imagePath); // Using a resource from the classpath
            if (imageUrl != null) {
                ImageIcon splashImage = new ImageIcon(imageUrl);

                // Scale the image
                Image image = splashImage.getImage();
                Image scaledImage = image.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
                ImageIcon scaledSplashImage = new ImageIcon(scaledImage);

                imageLabel.setIcon(scaledSplashImage);
            } else {
                imageLabel.setText("Image not found");
            }
        } catch (Exception e) {
            imageLabel.setText("Failed to load image");
            e.printStackTrace();
        }

        return imageLabel;
    }
}
