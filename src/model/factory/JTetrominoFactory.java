package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class JTetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {
                {1, 1, 1},
                {0, 0, 1}
        };
        Color color = Color.decode("#22b14c"); // Green color
        return new TetrisShape(shape, color);
    }
}