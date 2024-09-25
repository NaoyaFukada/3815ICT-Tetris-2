package model;

import java.awt.*;
import java.util.Random;
import model.Board;

public class Game {
    public static final int STATE_GAME_PLAY = 0;
    public static final int STATE_GAME_PAUSE = 1;
    public static final int STATE_GAME_OVER = 2;

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    private int state = STATE_GAME_PLAY;
    private int score = 0;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private TetrisShapeInstance currentShapeInstance;
    private Random random = new Random();
    private int initialLevel;
    private Board modelBoard;

    public Game(Board board, int initialLevel) {
        this.initialLevel = initialLevel;
        this.modelBoard = board;
        setCurrentShape();
    }

    public void update() {
        if (state == STATE_GAME_PLAY) {
            currentShapeInstance.update();
        }
    }

    public void setCurrentShape() {
        currentShapeInstance = new TetrisShapeInstance(this.modelBoard, this.initialLevel);
    }

    public void incrementScore(int value) {
        score += value;
    }

    public void reset() {
        board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        score = 0;
        state = STATE_GAME_PLAY;
        setCurrentShape();
    }

    public void pauseGame() {
        state = STATE_GAME_PAUSE;
    }

    public void resumeGame() {
        state = STATE_GAME_PLAY;
    }

    public void togglePauseGame() {
        if (state == STATE_GAME_PLAY) {
            state = STATE_GAME_PAUSE;  // Pause the game
        } else if (state == STATE_GAME_PAUSE) {
            state = STATE_GAME_PLAY;  // Resume the game
        }
    }

    public boolean isPaused() {
        return state == STATE_GAME_PAUSE;
    }

    // Getters and setters for state, score, board, etc.
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color[][] getBoard() {
        return board;
    }

    public TetrisShapeInstance getCurrentShapeInstance() {
        return currentShapeInstance;
    }

    public int getScore() {
        return score;
    }
}