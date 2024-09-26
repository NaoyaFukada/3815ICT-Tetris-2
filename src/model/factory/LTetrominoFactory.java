package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class LTetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {
                {1, 1, 1},
                {1, 0, 0}
        };
        Color color = Color.decode("#fff200"); // Yellow color
        return new TetrisShape(shape, color);
    }
}