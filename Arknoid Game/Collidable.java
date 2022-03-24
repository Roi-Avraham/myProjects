/**
 * @author Roi Avraham id:318778081.
 * this interface describe all the Collidable objects
 */
public interface Collidable {
    /**
     * getCollisionRectangle return the "collision shape" of the object.
     * @return Rectangle the shape of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * hit notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param collisionPoint Point.
     * @param currentVelocity Velocity.
     * @param hitter Ball
     * @return Velocity the new velocity.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
