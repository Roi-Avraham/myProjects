import biuoop.DrawSurface;
/**
 * @author Roi Avraham id:318778081.
 * this interface describe all the objects in the game
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     * @param d DrawSurface
     */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
    /**
     * add all the objects to the spirits list of the game.
     * @param g the game.
     */
    void addToGame(GameLevel g);
}
