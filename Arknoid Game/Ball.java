import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roi Avraham id:318778081.
 * this class describe ball which consist of center point,
 * radius, color and velocity.
 */
public class Ball implements Sprite, HitNotifier {
    private List<HitListener> hitListeners = new ArrayList<>();
    private GameEnvironment gameEnvironment;
    private Point center; //the center of the ball
    private int size; //the radius of the ball
    private java.awt.Color color; //the color of the ball
    private Velocity velocity; //the velocity of the ball
    /**
     * Ball is the first constructor of ball class.
     * @param center Point - the point which is the center of the ball.
     * @param r int - number represents the radius of the ball.
     * @param color java.awt.Color - the color of the ball.
     * @param gameEnvironment - the game.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.size = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }
    /**
     * Ball is the first constructor of ball class.
     * @param center Point - the point which is the center of the ball.
     * @param r int - number represents the radius of the ball.
     * @param color java.awt.Color - the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.size = r;
        this.color = color;
    }
    /**
     * Ball is the second constructor of the ball class.
     * @param x double - the place of the center point of the ball in the axis x.
     * @param y double - the place of the center point of the ball in the axis y.
     * @param r int - number represents the radius of the ball.
     * @param color java.awt.Color - the color of the ball.
     * @param gameEnvironment - the game.
     */
    public Ball(double x, double y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = new Point((double) x, (double) y);
        this.size = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }
    /**
     * getX return the position of the center point of the ball in the x axis.
     * @return this.center.getX() double number.
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * getY return the position of the center point of the ball in the y axis.
     * @return this.center.getY() double number.
     */
    public int getY() {
        return (int) this.center.getY();
    }
    /**
     * getSize return the radius of the center point of the ball.
     * @return this.size int number.
     */
    public int getSize() {
        return this.size;
    }
    /**
     * getColor return the color of the ball.
     * @return this.color java.awt.Color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * drawOn draw the ball on the given DrawSurface.
     * @param surface DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color); //draw the ball with its color
        surface.fillCircle(this.getX(), this.getY(), this.size);
    }
    /**
     * setVelocity gets velocity and add it to the ball.
     * @param v Velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**
     * setVelocity gets dx (the change position of the ball in the x-axis)
     * and dy (the change position of the ball in the y-axis). the function
     * create new velocity and add it to the ball
     * @param dx double.
     * @param dy double.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    /**
     * getVelocity return the velocity of the ball.
     * @return velocity Velocity.
     */
    public Velocity getVelocity() {
        //in case the velocity was not initialization
        if (velocity == null) {
            this.velocity = new Velocity(0, 0);
        }
        return velocity;
    }
    /**
     * moveOneStep moves the ball according to its position in the game and its velocity.
     */
    public void moveOneStep() {
        Point start = new Point(center.getX(), center.getY()); //the start point of the trajectory
        //the end point of the trajectory
        Point end = new Point(center.getX() + this.getVelocity().getDx(),
                center.getY() + this.getVelocity().getDy());
        Line trajectory = new Line(start, end); //the trajectory of the ball
        //in case the ball doesnt collides with any object
        if (gameEnvironment.getClosestCollision(trajectory) == null) {
            //move the ball in its velocity
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            //the information about the object which the ball collides with
            CollisionInfo info = gameEnvironment.getClosestCollision(trajectory);
            Line l = new Line(info.collisionPoint(), info.collisionPoint());
            //in case the collision point is not really touch the edges of the object
            if (info.collisionObject().getCollisionRectangle().edges(l) == 0) {
                //move the ball in its velocity
                this.center = this.getVelocity().applyToPoint(this.center);
                return;
            }
            //move the ball to "almost" the hit point, but just slightly before it.
            center = new Point((int) (info.collisionPoint().getX() -  velocity.getDx()),
                    (int) (info.collisionPoint().getY() -   velocity.getDy()));
            //almostThere(info.collisionObject(), info.collisionPoint());
            //move the ball after the hitting
            setVelocity(info.collisionObject().hit(this, info.collisionPoint(), velocity));
            //this.center = this.getVelocity().applyToPoint(this.center);
        }
    }
    /**
     * almostThere gets the object the ball collides with and the collisionPoint.
     * the function move the ball to "almost" the hit point, but just slightly before it.
     * @param collisionPoint Point.
     * @param object Collidable.
     */
    public void almostThere(Collidable object, Point collisionPoint) {
        Line l = new Line(collisionPoint, collisionPoint);
        Line up = object.getCollisionRectangle().edgeUp();
        Line down = object.getCollisionRectangle().edgeDown();
        Line left = object.getCollisionRectangle().edgeLeft();
        Line right = object.getCollisionRectangle().edgeRight();
        if (l.intersectionWith(up) != null) {
            center = new Point((int) (collisionPoint.getX() - velocity.getDx()), (int) collisionPoint.getY() + size);
        } else if (l.intersectionWith(down) != null) {
            center = new Point((int) (collisionPoint.getX() - velocity.getDx()), (int) collisionPoint.getY() - size);
        } else if (l.intersectionWith(left) != null) {
            center = new Point((int) collisionPoint.getX() - size, (int) (collisionPoint.getY() - velocity.getDy()));
        } else if (l.intersectionWith(right) != null) {
            center = new Point((int) collisionPoint.getX() + size, (int) (collisionPoint.getY() - velocity.getDy()));
        }
    }
    @Override
    public void timePassed() {
        moveOneStep();
    }
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    /**
     * removeFromGame remove the ball from the game.
     * @param g Game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
    /**
     * notifyHit tells the block that ball hits him.
     * @param death Block.
     */
    public void notifyHit(Block death) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(death, this);
        }
    }
}
