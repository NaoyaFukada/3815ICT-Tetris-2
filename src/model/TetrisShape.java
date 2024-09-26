package model;

import java.awt.Color;

public class TetrisShape {
    private final int[][] shape;
    private final Color color;

    public TetrisShape(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}