import java.util.ArrayList;
/**
 * @author Roi Avraham id:318778081.
 * During the game, there are going to be many objects a Ball can collide with.
 * The GameEnvironment class will be a collection of such things.
 */
public class GameEnvironment {
    //the list of the collisions in the game
    private java.util.List<Collidable> collisions = new ArrayList<Collidable>();
    /**
     * GameEnvironment is the constructor of this class.
     * @param collisions java.util.List<Collidable> - the list
     */
    public GameEnvironment(java.util.List<Collidable> collisions) {
        collisions = collisions;
    }
    /**
     * addCollidable add the given collidable to the environment.
     * @param c Collidable
     */
    public void addCollidable(Collidable c) {
        collisions.add(c);
    }
    /**
     * getCollisions return the list of the collidabls.
     * @return collisions.
     */
    public java.util.List<Collidable> getCollisions() {
        return collisions;
    }
    /**
     * CollisionInfo assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory Line the trajectory of the ball.
     * @return CollisionInfo the information
     * about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //in case there is no collisions in the game
        if (collisions.isEmpty()) {
            return null;
        }
        //list of the collisions point in this trajectory of the ball
        java.util.List<Point> points = new ArrayList<Point>();
        //go through all the collisions
        for (int i = 0; i < collisions.size(); i++) {
            //in case there is a collisions in this trajectory
            if (collisions.get(i).getCollisionRectangle().intersectionPoints(trajectory) != null) {
                Point collision = trajectory.closestIntersectionToStartOfLine(
                        collisions.get(i).getCollisionRectangle());
                points.add(collision);
            }
        }
        //in case there is no collisions point in this trajectory
        if (points.size() == 0) {
            return null;
        }
        int min = closestPoint(points, trajectory); //closest collision that is going to occur.
        for (int i = 0; i < collisions.size(); i++) {
            if (collisions.get(i).getCollisionRectangle().intersectionPoints(trajectory) != null) {
                Point collision = trajectory.closestIntersectionToStartOfLine(
                        collisions.get(i).getCollisionRectangle());
                //in case this collision point is the closest collision that is going to occur.
                if (collision.equals(points.get(min))) {
                    //create the CollisionInfo of this collision
                    CollisionInfo info = new CollisionInfo(collision, (Collidable) collisions.get(i));
                    return info;
                }
            }
        }
        //in case there is no collisions point in this trajectory
        return null;
    }
    /**
     * closestPoint return from the list of the point the point which
     * is the closest to the start point of the trajectory.
     * @param points java.util.List<Point>
     * @param trajectory Line
     * @return min - int the index of closest point.
     */
    public int closestPoint(java.util.List<Point> points, Line trajectory) {
        ////the min distance between the point from the list to the start point
        double minDistance = trajectory.start().distance(points.get(0));
        int min = 0;
        for (int i = 1; i < points.size(); i++) {
            if (minDistance > trajectory.start().distance(points.get(i))) {
                minDistance = trajectory.start().distance(points.get(i));
                min = i;
            }
        }
        return min;
    }
}


