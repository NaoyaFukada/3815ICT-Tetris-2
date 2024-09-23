import ui.MainFrame;
import ui.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplash();
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI(){
        new MainFrame("Tetris");
    }
}
