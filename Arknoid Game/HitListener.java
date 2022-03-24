/**
 * @author Roi Avraham id:318778081.
 * this interface describe the Listeners in the game
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit Block.
     * @param hitter Ball.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
