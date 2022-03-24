/**
 * @author Roi Avraham id:318778081.
 * this class tracking after the score in the game.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    /**
     * update the score.
     * @param scoreCounter Counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
       currentScore.increase(5);
    }
}
