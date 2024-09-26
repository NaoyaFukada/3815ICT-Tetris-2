package model.factory;

import model.TetrisShape;

import java.awt.Color;

public class ITetrominoFactory implements TetrominoFactory {
    @Override
    public TetrisShape createTetromino() {
        int[][] shape = {{1, 1, 1, 1}};
        Color color = Color.decode("#ed1c24"); // Red color
        return new TetrisShape(shape, color);
    }
}