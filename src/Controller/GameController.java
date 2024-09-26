package Controller;

import model.*;
import ui.panel.GameBoard;
import ui.panel.NextTetromino;
import util.AudioManager;
import util.HighScoreManager;

import javax.swing.*;

public class GameController implements Runnable, NextShapeListener {
    private Board board;
    private TetrisShapeInstance currentShape;
    private Timer timer;
    private GameBoard gameBoardPanel;
    private int playerNumber;
    private Game game;
    private boolean running = false;
    private TetrominoSequence tetrominoSequence;
    private NextTetromino nextTetrominoPanel;

    public GameController(int width, int height, int initialLevel, int playerNumber, TetrominoSequence tetrominoSequence, NextTetromino nextTetrominoPanel) {
        this.playerNumber = playerNumber;
        this.board = new Board(width, height);
        this.tetrominoSequence = tetrominoSequence;
        this.nextTetrominoPanel = nextTetrominoPanel;

        // Initialize the game model
        this.game = new Game(board, initialLevel, tetrominoSequence);

        // Set this controller as the listener for next shape changes
        this.game.setNextShapeListener(this);

        // Retrieve the current shape from the game
        currentShape = game.getCurrentShapeInstance();

        // Create the game board UI component
        gameBoardPanel = new GameBoard(board, currentShape, game);

        // Update the NextTetromino panel with the initial next shape
        nextTetrominoPanel.setNextShape(game.peekNextShape());

        // Initialize the game loop timer for Player 1
        if (playerNumber == 1) {
            int delay = 20; // Adjust the delay as needed (milliseconds)
            timer = new Timer(delay, e -> gameLoop());
        }
    }

    private void gameLoop() {
        if (!board.isGameOver() && !game.isPaused()) {
            currentShape.update();
            gameBoardPanel.repaint();
        } else if (board.isGameOver()) {
            if (timer != null) {
                timer.stop();
            }
            handleGameOver();
        }
    }

    @Override
    public void run() {
        while (running) {
            if (!board.isGameOver() && !game.isPaused()) {
                currentShape.update();
                SwingUtilities.invokeLater(() -> gameBoardPanel.repaint());
            } else if (board.isGameOver()) {
                running = false;
                handleGameOver();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleGameOver() {
        // Play game over sound if sound is enabled
        if (MetaConfig.getInstance().isSoundEnabled()) {
            AudioManager.playSoundEffect("game-finish");
        }

        SwingUtilities.invokeLater(() -> {
            int finalScore = game.getScore();
            JOptionPane.showMessageDialog(gameBoardPanel, "Game Over for Player " + playerNumber + "!\nScore: " + finalScore);

            // Use the singleton instance of HighScoreManager
            HighScoreManager highScoreManager = HighScoreManager.getInstance();
            if (highScoreManager.isHighScore(finalScore)) {
                String name = JOptionPane.showInputDialog(gameBoardPanel, "New High Score! Enter your name:");
                if (name != null && !name.trim().isEmpty()) {
                    highScoreManager.addScore(name.trim(), finalScore);
                }
            }
        });
    }

    public void startGame() {
        if (playerNumber == 2) {
            running = true;
            Thread thread = new Thread(this);
            thread.start();
        } else {
            timer.start();
        }
    }

    public void pauseGame() {
        if (!game.isPaused()) {
            game.pauseGame();
            if (timer != null) {
                timer.stop();
            }
            gameBoardPanel.repaint();
        }
    }

    public void resumeGame() {
        if (game.isPaused()) {
            game.resumeGame();
            if (timer != null) {
                timer.start();
            }
            gameBoardPanel.repaint();
        }
    }

    public void togglePauseGame() {
        game.togglePauseGame();
        if (game.isPaused()) {
            if (timer != null) {
                timer.stop();
            }
        } else {
            if (timer != null) {
                timer.start();
            }
        }
        gameBoardPanel.repaint();
    }

    public boolean isPaused() {
        return game.isPaused();
    }

    public void moveLeft() {
        if (MetaConfig.getInstance().isSoundEnabled()) {
            AudioManager.playSoundEffect("move-turn");
        }
        currentShape.moveLeft();
    }

    public void moveRight() {
        if (MetaConfig.getInstance().isSoundEnabled()) {
            AudioManager.playSoundEffect("move-turn");
        }
        currentShape.moveRight();
    }

    public void rotate() {
        if (MetaConfig.getInstance().isSoundEnabled()) {
            AudioManager.playSoundEffect("move-turn");
        }
        currentShape.rotate();
    }

    public void speedUp() {
        currentShape.speedUp();
    }

    public GameBoard getGameBoardPanel() {
        return gameBoardPanel;
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public int getScore() {
        return game.getScore();
    }

    public int getCurrentLevel() {
        return game.getCurrentLevel();
    }

    public int getLinesErased() {
        return game.getLinesErased();
    }

    @Override
    public void onNextShapeChanged(TetrisShape nextShape) {
        // Update the NextTetromino panel
        SwingUtilities.invokeLater(() -> nextTetrominoPanel.setNextShape(nextShape));
    }
}