package ui.panel;

import controller.GameController;
import model.ConfigObserver;
import model.MetaConfig;
import ui.MainFrame;
import util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends AbstractPanel implements ConfigObserver {
    private MetaConfig config;
    private JLabel statusLabel;
    private JPanel mainArea;
    private GameController[] gameControllers;
    private int playerNumber;

    public PlayPanel() {
        super("Play");
        config = MetaConfig.getInstance();
        initContentPanel();
        config.addObserver(this);
    }

    public void OpenPanel() {
        config = MetaConfig.getInstance();
        updatePlayerPanels();
    }

    @Override
    protected void initContentPanel() {
        contentPanel.setLayout(new BorderLayout());
        statusLabel = UIUtils.getInstance().createStatusLabel(getStatusText(), new Font("Arial", Font.PLAIN, 14));
        contentPanel.add(statusLabel, BorderLayout.NORTH);
        mainArea = new JPanel();
        contentPanel.add(mainArea, BorderLayout.CENTER);
    }

    private void updatePlayerPanels() {
        mainArea.removeAll();

        int numPlayers = config.isExtendModeEnabled() ? 2 : 1;
        gameControllers = new GameController[numPlayers];

        if (!config.isExtendModeEnabled()) {
            mainArea.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            JPanel player1Panel = createPlayerPanel(1);
            mainArea.add(player1Panel, gbc);
        } else {
            mainArea.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 10);
            JPanel player1Panel = createPlayerPanel(1);
            mainArea.add(player1Panel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 10, 0, 0);
            JPanel player2Panel = createPlayerPanel(2);
            mainArea.add(player2Panel, gbc);
        }

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

    private JPanel createPlayerPanel(int playerNumber) {
        this.playerNumber = playerNumber;
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLUE, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        int width = config.getFieldWidth() * 20 + 215;
        int height = config.getFieldHeight() * 20 + 15;
        Dimension panelSize = new Dimension(width, height);
        playerPanel.setPreferredSize(panelSize);
        playerPanel.setMinimumSize(panelSize);

        JPanel gameInfoPanel = new JPanel();
        gameInfoPanel.setLayout(new BoxLayout(gameInfoPanel, BoxLayout.Y_AXIS));
        gameInfoPanel.setPreferredSize(new Dimension(200, height));
        gameInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel playerInfoLabel = UIUtils.getInstance().createNormalLabel("Game Info (Player " + playerNumber + ")", new Font("Arial", Font.BOLD, 16));

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

        gameInfoPanel.add(playerInfoLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(playerTypeLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(initLevelLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(currentLevelLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(linesErasedLabel);
        gameInfoPanel.add(Box.createVerticalStrut(10));
        gameInfoPanel.add(scoreLabel);
        gameInfoPanel.add(Box.createVerticalStrut(20));

        NextTetromino nextTetrominoPanel = new NextTetromino();
        nextTetrominoPanel.setPreferredSize(new Dimension(100, 100));
        nextTetrominoPanel.setBorder(BorderFactory.createTitledBorder("Next Tetromino"));
        gameInfoPanel.add(nextTetrominoPanel);

        GameController gameController = new GameController(config.getFieldWidth(), config.getFieldHeight(), config.getGameLevel(), playerNumber);
        gameControllers[playerNumber - 1] = gameController;
        gameController.startGame();

        GameBoard gameBoardPanel = gameController.getGameBoardPanel();
        int borderWidth = 2;
        int adjustedW = (config.getFieldWidth() * (20 + borderWidth)) - borderWidth;
        int adjustedH = (config.getFieldHeight() * (20 + borderWidth)) - borderWidth;
        gameBoardPanel.setPreferredSize(new Dimension(adjustedW, adjustedH));
        gameBoardPanel.setMinimumSize(new Dimension(adjustedW, adjustedH));
        gameBoardPanel.setMaximumSize(new Dimension(adjustedW, adjustedH));

        playerPanel.add(gameInfoPanel, BorderLayout.WEST);
        playerPanel.add(gameBoardPanel, BorderLayout.CENTER);

        return playerPanel;
    }

    public void receiveKey(int key) {
        if (gameControllers == null || gameControllers.length == 0) {
            return;
        }

        switch (key) {
            case 0 -> gameControllers[0].moveLeft();  // Player 1 left
            case 1 -> gameControllers[0].moveRight(); // Player 1 right
            case 2 -> gameControllers[0].rotate();    // Player 1 rotate
            case 3 -> gameControllers[0].speedUp();   // Player 1 down
            case 4 -> this.togglePauseGames();
            case 7 -> {
                if (gameControllers.length > 1) gameControllers[1].moveLeft();  // Player 2 left
            }
            case 8 -> {
                if (gameControllers.length > 1) gameControllers[1].moveRight(); // Player 2 right
            }
            case 9 -> {
                if (gameControllers.length > 1) gameControllers[1].rotate();    // Player 2 rotate
            }
            case 10 -> {
                if (gameControllers.length > 1) gameControllers[1].speedUp();  // Player 2 down
            }
        }
    }

    private void togglePauseGames() {
        for (GameController controller : gameControllers) {
            controller.togglePauseGame();
        }
    }

    private void pauseGames() {
        for (GameController controller : gameControllers) {
            controller.pauseGame();
        }
    }

    private void resumeGames() {
        for (GameController controller : gameControllers) {
            controller.resumeGame();
        }
    }

    @Override
    protected void backFunction() {
        // Check if all games are over
        boolean allGamesOver = true;
        for (GameController controller : gameControllers) {
            if (!controller.isGameOver()) {
                allGamesOver = false;
                break;
            }
        }

        if (allGamesOver) {
            // All games are over, go back to main menu directly
            MainFrame.MAIN_FRAME.showScreen("Main");
        } else {
            // Some games are still ongoing

            // Record the paused status of each game before pausing
            boolean[] wasPaused = new boolean[gameControllers.length];
            for (int i = 0; i < gameControllers.length; i++) {
                wasPaused[i] = gameControllers[i].isPaused();
            }

            this.pauseGames();
            // Show confirmation dialog
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to stop the current game(s)?",
                    "Stop Game",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                MainFrame.MAIN_FRAME.showScreen("Main");
            } else {
                // Restore the paused status of each game
                for (int i = 0; i < gameControllers.length; i++) {
                    if (!wasPaused[i]) {
                        gameControllers[i].resumeGame(); // Resume if it was playing before
                    } // Else, do nothing; the game remains paused
                }
            }
        }
    }
}