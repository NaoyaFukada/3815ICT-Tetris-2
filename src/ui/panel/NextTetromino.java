package ui.panel;

import model.TetrisShape;

import javax.swing.*;
import java.awt.*;

public class NextTetromino extends JPanel {

    private TetrisShape nextShape;

    public NextTetromino(){
        // Initialization if needed
    }

    public void setNextShape(TetrisShape shape) {
        this.nextShape = shape;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (nextShape != null) {
            // Draw the next shape
            int[][] coords = nextShape.getShape();
            Color color = nextShape.getColor();

            // Determine cell size based on panel size and shape dimensions
            int cellSize = Math.min(getWidth() / coords[0].length, getHeight() / coords.length);

            int xOffset = (getWidth() - coords[0].length * cellSize) / 2;
            int yOffset = (getHeight() - coords.length * cellSize) / 2;

            for (int r = 0; r < coords.length; r++) {
                for (int c = 0; c < coords[0].length; c++) {
                    if (coords[r][c] != 0) {
                        int x = xOffset + c * cellSize;
                        int y = yOffset + r * cellSize;
                        g.setColor(color);
                        g.fillRect(x, y, cellSize, cellSize);
                        g.setColor(Color.GRAY);
                        g.drawRect(x, y, cellSize, cellSize);
                    }
                }
            }
        } else {
            // Draw a placeholder if no shape is available
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
        }
    }
}