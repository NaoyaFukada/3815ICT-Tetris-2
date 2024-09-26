package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TetrominoSequence {
    private Queue<TetrisShape> sequence;
    private Random random;

    // This is called from PlayPanel
    public TetrominoSequence() {
//        random = new Random(seed);
        random = new Random();
        sequence = new LinkedList<>();
        generateNextShapes(1000);
    }

    private void generateNextShapes(int count) {
        TetrisShape[] shapes = TetrisShape.values();
        for (int i = 0; i < count; i++) {
            TetrisShape shape = shapes[random.nextInt(shapes.length)];
            sequence.add(shape);
        }
    }

    // This is where to access to get next shape
    public synchronized TetrisShape getNextShape() {
        if (sequence.isEmpty()) {
            generateNextShapes(1000);
        }
        return sequence.poll();
    }
}