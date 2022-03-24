import biuoop.DrawSurface;
import java.util.ArrayList;
/**
 * @author Roi Avraham id:318778081.
 * this class describe the Sprite Collection.
 */
public class SpriteCollection {
    private java.util.List<Sprite> sprites; //the list of all the spirits in the game
    /**
     * getSprites returns the sprite list.
     * @return sprites.
     */
    public java.util.List<Sprite> getSprites() {
        return sprites;
    }
    /**
     * SpriteCollection is the constructor of this class.
     */
    public SpriteCollection() {
        sprites = new ArrayList<Sprite>();
    }
    /**
     * addSprite adding new spirit to the list.
     * @param s Sprite - the new spirit.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }
    /**
     * notifyAllTimePassed is runs the function timePassed in all the spirits in the list.
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).timePassed();
        }
    }
    /**
     * drawAllOn is runs the function drawOn(d) on all sprites.
     * @param d DrawSurface which all the spirits will be draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).drawOn(d);
        }
    }
}
