package model;

import util.ScoreFacade;
import util.AudioManager;

public class Game {
    public static final int STATE_GAME_PLAY = 0;
    public static final int STATE_GAME_PAUSE = 1;
    public static final int STATE_GAME_OVER = 2;

    private int state = STATE_GAME_PLAY;
    private int linesErased = 0;
    private int currentLevel;
    private int initialLevel;
    private TetrisShapeInstance currentShapeInstance;
    private Board board;
    private TetrominoSequence tetrominoSequence;

    private TetrisShape nextShape;
    private NextShapeListener nextShapeListener;
    private int tetrominoIndex = 0; // Index into the tetromino sequence

    // Use ScoreFacade instead of direct score handling
    private ScoreFacade scoreFacade;

    public Game(Board board, int initialLevel, TetrominoSequence tetrominoSequence) {
        this.initialLevel = initialLevel;
        this.currentLevel = initialLevel;
        this.board = board;
        this.tetrominoSequence = tetrominoSequence;

        // Initialize ScoreFacade
        this.scoreFacade = new ScoreFacade();

        // Initialize nextShape
        this.nextShape = tetrominoSequence.getShapeAt(tetrominoIndex++);
        setCurrentShape();
    }

    public void update() {
        if (state == STATE_GAME_PLAY) {
            currentShapeInstance.update();
        }
    }

    public void setCurrentShape() {
        currentShapeInstance = new TetrisShapeInstance(this.board, this.initialLevel, this);
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

        // Use ScoreFacade to update the score
        scoreFacade.addScore(points);
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
        // Use ScoreFacade to get the current score
        return scoreFacade.getScore();
    }

    public TetrisShape getNextShape() {
        TetrisShape shapeToReturn = nextShape;
        nextShape = tetrominoSequence.getShapeAt(tetrominoIndex++);
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
