package ui.panel;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    public static final int CELL_SIZE = 20; // Fixed size for each cell
    public static final int BOARD_WIDTH = 10; // Number of cells horizontally
    public static final int BOARD_HEIGHT = 20; // Number of cells vertically

    private Board board;

    public GameBoard() {
        // Initialize the game board
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the cells
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                // Get the cell at this position
                Color color = board.getCellColor(row, col);

                // If the cell is not empty, draw it
                if (color != null) {
                    g.setColor(color);
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

                // Draw the cell border
                g.setColor(Color.GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the current falling shape (if any)
        // For now, we can leave this empty or draw a placeholder
    }
}
