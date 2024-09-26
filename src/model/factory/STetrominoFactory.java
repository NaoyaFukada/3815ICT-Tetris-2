package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class STetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {
                {0, 1, 1},
                {1, 1, 0}
        };
        Color color = Color.decode("#00a2e8"); // Light blue color
        return new TetrisShape(shape, color);
    }
}