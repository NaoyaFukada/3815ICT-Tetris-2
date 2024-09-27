package model.facade;

import model.ScoreManager;

public class ScoreFacade {

    private ScoreManager scoreManager;

    // Constructor that initializes ScoreManager
    public ScoreFacade() {
        this.scoreManager = new ScoreManager();
    }

    // Method to reset the score
    public void resetScore() {
        scoreManager.resetScore();
        System.out.println("Score has been reset.");
    }

    // Method to add points to the score
    public void addScore(int points) {
        scoreManager.addScore(points);
        System.out.println("Added " + points + " points. Current score: " + scoreManager.getScore());
    }

    // Method to get the current score
    public int getScore() {
        return scoreManager.getScore();
    }
}
