import biuoop.DrawSurface;
import java.util.List;
/**
 * @author Roi Avraham id:318778081.
 * LevelInformation is interface of the levels in the game.
 */
public interface LevelInformation {
    /**
     * numberOfBalls returns the number of the balls.
     * @return int.
     */
    int numberOfBalls();
    /**
     * The initial velocity of each ball
     * Note that initialBallVelocities().size() == numberOfBalls().
     * @return List<Velocity>.
     */
    List<Velocity> initialBallVelocities();
    /**
     * paddleSpeed returns the speed of the paddle.
     * @return int.
     */
    int paddleSpeed();
    /**
     * paddleSpeed returns width the of the paddle.
     * @return int.
     */
    int paddleWidth();
    /**
     * the level name will be displayed at the top of the screen.
     * @return String.
     */
    String levelName();
    /**
     * Returns a sprite with the background of the level.
     * @return Sprite.
     */
    Sprite getBackground();
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return List<Block>.
     */
    List<Block> blocks();
    /**
     * Number of blocks that should be removed
     * before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return int.
     */
    int numberOfBlocksToRemove();
    /**
     * this function initialize the Level.
     * @param gameLevel GameLevel
     */
    void initializeLevel(GameLevel gameLevel);
    /**
     * returns the number of the balls to remove until the level finishes.
     * @return int.
     */
    int numberOfBallsToRemove();
    /**
     * draw the background of the level.
     * @param surface DrawSurface.
     */
    void drawOn(DrawSurface surface);
}
