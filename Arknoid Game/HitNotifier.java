/**
 * @author Roi Avraham id:318778081.
 * this interface describe the Notifier in the game
 */
public interface HitNotifier {
    /**
     *Add hl as a listener to hit events.
     * @param hl HitListener
     */
    void addHitListener(HitListener hl);
    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl HitListener
     */
    void removeHitListener(HitListener hl);
}
