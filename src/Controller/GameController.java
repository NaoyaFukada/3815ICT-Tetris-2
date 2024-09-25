package controller;

import model.Board;
import model.Game;
import model.TetrisShapeInstance;
import ui.panel.GameBoard;

import javax.swing.*;

public class GameController {
    private Board board;
    private TetrisShapeInstance currentShape;
    private Timer timer;
    private GameBoard gameBoardPanel;
    private int playerNumber;
    private Game game;

    public GameController(int width, int height, int initialLevel, int playerNumber) {
        this.playerNumber = playerNumber;
        this.board = new Board(width, height);

        // Initialize the game model
        this.game = new Game(board, initialLevel);

        // Retrieve the current shape from the game
        currentShape = game.getCurrentShapeInstance();

        // Create the game board UI component
        gameBoardPanel = new GameBoard(board, currentShape, game);

        // Initialize the game loop timer
        int delay = 20; // Adjust the delay as needed (milliseconds)
        timer = new Timer(delay, e -> {
            if (!board.isGameOver() && !game.isPaused()) {
                currentShape.update();
                gameBoardPanel.repaint();
            } else if (board.isGameOver()) {
                timer.stop();
                // Optionally, show a game over message
                JOptionPane.showMessageDialog(gameBoardPanel, "Game Over for Player " + playerNumber + "!");
            }
        });
    }

    public void startGame() {
        timer.start();
    }

    public void pauseGame() {
        if (!game.isPaused()) {
            game.pauseGame();  // Set the game state to paused
            timer.stop();      // Stop the game timer
            gameBoardPanel.repaint(); // Repaint to show pause message
        }
    }

    public void resumeGame() {
        if (game.isPaused()) {
            game.resumeGame();  // Set the game state to playing
            timer.start();      // Start the game timer
            gameBoardPanel.repaint(); // Repaint to remove pause message
        }
    }

    public void togglePauseGame() {
        game.togglePauseGame();  // Toggle the game state
        if (game.isPaused()) {
            timer.stop();  // Stop the game if paused
        } else {
            timer.start();  // Resume the game if not paused
        }
        gameBoardPanel.repaint(); // Repaint to update pause message
    }

    public boolean isPaused() {
        return game.isPaused();
    }

    public void moveLeft() {
        currentShape.moveLeft();
    }

    public void moveRight() {
        currentShape.moveRight();
    }

    public void rotate() {
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
}