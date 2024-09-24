package model;

import ui.panel.GameBoard;

import java.awt.*;
import java.util.Random;

public class TetrisShapeInstance {
    private int x;             // Grid X position
    private int y;             // Grid Y position
    private int[][] coords;    // Shape coordinates
    private Color color;       // Shape color
    private Board board;       // Reference to the game board

    // Variables for smooth movement
    private double progress = 0;            // Progress towards next row
    private final double progressIncrement; // Increment per update
    private final double progressThreshold; // Threshold to move down a row

    // Collision handling
    private boolean collision = false;
    private boolean isWaiting = false;
    private long timeOfLastCollision = 0;   // Time when collision was detected
    private final int collisionDelay = 300; // Delay in milliseconds before settling

    public TetrisShapeInstance(Board board, int initialLevel) {
        this.board = board;

        // Adjust these values to control speed and smoothness
        this.progressThreshold = 100;
        this.progressIncrement = 5 + initialLevel * 1.5;

        spawnNewShape();
    }

    public void spawnNewShape() {
        // Randomly select a shape
        TetrisShape[] shapes = TetrisShape.values();
        Random random = new Random();
        TetrisShape shape = shapes[random.nextInt(shapes.length)];

        this.coords = shape.getShape();
        this.color = shape.getColor();

        // Start position (top-middle of the board)
        this.x = board.getWidth() / 2 - coords[0].length / 2;
        this.y = 0; // Start at the top of the board
        this.progress = 0;
        this.collision = false;
        this.isWaiting = false;
    }

    public boolean update() {
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

                    // Check boundaries
                    if (boardX < 0 || boardX >= board.getWidth() || boardY >= board.getHeight()) {
                        if (boardY >= board.getHeight()) {
                            return true; // Collided with the bottom
                        }
                        continue; // Ignore other out-of-bounds positions
                    }

                    // Check if the cell is occupied
                    if (boardY >= 0 && board.getCellColor(boardY, boardX) != null) {
                        return true;
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
                    System.out.println(boardY);
                    if (boardY >= 0) {
                        board.setCellColor(boardY, boardX, color);
                    } else {
                        System.out.println("Game Over");
                        // Game over condition
                        board.setGameOver(true);
                    }
                }
            }
        }

        // Clear full lines
        board.clearFullLines();

        // Spawn a new shape
        spawnNewShape();
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
        progress += progressThreshold / 2; // Increase progress to speed up falling
    }
}