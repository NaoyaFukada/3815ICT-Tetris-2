import static org.junit.Assert.*;

import model.Game;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    // Test for calculating score
    @Test
    public void testCalculateScore() {
        int initialScore = game.getScore();
        game.clearLines(3);  // Assume clearLines is the method that adds score
        assertEquals(initialScore + 300, game.getScore());
    }

    // Test for game over state
    @Test
    public void testIsGameOver() {
        game.setGameOver(true);
        assertTrue(game.isGameOver());
    }

    // Test for resetting the game
    @Test
    public void testResetGame() {
        game.reset();
        assertFalse(game.isGameOver());
        assertEquals(0, game.getScore());
    }

    // Test for adding a player name
    @Test
    public void testAddPlayerName() {
        game.setPlayerName("Player1");
        assertEquals("Player1", game.getPlayerName());
    }

    // Test for clearing lines and increasing level
    @Test
    public void testLevelIncrease() {
        game.clearLines(10);
        assertEquals(2, game.getLevel());
    }
}
