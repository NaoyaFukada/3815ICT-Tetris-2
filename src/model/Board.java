package model;

import util.AudioManager;

import java.awt.Color;

public class Board {
    private int width;
    private int height;
    private Color[][] cells;
    private boolean gameOver = false;

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
    }

    public Color getCellColor(int row, int col) {
        return cells[row][col];
    }

    public void setCellColor(int row, int col, Color color) {
        cells[row][col] = color;
    }

    public int clearFullLines() {
        int linesCleared = 0;
        for (int row = height - 1; row >= 0; row--) {
            boolean fullLine = true;
            for (int col = 0; col < width; col++) {
                if (cells[row][col] == null) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                removeLine(row);
                row++; // Check the same line again after removal
                linesCleared++;
            }
        }
        if (linesCleared > 0 && MetaConfig.getInstance().isSoundEnabled()) {
            AudioManager.playSoundEffect("erase-line");
        }
        return linesCleared;
    }

    private void removeLine(int line) {
        for (int row = line; row > 0; row--) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = cells[row - 1][col];
            }
        }
        // Clear the top line
        for (int col = 0; col < width; col++) {
            cells[0][col] = null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Board copy() {
        Board newBoard = new Board(this.width, this.height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newBoard.cells[y][x] = this.cells[y][x];
            }
        }
        return newBoard;
    }
}