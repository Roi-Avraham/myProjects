/**
 * @author Roi Avraham id:318778081.
 * this class describe about the ball which is removed from the game.
 */
public class BallRemover  implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;
    private Block deathRegion;
    /**
     * BallRemover is the constructor of this class.
     * @param game Game.
     * @param remainingBalls Counter.
     * @param deathRegion Block.
     */
    public BallRemover(GameLevel game, Counter remainingBalls, Block deathRegion) {
        this.game = game;
        this.remainingBalls = remainingBalls;
        this.deathRegion = deathRegion;
    }
    /**
     * BallRemover is the constructor of this class.
     * @param game Game.
     * @param remainingBalls Counter.
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }
    /**
     * getRemainingBalls returns the remainingBalls.
     * @return remainingBalls.
     */
    public Counter getRemainingBalls() {
        return this.remainingBalls;
    }
    /**
     * Blocks that are hit should be removed
     * from the game. Remember to remove this listener from the block
     * that is being removed from the game.
     * @param beingHit Block.
     * @param hitter Ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit == this.deathRegion) {
            hitter.removeFromGame(game);
            remainingBalls.decrease(1);
        }
    }
}
