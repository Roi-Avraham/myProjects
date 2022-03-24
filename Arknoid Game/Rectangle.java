import java.util.ArrayList;
/**
 * @author Roi Avraham id:318778081.
 * this class describe rectangle.
 */
public class Rectangle {
    private Point upperLeft; //the point of the uperleft of the rectangle.
    private double width; //the width of the rectangle.
    private double height; //the height of the rectangle.
    /**
     * Rectangle is the constructor of this class. Create a new rectangle
     * with location and width/height.
     * @param height Point
     * @param width Point
     * @param upperLeft Point
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * intersectionPoints returns all the points of the line which are
     * intersect with the rectangle.
     * @param line Line
     * @return intersection java.util.List<Point>
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        //the list of the points of the line which are intersect with the rectangle.
        java.util.List<Point> intersection = new ArrayList<Point>();
        //the down edge of the rectangle.
        Line down = new Line(new Point(upperLeft.getX(), upperLeft.getY() - height),
                new Point(upperLeft.getX() + width, upperLeft.getY() - height));
        //the up edge of the rectangle.
        Line up = new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY()));
        //the left edge of the rectangle.
        Line left = new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() - height));
        //the right edge of the rectangle.
        Line right = new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                new Point(upperLeft.getX() + width, upperLeft.getY() - height));
        //in case the line is intersect with up edge of the ball
        if (line.intersectionWith(up) != null) {
            intersection.add(line.intersectionWith(up));
        }
        //in case the line is intersect with down edge of the ball
        if (line.intersectionWith(down) != null) {
            intersection.add(line.intersectionWith(down));
        }
        //in case the line is intersect with left edge of the ball
        if (line.intersectionWith(left) != null) {
            intersection.add(line.intersectionWith(left));
        }
        //in case the line is intersect with right edge of the ball
        if (line.intersectionWith(right) != null) {
            intersection.add(line.intersectionWith(right));
        }
        if (intersection.size() != 0) {
            return intersection;
        }
        //in case there is no intersect with the edges of the rectangle
        return null;
    }
    /**
     * getWidth.
     * @return this.width - the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * getHeight.
     * @return this.height - the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * getUpperLeft.
     * @return upperLeft - the upper left of the rectangle
     */
    public Point getUpperLeft() {
        return upperLeft;
    }
    /**
     * edges gets a line and return a number which symbolise which edge of the ractangle the line
     * intersect with.
     * @param l - the line
     * @return int
     */
    public int edges(Line l) {
        Line up = new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY()));
        Line down = new Line(new Point(upperLeft.getX(), upperLeft.getY() - height),
                new Point(upperLeft.getX() + width, upperLeft.getY() - height));
        Line left = new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() - height));
        Line right = new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                new Point(upperLeft.getX() + width, upperLeft.getY() - height));
        if (up.intersectionWith(l) != null) {
            return 1;
        }
        if (down.intersectionWith(l) != null) {
            return 1;
        }
        if (right.intersectionWith(l) != null) {
            return 2;
        }
        if (left.intersectionWith(l) != null) {
            return 2;
        }
        return 0;
    }
    /**
     *  edgeUp.
     * @return up - the upper edge of the rectangle
     */
    public Line edgeUp() {
        Line up = new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY()));
        return up;
    }
    /**
     *  edgeDown.
     * @return down - the down edge of the rectangle
     */
    public Line edgeDown() {
        Line down = new Line(new Point(upperLeft.getX(), upperLeft.getY() - height),
                new Point(upperLeft.getX() + width, upperLeft.getY() - height));
        return down;
    }
    /**
     * edgeLeft.
     * @return left - the left edge of the rectangle
     */
    public Line edgeLeft() {
        Line left = new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() - height));
        return left;
    }
    /**
     * edgeRight.
     * @return right - the right edge of the rectangle
     */
    public Line edgeRight() {
        Line right = new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                new Point(upperLeft.getX() + width, upperLeft.getY() - height));
        return right;
    }
}
