package ui.panel;

import javax.swing.*;
import java.awt.*;

public class NextTetromino extends JPanel {

    public NextTetromino(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw a placeholder for the next Tetromino
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
    }
}
