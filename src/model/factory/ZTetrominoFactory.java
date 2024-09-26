package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class ZTetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {
                {1, 1, 0},
                {0, 1, 1}
        };
        Color color = Color.decode("#a349a4"); // Purple color
        return new TetrisShape(shape, color);
    }
}