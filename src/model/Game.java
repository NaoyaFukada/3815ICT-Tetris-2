package model;

import util.AudioManager;

public class Game {
    public static final int STATE_GAME_PLAY = 0;
    public static final int STATE_GAME_PAUSE = 1;
    public static final int STATE_GAME_OVER = 2;

    private int state = STATE_GAME_PLAY;
    private int score = 0;
    private int linesErased = 0;
    private int currentLevel;
    private int initialLevel;
    private TetrisShapeInstance currentShapeInstance;
    private Board modelBoard;
    private TetrominoSequence tetrominoSequence;

    private TetrisShape nextShape;
    private NextShapeListener nextShapeListener;

    public Game(Board board, int initialLevel, TetrominoSequence tetrominoSequence) {
        this.initialLevel = initialLevel;
        this.currentLevel = initialLevel;
        this.modelBoard = board;
        this.tetrominoSequence = tetrominoSequence;

        // Initialize nextShape
        this.nextShape = tetrominoSequence.getNextShape();
        setCurrentShape();
    }

    public void update() {
        if (state == STATE_GAME_PLAY) {
            currentShapeInstance.update();
        }
    }

    public void setCurrentShape() {
        currentShapeInstance = new TetrisShapeInstance(this.modelBoard, this.initialLevel, this);
    }

    public void incrementLinesErased(int linesCleared) {
        linesErased += linesCleared;
    }

    public void updateScore(int linesCleared) {
        int points = 0;
        switch (linesCleared) {
            case 1: points = 100; break;
            case 2: points = 300; break;
            case 3: points = 600; break;
            case 4: points = 1000; break;
        }
        score += points;
    }

    public void checkLevelUp() {
        int newLevel = initialLevel + (linesErased / 10);
        if (newLevel > currentLevel) {
            currentLevel = newLevel;
            if (MetaConfig.getInstance().isSoundEnabled()) {
                AudioManager.playSoundEffect("level-up");
            }
        }
    }

    public int getLinesErased() {
        return linesErased;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScore() {
        return score;
    }

    public TetrisShape getNextShape() {
        TetrisShape shapeToReturn = nextShape;
        nextShape = tetrominoSequence.getNextShape();
        if (nextShapeListener != null) {
            nextShapeListener.onNextShapeChanged(nextShape);
        }
        return shapeToReturn;
    }

    public TetrisShape peekNextShape() {
        return nextShape;
    }

    public void setNextShapeListener(NextShapeListener listener) {
        this.nextShapeListener = listener;
    }

    public void pauseGame() {
        state = STATE_GAME_PAUSE;
    }

    public void resumeGame() {
        state = STATE_GAME_PLAY;
    }

    public void togglePauseGame() {
        if (state == STATE_GAME_PLAY) {
            state = STATE_GAME_PAUSE;
        } else if (state == STATE_GAME_PAUSE) {
            state = STATE_GAME_PLAY;
        }
    }

    public boolean isPaused() {
        return state == STATE_GAME_PAUSE;
    }

    public TetrisShapeInstance getCurrentShapeInstance() {
        return currentShapeInstance;
    }
}