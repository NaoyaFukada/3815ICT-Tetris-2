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
    private GameBoard gameBoardPanel;
    private int playerNumber;
    private Game game;
    private boolean running = false;
    private TetrominoSequence tetrominoSequence;
    private NextTetromino nextTetrominoPanel;
    private GameOverListener gameOverListener;
    private PlayerType playerType;
    private TetrisAI ai;
    private Move bestMove;
    private TetrisShapeInstance previousShapeInstance;

    public GameController(int width, int height, int initialLevel, int playerNumber,
                          TetrominoSequence tetrominoSequence, NextTetromino nextTetrominoPanel,
                          GameOverListener gameOverListener, PlayerType playerType) {
        this.playerNumber = playerNumber;
        this.board = new Board(width, height);
        this.tetrominoSequence = tetrominoSequence;
        this.nextTetrominoPanel = nextTetrominoPanel;
        this.gameOverListener = gameOverListener;
        this.playerType = playerType;

        if (playerType == PlayerType.AI) {
            ai = new TetrisAI();
        }

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
    }

    @Override
    public void run() {
        while (running) {
            if (!board.isGameOver() && !game.isPaused()) {
                if (playerType == PlayerType.AI) {
                    if (currentShape != previousShapeInstance) {
                        previousShapeInstance = currentShape;
                        bestMove = ai.findBestMove(board, currentShape.getTetrisShape());
                    }
                    aiMove();
                } else {
                    currentShape.update();
                    SwingUtilities.invokeLater(() -> gameBoardPanel.repaint());
                }
            } else if (board.isGameOver()) {
                running = false;
                handleGameOver();
            }
            try {
                Thread.sleep(50); // Adjusted sleep time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void aiMove() {
        if (bestMove == null) {
            bestMove = ai.findBestMove(board.copy(), currentShape.getTetrisShape());
            if (bestMove == null) {
                // No valid move found
                System.out.println("AI could not find a valid move.");
                // Handle as needed, perhaps end the game
                return;
            }
        }

        if (currentShape.getRotationState() != bestMove.rotation) {
            currentShape.rotate();
        } else if (currentShape.getX() < bestMove.column) {
            currentShape.moveRight();
        } else if (currentShape.getX() > bestMove.column) {
            currentShape.moveLeft();
        } else {
            // At desired position and rotation
            currentShape.speedUp();
            // Reset bestMove for the next piece
            bestMove = null;
        }

        // Update the shape
        currentShape.update();
        SwingUtilities.invokeLater(() -> gameBoardPanel.repaint());
    }


    private void handleGameOver() {
        // Play game over sound if sound is enabled
        if (MetaConfig.getInstance().isSoundEnabled()) {
            AudioManager.playSoundEffect("game-finish");
        }

        SwingUtilities.invokeLater(() -> {
            if (gameOverListener != null) {
                gameOverListener.onGameOver(this);
            }

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
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void pauseGame() {
        if (!game.isPaused()) {
            game.pauseGame();
            gameBoardPanel.repaint();
        }
    }

    public void resumeGame() {
        if (game.isPaused()) {
            game.resumeGame();
            gameBoardPanel.repaint();
        }
    }

    public void togglePauseGame() {
        game.togglePauseGame();
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

    // Interface for game over listener
    public interface GameOverListener {
        void onGameOver(GameController controller);
    }
}