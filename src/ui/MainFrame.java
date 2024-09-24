package ui;

import ui.panel.ConfigurePanel;
import ui.panel.MainPanel;
import ui.panel.PlayPanel;
import ui.panel.HighScorePanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public static MainFrame MAIN_FRAME;
    private CardLayout cardLayout;
    private PlayPanel playPanel;
    private JPanel containerPanel;

    public MainFrame(String title) throws HeadlessException {
        super(title);  // Call the JFrame constructor with the window title
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Set close operation (To show dialog before closing)
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainFrame.this.confirmExit();  // Handle window closing event
            }
        });
        MAIN_FRAME = this;  // Set the main frame reference
        this.initLayout();  // Initialize layout
        this.initPanels();  // Initialize panels
        this.setVisible(true);  // Make the frame visible
//        this.setupKeyBindings();  // Setup key bindings
    }

    public void confirmExit() {
        // Show confirmation dialog
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the program?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
//            CommFun.closeSound(); // Close any sound resources
            System.exit(0); // Exit the program
        }
    }

//    // Setup key bindings for game controls
//    private void setupKeyBindings() {
//        // InputMap to capture key presses
//        // WHen value was 2, it means "The input map is active when the window containing the component has focus."
//        InputMap inputMap = this.containerPanel.getInputMap(2);  // Focus on the window
//
//        // ActionMap to define actions when keys are pressed
//        ActionMap actionMap = this.containerPanel.getActionMap();
//
//        // Define actions for left, right, up, down, pause, music, and sound controls
//        Action leftAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(0);  // Left key
//            }
//        };
//        Action leftAction2 = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(7);  // Comma key (alternative left key)
//            }
//        };
//        Action rightAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(1);  // Right key
//            }
//        };
//        Action rightAction2 = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(8);  // Period key (alternative right key)
//            }
//        };
//        Action upAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(2);  // Up key
//            }
//        };
//        Action upAction2 = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(9);  // L key (alternative up key)
//            }
//        };
//        Action downAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(3);  // Down key
//            }
//        };
//        Action downAction2 = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(10);  // Space key (alternative down key)
//            }
//        };
//        Action pauseAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(4);  // P key (pause)
//            }
//        };
//        Action musicAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(5);  // M key (toggle music)
//            }
//        };
//        Action soundAction = new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                MainFrame.this.playPanel.receiveKey(6);  // S key (toggle sound)
//            }
//        };
//    }

    private void initLayout() {
        this.setLayout(new BorderLayout());
        this.cardLayout = new CardLayout();
        this.containerPanel = new JPanel(this.cardLayout);
        this.add(this.containerPanel, "Center");
        JPanel footer = new JPanel();
        footer.add(new JLabel("Author: Naoya/Ryota"));
        this.add(footer, "South");
    }

    private void initPanels() {
        MainPanel mainPanel = new MainPanel();
        this.playPanel = new PlayPanel();
//        mainPanel.setPlayListener((e) -> {
//            this.cardLayout.show(this.containerPanel, "Play Panel");
//        });
        ConfigurePanel configurePanel = new ConfigurePanel();
//        mainPanel.setConfigListener((e) -> {
//            this.cardLayout.show(this.containerPanel, "Configure Panel");
//        });
//        HighScorePanel highScorePanel = new HighScorePanel(this.cardLayout, this.containerPanel);
//        mainPanel.setHighScoreListener((e) -> {
//            this.cardLayout.show(this.containerPanel, "High Score Panel");
//        });

        HighScorePanel highScorePanel = new HighScorePanel();

        this.containerPanel.add(mainPanel, "Main");
        this.containerPanel.add(this.playPanel, "Play");
        this.containerPanel.add(configurePanel, "Configuration");
        this.containerPanel.add(highScorePanel, "High Score Panel");
    }

    private void adjustSizeForPanel(String panelName) {
        switch (panelName) {
            case "Main":  // For the main panel
                this.setSize(450, 500);
                break;
            case "Play":
                this.setSize(600, 400);
                break;
            case "Configuration":
                this.setSize(600, 500);
                break;
            default:
                this.setSize(600, 679);
        }
        this.setLocationRelativeTo(null);  // Center the window after resizing
    }

    private void adjustSizeForPanel(int w, int h) {
        this.setSize(w, h);
        this.setLocationRelativeTo(null);  // Center the window after resizing
        playPanel.OpenPanel();
    }

    public void showScreen(String screenName) {
        cardLayout.show(containerPanel, screenName);  // Switch to the desired panel
        adjustSizeForPanel(screenName);
    }

    public void showScreen(String screenName, int get_w, int get_h, boolean twoPlayerMode) {
        int w;
        int h = get_h * 20 + 200;
        if (twoPlayerMode) {
            w = get_w * 40 + 450;
        } else {
            w = get_w * 20 + 220;
        }
        cardLayout.show(containerPanel, screenName);  // Switch to the desired panel
        adjustSizeForPanel(w, h);
    }
}
