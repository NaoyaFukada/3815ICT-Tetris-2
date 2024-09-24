package ui.panel;

import model.ConfigObserver;
import model.MetaConfig;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends AbstractPanel implements ConfigObserver {
    // MetaConfig instance for managing settings
    private MetaConfig config;
    // Labels to display the status of music and sound
    private JLabel statusLabel;

    // Main area for player info and the game board
    private JPanel mainArea;

    // Constructor for the PlayPanel
    public PlayPanel() {
        // Calling the parent class constructor and passing the title "Play"
        super("Play");

        // Initialize MetaConfig instance
        config = MetaConfig.getInstance();
        initContentPanel();
        config.addObserver(this);
        System.out.println(config.isMusicEnabled());
    }

    // This method will be called every time the Play panel is shown
    public void OpenPanel() {
        // Example action when the panel is opened
        config = MetaConfig.getInstance();
        updatePlayerPanels();
        System.out.println("PlayPanel opened, music enabled: " + config.isMusicEnabled());
    }

    @Override
    protected void initContentPanel() {
        // Sets the layout of the content panel (the panel holding everything) to BorderLayout
        contentPanel.setLayout(new BorderLayout());

        // Top panel for title and status (Music and Sound)
        statusLabel = UIUtils.getInstance().createStatusLabel(getStatusText(), new Font("Arial", Font.PLAIN, 14));
        // Add the status label to the top part of the content panel
        contentPanel.add(statusLabel, BorderLayout.NORTH);

        // Initialize the main area for player info and the game board
        mainArea = new JPanel();
        contentPanel.add(mainArea, BorderLayout.CENTER);
    }

    // This method updates the player panels (called from OpenPanel)
    private void updatePlayerPanels() {
        mainArea.removeAll();  // Clear any existing player panels from the main area

        // single player mode
        if (!config.isExtendModeEnabled()) {
            mainArea.setLayout(new GridBagLayout());  // Centering layout
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;  // Center the panel horizontally
            gbc.gridy = 0;  // Center the panel vertically
            gbc.anchor = GridBagConstraints.CENTER;  // Ensure it's centered
            JPanel player1Panel = createPlayerPanel(1);
            mainArea.add(player1Panel, gbc);  // Add player1Panel to the center
        } else {
            // two player mode
            mainArea.setLayout(new GridBagLayout());  // Use GridBagLayout instead of GridLayout for 2 players
            GridBagConstraints gbc = new GridBagConstraints();

            // Create player 1 panel and add it to the left side
            gbc.gridx = 0;  // Left column
            gbc.gridy = 0;  // First row
            gbc.anchor = GridBagConstraints.CENTER;  // Ensure it respects the preferred size
            gbc.insets = new Insets(0, 0, 0, 10);  // Add 10px gap on the right side of player 1 panel
            JPanel player1Panel = createPlayerPanel(1);
            mainArea.add(player1Panel, gbc);  // Add player1Panel for player 1

            // Create player 2 panel and add it to the right side
            gbc.gridx = 1;  // Right column
            gbc.gridy = 0;  // First row
            gbc.anchor = GridBagConstraints.CENTER;  // Ensure it respects the preferred size
            gbc.insets = new Insets(0, 10, 0, 0);  // Add 10px gap on the left side of player 2 panel
            JPanel player2Panel = createPlayerPanel(2);
            mainArea.add(player2Panel, gbc);  // Add player2Panel for player 2
        }

        // Revalidate and repaint the main area to reflect the updated components
        mainArea.revalidate();
        mainArea.repaint();
    }



    @Override
    public void update() {
        statusLabel.setText(getStatusText());
    }

    private String getStatusText() {
        return "Music: " + (config.isMusicEnabled() ? "ON" : "OFF") + " | Sound: " + (config.isSoundEnabled() ? "ON" : "OFF");
    }

    // Method to create a player panel (game info and game board) for a specific player
    private JPanel createPlayerPanel(int playerNumber) {
        // Create the panel that will hold both the game info and game board
        JPanel playerPanel = new JPanel();
        // Set BorderLayout for the player panel (info on the left, game board on the right)
        playerPanel.setLayout(new BorderLayout());
        // Set a blue border around the player panel with 2-pixel thickness
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLUE, 2),  // Outer blue border
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Inner padding (top, left, bottom, right)
        ));

        // Calculate width and height based on the configuration
        int width = config.getFieldWidth() * 20 + 215;
        int height = config.getFieldHeight() * 20 + 15;
        Dimension panelSize = new Dimension(width, height);
        playerPanel.setPreferredSize(panelSize);
        playerPanel.setMinimumSize(panelSize);

        // Create the game info panel (contains player info, score, etc.)
        JPanel gameInfoPanel = new JPanel();
        // Use BoxLayout to arrange components vertically
        gameInfoPanel.setLayout(new BoxLayout(gameInfoPanel, BoxLayout.Y_AXIS));
        // Set a fixed size for the game info panel
        gameInfoPanel.setPreferredSize(new Dimension(200, height));
        gameInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Add padding to the right

        // Add various labels and spacing between them to the game info panel
        JLabel playerInfoLabel = UIUtils.getInstance().createNormalLabel("Game Info (Player " + playerNumber + ")", new Font("Arial", Font.BOLD, 16));

        // Conditionally set player type based on player number
        String playerType;
        if (playerNumber == 1) {
            playerType = "Player Type: " + config.getPlayerOneType();
        } else if (playerNumber == 2) {
            playerType = "Player Type: " + config.getPlayerTwoType();
        } else {
            playerType = "Player Type: UNKNOWN";
        }

        JLabel playerTypeLabel = new JLabel(playerType);
        JLabel initLevelLabel = new JLabel("Initial Level: " + config.getGameLevel());
        JLabel currentLevelLabel = new JLabel("Current Level: 1");
        JLabel linesErasedLabel = new JLabel("Lines Erased: 0");
        JLabel scoreLabel = new JLabel("Score: 0");

        // Add the labels to the game info panel with vertical spacing
        gameInfoPanel.add(playerInfoLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10)); // Add space between labels
        gameInfoPanel.add(playerTypeLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(initLevelLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(currentLevelLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(linesErasedLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(scoreLabel);
        gameInfoPanel.add(Box.createVerticalStrut(20)); // Extra space before the next section

        // Next Tetromino Panel (shows the next block in the game)
        NextTetromino nextTetrominoPanel = new NextTetromino();
        nextTetrominoPanel.setPreferredSize(new Dimension(100, 100));
        nextTetrominoPanel.setBorder(BorderFactory.createTitledBorder("Next Tetromino"));
        // Add the next Tetromino panel to the game info panel
        gameInfoPanel.add(nextTetrominoPanel);

        // Game board panel (where the Tetris game is displayed)
        GameBoard gameBoardPanel = new GameBoard(config.getFieldWidth(), config.getFieldHeight(), config.getGameLevel());
        int borderWidth = 2; // Assuming 1-pixel borders between cells
        int adjustedW = (config.getFieldWidth() * (20 + borderWidth)) - borderWidth; // Total width
        int adjustedH = (config.getFieldHeight() * (20 + borderWidth)) - borderWidth; // Total height
        gameBoardPanel.setPreferredSize(new Dimension(adjustedW, adjustedH));
        gameBoardPanel.setMinimumSize(new Dimension(adjustedW, adjustedH));
        gameBoardPanel.setMaximumSize(new Dimension(adjustedW, adjustedH));
//        gameBoardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Add the game info panel to the left and the game board to the center
        playerPanel.add(gameInfoPanel, BorderLayout.WEST);
        playerPanel.add(gameBoardPanel, BorderLayout.CENTER);

        // Return the complete player panel with both game info and game board
        return playerPanel;
    }


}
