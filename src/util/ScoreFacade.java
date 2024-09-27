package util;

public class ScoreFacade {

    private int score = 0;

    // Method to reset the score
    public void resetScore() {
        score = 0;
    }

    // Method to add points to the score
    public void addScore(int points) {
        score += points;
    }

    // Method to get the current score
    public int getScore() {
        return score;
    }
}
