import biuoop.DrawSurface;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
/**
 * @author Roi Avraham id:318778081.
 * this class describe the block in the game
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners = new ArrayList<>();
    private Rectangle rectangle; //the shape of the block
    private java.awt.Color color; //the color of the block
    /**
     * addHitListener add the Hit Listener to the list.
     * @param hl HitListener.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }
    /**
     * removeHitListener remove the Hit Listener to the list.
     * @param hl HitListener.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
    /**
     * the constructor of this class.
     * @param rectangle Rectangle.
     * @param color java.awt.Color.
     */
    public Block(Rectangle rectangle, java.awt.Color color) {
        this.rectangle = rectangle; //the shape of the block
        this.color = color; //the color of the block
    }
    /**
     * getRectangle return the shape of the block.
     * @return rectangle.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
    /**
     * getColor return the color of the block.
     * @return color.
     */
    public java.awt.Color getColor() {
        return color;
    }
    /**
     * getCollisionRectangle return the collision shape of the block.
     * @return this.rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line line = new Line(collisionPoint, collisionPoint); //the collision point as line
        //in case the ball hit the up edge
        if (rectangle.edgeUp().intersectionWith(line) != null) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //in case the ball hit the down or left edge
        if (rectangle.edgeDown().intersectionWith(line) != null) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //in case the ball hit the right or left edge
        if (rectangle.edgeRight().intersectionWith(line) != null) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        //in case the ball hit the left or left edge
        if (rectangle.edgeLeft().intersectionWith(line) != null) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        hitter.notifyHit(this);
        this.notifyHit(hitter);
        return currentVelocity; //return the new velocity
    }
    /**
     * drawOn draw the block.
     * @param surface DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        if (this.rectangle.getUpperLeft().equals(new Point(0, 600))
                && this.rectangle.getWidth() == 800 && this.rectangle.getHeight() == 20) {
            return;
        }
        surface.setColor(this.color); //draw the ball with its color
        //draw the borders of the rectangle.
        surface.fillRectangle((int) rectangle.getUpperLeft().getX(),
                (int) rectangle.getUpperLeft().getY()
                - (int) rectangle.getHeight(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
        surface.setColor(Color.BLACK); //draw the ball with its color
        //draw the rectangle
        surface.drawRectangle((int) rectangle.getUpperLeft().getX(),
                (int) rectangle.getUpperLeft().getY()
                        - (int) rectangle.getHeight(), (int) rectangle.getWidth(), (int) rectangle.getHeight());

    }
    /**
     * timePassed.
     */
    public void timePassed() {
        return;
    }
    /**
     * addToGame - add the ball into the game.
     * @param g - Game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this); //add the block to the spirit list
        g.addCollidable(this); //add the block to the collidable list
    }
    /**
     * removeFromGame remove the block from the game.
     * @param game Game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    /**
     * notifyHit tells the block that ball hits him.
     * @param hitter Ball.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
