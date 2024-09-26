package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class OTetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {
                {1, 1},
                {1, 1}
        };
        Color color = Color.decode("#3f48cc"); // Blue color
        return new TetrisShape(shape, color);
    }
}