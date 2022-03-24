import biuoop.DrawSurface;
/**
 * @author Roi Avraham id:318778081.
 * this interface describe all the Animations objects
 */
public interface Animation {
    /**
     * doOneFrame shows the frame of the animation on the given drawSurface.
     * @param d DrawSurface
     */
    void doOneFrame(DrawSurface d);
    /**
     * shouldStop indicates when the current animation need to stop shows her frame.
     * @return true/false boolean
     */
    boolean shouldStop();
}
