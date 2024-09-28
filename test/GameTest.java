import model.Board;
import model.Game;
import model.TetrominoSequence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Board board;
    private TetrominoSequence sequence;

    @Before
    public void setUp() {
        // Initialize the required objects for Game
        board = new Board(10, 20);  // Assuming Board has a constructor that takes width and height
        sequence = new TetrominoSequence();  // Assuming TetrominoSequence has a no-argument constructor
        game = new Game(board, 1, sequence);  // Initialize Game with board, initial level, and sequence
    }

    // Test for calculating score based on lines cleared
    @Test
    public void testUpdateScore() {
        int initialScore = game.getScore();
        game.updateScore(3);  // Simulate clearing 3 lines
        assertEquals(initialScore + 600, game.getScore());  // 600 points for 3 lines
    }

    // Test for incrementing lines erased
    @Test
    public void testIncrementLinesErased() {
        int initialLinesErased = game.getLinesErased();
        game.incrementLinesErased(2);  // Simulate erasing 2 lines
        assertEquals(initialLinesErased + 2, game.getLinesErased());
    }

    // Test for checking level progression
    @Test
    public void testCheckLevelUp() {
        game.incrementLinesErased(10);  // Erase 10 lines to trigger level up
        game.checkLevelUp();  // Should increment level
        assertEquals(2, game.getCurrentLevel());  // Level should now be 2
    }

    // Test for pausing and resuming the game
    @Test
    public void testPauseResumeGame() {
        game.pauseGame();  // Pause the game
        assertTrue(game.isPaused());

        game.resumeGame();  // Resume the game
        assertFalse(game.isPaused());
    }

    // Test for retrieving the next tetromino shape
    @Test
    public void testGetNextShape() {
        assertNotNull(game.getNextShape());  // Ensure a tetromino shape is retrieved
    }

    // Test for toggling pause and resume
    @Test
    public void testTogglePauseGame() {
        game.togglePauseGame();  // Toggle to pause
        assertTrue(game.isPaused());

        game.togglePauseGame();  // Toggle to resume
        assertFalse(game.isPaused());
    }
}
