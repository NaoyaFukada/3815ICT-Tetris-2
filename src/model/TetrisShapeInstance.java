package model;

import ui.panel.GameBoard;

import java.awt.*;

public class TetrisShapeInstance {
    private int x;             // Grid X position
    private int y;             // Grid Y position
    private int[][] coords;    // Shape coordinates
    private Color color;       // Shape color
    private Board board;       // Reference to the game board
    private Game game;         // Reference to the game

    // Variables for smooth movement
    private double progress = 0;            // Progress towards next row
    private double progressIncrement;       // Increment per update
    private final double progressThreshold; // Threshold to move down a row

    // Collision handling
    private boolean collision = false;
    private boolean isWaiting = false;
    private long timeOfLastCollision = 0;   // Time when collision was detected
    private final int collisionDelay = 100; // Delay in milliseconds before settling

    // Added variables
    private int rotationState = 0;

    // **New field to store the TetrisShape**
    private TetrisShape tetrisShape;

    public TetrisShapeInstance(Board board, int initialLevel, Game game) {
        this.board = board;
        this.game = game;

        // Adjust these values to control speed and smoothness
        this.progressThreshold = 100;
        updateSpeed();

        spawnNewShape();
    }

    private void updateSpeed() {
        this.progressIncrement = 5 + game.getCurrentLevel() * 1.5;
    }

    public void spawnNewShape() {
        // Get the next shape from the game's tetromino sequence
        TetrisShape shape = game.getNextShape();
        this.tetrisShape = shape; // **Store the reference**
        this.coords = shape.getShape();
        this.color = shape.getColor();

        // Start position (top-middle of the board)
        this.x = board.getWidth() / 2 - coords[0].length / 2;
        this.y = -3; // Start at the top of the board
        this.progress = 0;
        this.collision = false;
        this.isWaiting = false;

        // Reset rotation state
        rotationState = 0;
    }

    public boolean update() {
        updateSpeed();

        if (board.isGameOver()) {
            return false;
        }

        if (collision) {
            if (!isWaiting) {
                isWaiting = true;
                timeOfLastCollision = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - timeOfLastCollision >= collisionDelay) {
                settle();
                isWaiting = false;
                collision = false;
                return false; // Shape has settled
            } else {
                // Still waiting, allow player to move or rotate the shape
                return true;
            }
        }

        progress += progressIncrement;
        while (progress >= progressThreshold) {
            progress -= progressThreshold;
            if (!collidesAt(x, y + 1)) {
                y += 1;
            } else {
                collision = true;
                return true;
            }
        }

        // Perform immediate collision check
        if (collidesAt(x, y + 1)) {
            collision = true;
        }

        return true;
    }

    public TetrisShape getTetrisShape() {
        return tetrisShape;
    }

    public void moveLeft() {
        if (!collidesAt(x - 1, y)) {
            x--;
            if (!collidesAt(x, y + 1)) {
                collision = false;
                isWaiting = false;
            }
        }
    }

    public void moveRight() {
        if (!collidesAt(x + 1, y)) {
            x++;
            if (!collidesAt(x, y + 1)) {
                collision = false;
                isWaiting = false;
            }
        }
    }

    public void rotate() {
        int[][] rotatedShape = rotateMatrix(coords);
        if (!collidesAt(x, y, rotatedShape)) {
            coords = rotatedShape;
            rotationState = (rotationState + 1) % 4;
            if (!collidesAt(x, y + 1)) {
                collision = false;
                isWaiting = false;
            }
        }
    }

    private int[][] rotateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = matrix[r][c];
            }
        }
        return rotated;
    }

    private boolean collidesAt(int newX, int newY) {
        return collidesAt(newX, newY, coords);
    }

    private boolean collidesAt(int newX, int newY, int[][] shape) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) {
                    int boardX = newX + c;
                    int boardY = newY + r;

                    // Check horizontal boundaries
                    if (boardX < 0 || boardX >= board.getWidth()) {
                        return true; // Collision with left or right wall
                    }

                    // Check vertical boundaries
                    if (boardY >= board.getHeight()) {
                        return true; // Collision with bottom
                    }

                    // Check if the cell is occupied
                    if (boardY >= 0 && board.getCellColor(boardY, boardX) != null) {
                        return true; // Collision with existing blocks
                    }
                }
            }
        }
        return false;
    }

    private void settle() {
        // Transfer the shape's cells to the board
        for (int r = 0; r < coords.length; r++) {
            for (int c = 0; c < coords[0].length; c++) {
                if (coords[r][c] != 0) {
                    int boardX = x + c;
                    int boardY = y + r;
                    if (boardY >= 0) {
                        if (board.getCellColor(boardY, boardX) != null) {
                            board.setGameOver(true);
                            return; // Exit the settle method
                        }
                        board.setCellColor(boardY, boardX, color);
                    } else {
                        board.setGameOver(true);
                        return; // Exit the settle method
                    }
                }
            }
        }

        // Clear full lines and get the number of lines cleared
        int linesCleared = board.clearFullLines();

        // Update the game score and lines erased
        game.incrementLinesErased(linesCleared);
        game.updateScore(linesCleared);

        // Check if the level should be increased
        game.checkLevelUp();

        // Spawn a new shape or end the game
        if (collidesAt(board.getWidth() / 2 - coords[0].length / 2, -1)) {
            board.setGameOver(true);
        } else {
            spawnNewShape();
        }
    }

    public void render(Graphics g) {
        int yOffset = (int) ((progress * GameBoard.CELL_SIZE) / progressThreshold);

        for (int r = 0; r < coords.length; r++) {
            for (int c = 0; c < coords[0].length; c++) {
                if (coords[r][c] != 0) {
                    int pixelX = (x + c) * GameBoard.CELL_SIZE;
                    int pixelY = (y + r) * GameBoard.CELL_SIZE + yOffset;

                    g.setColor(color);
                    g.fillRect(pixelX, pixelY, GameBoard.CELL_SIZE, GameBoard.CELL_SIZE);

                    // Draw cell border
                    g.setColor(Color.GRAY);
                    g.drawRect(pixelX, pixelY, GameBoard.CELL_SIZE, GameBoard.CELL_SIZE);
                }
            }
        }
    }

    // Speed up the falling when the down key is pressed
    public void speedUp() {
        progress += 80;
        // Check collision immediately after speeding up
        while (progress >= progressThreshold) {
            progress -= progressThreshold;
            // Try to move down one row and check for collision
            if (!collidesAt(x, y + 1)) {
                y += 1;  // Move the shape down by one row
            } else {
                // If a collision occurs, mark it and settle the shape
                collision = true;
                settle();
                return;
            }
        }
        if (collidesAt(x, y + 1)) {
            collision = true;
            settle();
        }
    }

    // Getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotationState() {
        return rotationState;
    }
}
