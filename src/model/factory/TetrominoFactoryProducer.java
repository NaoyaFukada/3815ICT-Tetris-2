package model.factory;

public class TetrominoFactoryProducer {
    public static TetrominoFactory getFactory(TetrominoType type) {
        return switch (type) {
            case I -> new ITetrominoFactory();
            case T -> new TTetrominoFactory();
            case L -> new LTetrominoFactory();
            case J -> new JTetrominoFactory();
            case S -> new STetrominoFactory();
            case Z -> new ZTetrominoFactory();
            case O -> new OTetrominoFactory();
        };
    }
}