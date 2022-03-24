/**
 * @author Roi Avraham id:318778081.
 * this class describe velocity which consist of dx and dy.
 */
public class Velocity {
    private double dx; //the speed in the x axis.
    private double dy; //the speed in the y axis.
    /**
     * Velocity is the first constructor of velocity class.
     * @param dx double - the speed in the x axis.
     * @param dy double - the speed in the y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * Velocity fromAngleAndSpeed is the second constructor of velocity class.
     * the method gets angle and speed and coverts them into speed in the x axis(dx)
     * and speed in the y axis (dy).
     * @param angle double
     * @param speed double
     * @return Velocity - the speed of the ball converted from angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = speed * -Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }
    /**
     * getX return the speed in the x axis.
     * @return this.dx double number.
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * getY return the speed in the y axis.
     * @return this.dy double number.
     */
    public double getDy() {
        return this.dy;
    }
    /**
     * applyToPoint take a point with position (x,y) and return a new point
     *  with position (x+dx, y+dy).
     * @param p Point - point with position (x,y)
     * @return newPoint Point - new point with position (x+dx, y+dy).
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx; //the new position of the point in the x axis
        double newY = p.getY() + this.dy; //the new position of the point in the y axis
        Point newPoint = new Point(newX, newY); //creating the new point
        return newPoint;
    }
}
