package model;

import model.factory.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TetrominoSequence {
    private List<TetrominoFactory> factories;
    private List<TetrisShape> sequence;
    private Random random;

    public TetrominoSequence() {
        factories = new ArrayList<>();
        for (TetrominoType type : TetrominoType.values()) {
            factories.add(TetrominoFactoryProducer.getFactory(type));
        }

        random = new Random(0); // Use a fixed seed for determinism
        sequence = new ArrayList<>();
        generateInitialSequence();
    }

    private void generateInitialSequence() {
        // Generate an initial sequence of tetrominoes
        // You can adjust the size as needed
        int initialSize = 1000;
        generateMoreShapes(initialSize);
    }

    private void generateMoreShapes(int count) {
        List<TetrominoFactory> bag = new ArrayList<>(factories);

        while (count > 0) {
            if (bag.isEmpty()) {
                bag.addAll(factories);
                Collections.shuffle(bag, random);
            }
            TetrominoFactory factory = bag.remove(0);
            sequence.add(factory.createTetromino());
            count--;
        }
    }

    public synchronized TetrisShape getShapeAt(int index) {
        // Ensure the sequence is long enough
        while (index >= sequence.size()) {
            // Generate more shapes as needed
            generateMoreShapes(7); // Generate in batches of 7 (a full bag)
        }
        return sequence.get(index);
    }
}