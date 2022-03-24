/**
 * @author Roi Avraham id:318778081.
 * this class describe point which consist of x and y.
 */
public class Point {
    private double x; //the place of the point in the axis x
    private double y; //the place of the point in the axis y
    /**
     * Point is the constructor of Point class.
     * the function receives the place of the point (x,y) and create new point
     * in this place.
     * @param x double number represents the position of the point on the x-axis
     * @param y double number represents the position of the point on the y-axis
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * distance receives other point and calculate and return the distance of
     * this point to the other point.
     * @param other Point - other point.
     * @return distanceOfThePoints double number - distance of this point to the other point.
     */
    public double distance(Point other) {
        double distanceOfThePoints = ((this.x - other.getX()) * (this.x - other.getX()))
                + ((this.y - other.getY()) * (this.y - other.getY()));
        distanceOfThePoints = Math.sqrt(distanceOfThePoints);
        return distanceOfThePoints; //the distance of this point to the other point.
    }
    /**
     * equals receives other point and return true if the points are equal, false otherwise.
     * @param other Point - other point.
     * @return boolean
     */
    public boolean equals(Point other) {
        double epsilon = Math.pow(10, -2);
        //in case the x position and the y position of this point is the same as the other point.
        if (Math.abs(this.x - other.getX()) <= epsilon && Math.abs(this.y - other.getY()) <= epsilon) {
            return true;
        }
        return false; //the points are not the same
    }
    /**
     * getX return the position of this point in the x axis.
     * @return x double number.
     */
    public double getX() {
        return this.x;
    }
    /**
     * getX return the position of this point in the y axis.
     * @return y double number.
     */
    public double getY() {
        return this.y;
    }
}
