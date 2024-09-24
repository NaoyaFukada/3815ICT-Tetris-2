package model;

import java.awt.Color;

public enum TetrisShape {
    I(new int[][]{{1, 1, 1, 1}}, Color.decode("#ed1c24")),
    T(new int[][]{{1, 1, 1}, {0, 1, 0}}, Color.decode("#ff7f27")),
    L(new int[][]{{1, 1, 1}, {1, 0, 0}}, Color.decode("#fff200")),
    J(new int[][]{{1, 1, 1}, {0, 0, 1}}, Color.decode("#22b14c")),
    S(new int[][]{{0, 1, 1}, {1, 1, 0}}, Color.decode("#00a2e8")),
    Z(new int[][]{{1, 1, 0}, {0, 1, 1}}, Color.decode("#a349a4")),
    O(new int[][]{{1, 1}, {1, 1}}, Color.decode("#3f48cc"));

    private final int[][] shape;
    private final Color color;

    TetrisShape(int[][] shape, Color color) {
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