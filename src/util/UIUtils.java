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

    // Method to create labels securely and consistently
    public JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        return label;
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
