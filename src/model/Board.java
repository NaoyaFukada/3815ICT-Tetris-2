package model;

import java.awt.Color;

public class Board {
    private int width;
    private int height;
    private Color[][] cells;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Color[height][width];

        // Initialize the board with empty cells (null)
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = null;
            }
        }

        // For demonstration, let's fill some cells with random colors
        // Remove this in the actual game logic
        cells[5][5] = Color.RED;
        cells[6][5] = Color.BLUE;
        cells[7][5] = Color.GREEN;
    }

    public Color getCellColor(int row, int col) {
        return cells[row][col];
    }

    public void setCellColor(int row, int col, Color color) {
        cells[row][col] = color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}