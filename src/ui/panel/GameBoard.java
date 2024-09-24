package ui.panel;

import model.Board;
import model.TetrisShapeInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel implements ActionListener {
    public static final int CELL_SIZE = 20; // Fixed size for each cell
    public static int BOARD_WIDTH; // Number of cells horizontally
    public static int BOARD_HEIGHT; // Number of cells vertically

    private Board board;
    private TetrisShapeInstance currentShape;
    private Timer timer;

    public GameBoard(int w, int h, int l) {
        // Initialize the game board
        board = new Board(w, h);
        BOARD_WIDTH = w;
        BOARD_HEIGHT = h;

        // Initialize the current shape
        currentShape = new TetrisShapeInstance(board, l);

        // Set the preferred size
        setPreferredSize(new Dimension(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE));

        // Initialize the game loop timer
        int delay = 20; // Adjust the delay as needed (milliseconds)
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!board.isGameOver()) {
            currentShape.update();
            repaint();
        } else {
            timer.stop();
            System.out.println("Stop");
            // Optionally, show a game over message
//            JOptionPane.showMessageDialog(this, "Game Over!");
        }
    }

    // Calling repaint() will ultimately trigger the paintComponent() method to be called
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

        // Draw the current falling shape
        currentShape.render(g);
    }
}