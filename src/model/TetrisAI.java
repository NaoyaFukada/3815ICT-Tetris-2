package model;

import java.awt.Color;
import java.util.Arrays;

public class TetrisAI {
    // Evaluator used to score different board states
    private BoardEvaluator evaluator = new BoardEvaluator();

    // This method finds the best move for a given Tetris piece (shape) on the current board
    public Move findBestMove(Board board, TetrisShape shape) {
        Move bestMove = null; // Placeholder for the best move found
        int bestScore = Integer.MIN_VALUE; // Start with the lowest possible score

        // Iterate over each possible rotation
        for (int rotation = 0; rotation < 4; rotation++) {
            // Get the shape matrix after applying the current rotation
            int[][] rotatedShape = getRotatedShape(shape.getShape(), rotation);

            // Determine the range of columns where the shape can be legally placed
            int minX = -getLeftMostOffset(rotatedShape); // Furthest left the piece can be placed
            int maxX = board.getWidth() - getRightMostOffset(rotatedShape) + 1; // Furthest right

            // Iterate over each column within the legal range
            for (int col = minX; col < maxX; col++) {
                // Simulate dropping the piece at the current column
                Board simulatedBoard = simulateDrop(board, rotatedShape, col);

                if (simulatedBoard == null) {
                    // If the piece cannot be placed here, skip to the next column
                    continue;
                }

                // Evaluate the board after placing the piece
                int score = evaluator.evaluateBoard(simulatedBoard);
                if (score > bestScore) {
                    // If this placement is better than the previous best, update the best move and score
                    bestScore = score;
                    bestMove = new Move(col, rotation);
                }
            }
        }

        if (bestMove == null) {
            // If no valid moves are found (shouldn't normally happen), log a message
            System.out.println("No valid moves found for the current piece.");
        }

        // Return the best move found
        return bestMove;
    }

    // This method rotates the shape matrix by the specified number of rotations
    private int[][] getRotatedShape(int[][] shape, int rotation) {
        int[][] rotatedShape = shape;
        for (int i = 0; i < rotation; i++) {
            // Rotate the matrix 90 degrees clockwise for each rotation
            rotatedShape = rotateMatrix(rotatedShape);
        }
        return rotatedShape;
    }

    // This method rotates a matrix 90 degrees clockwise
    private int[][] rotateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];

        // Transpose and reverse each row to achieve the rotation
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = matrix[r][c];
            }
        }
        return rotated;
    }

    // This method calculates how far left the piece can be placed
    private int getLeftMostOffset(int[][] shape) {
        System.out.println(Arrays.deepToString(shape));
        // Iterate over each column to find the first non-empty cell
        for (int c = 0; c < shape[0].length; c++) {
            for (int r = 0; r < shape.length; r++) {
                if (shape[r][c] != 0) {
                    System.out.println("c" + c + "r" + r + "h" + c);
                    return c; // Return the first column with a block
                }
            }
        }
        return 0; // Default to 0 if no block is found (shouldn't happen)
    }

    // This method calculates how far right the piece can be placed
    private int getRightMostOffset(int[][] shape) {
        // Iterate from right to left to find the first non-empty cell
        for (int c = shape[0].length - 1; c >= 0; c--) {
            for (int r = 0; r < shape.length; r++) {
                if (shape[r][c] != 0) {
                    return shape[0].length - c - 1; // Return the offset from the right edge
                }
            }
        }
        return 0; // Default to 0 if no block is found (shouldn't happen)
    }

    // This method simulates dropping the piece on the board at a specific column
    private Board simulateDrop(Board board, int[][] shape, int col) {
        // Create a deep copy of the board to simulate the drop
        Board simulatedBoard = board.copy();

        // Determine the row where the piece would land
        int row = getDropRow(simulatedBoard, shape, col);

        // Check if the piece would land out of bounds (above the board)
        if (row + shape.length <= 0) {
            return null; // Return null if the piece cannot be placed
        }

        // Place the piece on the simulated board
        placePiece(simulatedBoard, shape, col, row, Color.GRAY); // Use a placeholder color

        // Clear any full lines from the simulated board
        simulatedBoard.clearFullLines();

        // Return the simulated board after the piece is placed
        return simulatedBoard;
    }

    // This method determines the row where the piece would land if dropped at the specified column
    private int getDropRow(Board board, int[][] shape, int col) {
        int row = -shape.length; // Start just above the board
        // Keep moving the piece down until it collides with something
        while (!collidesAt(board, shape, col, row + 1)) {
            row++;
        }
        return row; // Return the last valid row
    }

    // This method checks if the piece would collide with anything at the specified position
    private boolean collidesAt(Board board, int[][] shape, int x, int y) {
        // Iterate over each block in the shape
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) { // Check only if there is a block
                    int boardX = x + c;
                    int boardY = y + r;
                    // Check for collisions with the board edges or existing blocks
                    if (boardX < 0 || boardX >= board.getWidth() || boardY >= board.getHeight()) {
                        return true; // Collision with walls or floor
                    }
                    if (boardY >= 0 && board.getCellColor(boardY, boardX) != null) {
                        return true; // Collision with existing blocks
                    }
                }
            }
        }
        return false; // No collision detected
    }

    // This method places the piece on the board at the specified position
    private void placePiece(Board board, int[][] shape, int x, int y, Color color) {
        // Iterate over each block in the shape
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) { // Check only if there is a block
                    int boardX = x + c;
                    int boardY = y + r;
                    // Set the block color on the board if it's within the board's bounds
                    if (boardY >= 0 && boardY < board.getHeight() && boardX >= 0 && boardX < board.getWidth()) {
                        board.setCellColor(boardY, boardX, color);
                    }
                }
            }
        }
    }
}
