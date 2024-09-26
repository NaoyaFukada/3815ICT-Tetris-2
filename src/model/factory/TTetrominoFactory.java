package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class TTetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {
                {1, 1, 1},
                {0, 1, 0}
        };
        Color color = Color.decode("#ff7f27"); // Orange color
        return new TetrisShape(shape, color);
    }
}