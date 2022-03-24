/**
 * @author Roi Avraham id:318778081.
 * this class describe the collision object.
 */
public class CollisionInfo {
    private Point collision;  // the point at which the collision of the ball occurs.
    private Collidable object; //the object which the ball hit
    /**
     * CollisionInfo - the constructor of this class.
     * @param collision - the point at which the collision of the ball occurs.
     * @param object  Collidable - the object which the ball hit
     */
    public CollisionInfo(Point collision, Collidable object) {
        this.collision = collision;
        this.object = object;
    }
    /**
     * collisionPoint -the point at which the collision occurs.
     * @return point
     */
    public Point collisionPoint() {
        return collision;
    }
    /**
     * collisionObject - the collidable object involved in the collision.
     * @return Collidable
     */
    public Collidable collisionObject() {
        return object;
    }
}
