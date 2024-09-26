package ui.panel;

import model.Board;
import model.Game;
import model.TetrisShapeInstance;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    public static final int CELL_SIZE = 20; // Fixed size for each cell

    private Board board;
    private TetrisShapeInstance currentShape;
    private Game game;

    public GameBoard(Board board, TetrisShapeInstance currentShape, Game game) {
        this.board = board;
        this.currentShape = currentShape;
        this.game = game;

        // Set the preferred size
        int width = board.getWidth() * CELL_SIZE;
        int height = board.getHeight() * CELL_SIZE;
        setPreferredSize(new Dimension(width, height));
    }

    // Override paintComponent to render the game board
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the cells from the board
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
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

        // Draw the current falling shape
        currentShape.render(g);

        // If the game is paused, draw the message
        if (game.isPaused()) {
            // Draw a semi-transparent overlay
            g.setColor(new Color(0, 0, 0, 150)); // RGBA color with alpha transparency
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw the pause message on two lines
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            String message1 = "Game is paused.";
            String message2 = "Press 'P' to continue.";

            FontMetrics fm = g.getFontMetrics();
            int x1 = (getWidth() - fm.stringWidth(message1)) / 2;
            int x2 = (getWidth() - fm.stringWidth(message2)) / 2;
            int y = getHeight() / 2;

            // Draw the first message line
            g.drawString(message1, x1, y - 10); // Slightly above center

            // Draw the second message line
            g.drawString(message2, x2, y + 10); // Slightly below center
        }
    }
}