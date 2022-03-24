/**
 * @author Roi Avraham id:318778081.
 * this class describe Line which consist of two points.
 * the start point of the line with the position (x1,y1) and
 * the end point of the line with the position (x2,y2).
 */
public class Line {
    private double x1; //the place of the start point in the axis x
    private double y1; //the place of the start point in the axis y
    private double x2; //the place of the end point in the axis x
    private double y2; //the place of the end point in the axis y
    /**
     * Line is the constructor of Line class.
     * the function receives 2 points and create a line.
     * @param start Point the start point of the line.
     * @param end Point the end point of the line.
     */
    public Line(Point start, Point end) {
        this.x1 = start.getX(); //the place of the start point in the axis x
        this.y1 = start.getY(); //the place of the start point in the axis y
        this.x2 = end.getX(); //the place of the end point in the axis x
        this.y2 = end.getY(); //the place of the end point in the axis y
    }
    /**
     * Line is another constructor of Line class.
     * the function receives the positions of the start and the end point and create a line.
     * @param x1  double number - the place of the start point in the axis x
     * @param y1  double number - the place of the start point in the axis y
     * @param x2  double number - the place of the end point in the axis x
     * @param y2  double number - the place of the end point in the axis y
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    /**
     * length function return the length of the line.
     * @return distanceOfThePoints double number represent the length of the line
     */
    public double length() {
        double distanceOfThePoints = (this.x1 - this.x2) * (this.x1 - this.x2)
                + (this.y1 - this.y2) * (this.y1 - this.y2);
        //the distance of the start point from the end point
        distanceOfThePoints = Math.sqrt(distanceOfThePoints);
        return distanceOfThePoints;
    }
    /**
     * middle function returns the middle point of the line.
     * @return mid Point - the middle point of the line
     */
    public Point middle() {
        Point mid = new Point((this.x1 + this.x2) / 2, (this.y1 + this.y2) / 2);
        return mid;
    }
    /**
     * start function returns the start point of the line.
     * @return s Point - the start point of the line
     */
    public Point start() {
        Point s = new Point(this.x1, this.y1);
        return s;
    }
    /**
     * start function returns the end point of the line.
     * @return e Point - the end point of the line
     */
    public Point end() {
        Point e = new Point(this.x2, this.y2);
        return e;
    }
    /**
     * isTheSamePoint function gets line and return true if both of the line
     * (this and other) are the same point. otherwise return false.
     * @param other Line - another line
     * @return true/false - true if the both lines are the same point and false otherwise.
     */
    public boolean isTheSamePoint(Line other) {
        //checks if the both lines are points
        if (this.start().equals(this.end()) && other.start().equals(other.end())) {
            //checks if the both lines are the same point
            if (this.start().equals(other.start())) {
                return true;
            }
        }
        return false; //the lines are not the same point
    }
    /**
     * segmentsOnTheLines gets another segment which is on the same line of
     * this segment. the function return true if the 2 segments intersecting otherwise
     * return false.
     * @param other Line - other line.
     * @return true/false boolean
     */
    public boolean segmentsOnTheLines(Line other) {
        Point mid1 = this.middle(); //the middle point of this segment
        Point mid2 = other.middle(); //the middle point of other segment
        double len1 = mid1.distance(this.end()); //the half length of this segment
        double len2 = mid2.distance(other.end()); //the half length of the other segment
        double centerDis = mid1.distance(mid2); //the distance between the 2 middles points
        //in case len1+len2 is bigger then the centerDis that is mean the 2 segments intersecting
        return len1 + len2  >=  centerDis;
    }
    /**
     * isIntersecting function returns true if the lines (this and the other)
     * intersect, false otherwise.
     * @param other Line - other line.
     * @return true/false boolean
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }
    /**
     * isPoint function returns true if the line is a point and false otherwise.
     * @return true/false boolean
     */
    public boolean isPoint() {
        //return true is the start point of the line is equal to the end point and false otherwise
        return start().equals(end());
    }
    /**
     * isVertical function returns true if the line is vertical to the x axis.
     * @return true/false boolean
     */
    public boolean isVertical() {
        //return true if the value of the x start point is equal to the x of the end point
        return this.start().getX() == this.end().getX();
    }
    /**
     * plugX function gets x and returns the y value of him on this line.
     * the line is not vertical to the x axis!
     * @param x double - the value of the x.
     * @return x * m1 + c1 double - the y value of the x on this line.
     */
    public double plugX(double x) {
        double m1Y = this.start().getY() - this.end().getY();
        double m1X = this.start().getX() - this.end().getX();
        double m1 = m1Y / m1X; //the incline of this line
        double c1 = this.start().getY() - (m1 * this.start().getX());
        return (x * m1 + c1); //the y value
    }
    /**
     * intersectionWith function returns the intersection point if
     * the lines (this and the other line the function gets) intersect,
     * and null otherwise.
     * @param other Line - other line.
     * @return Point - the intersection point if there is and null otherwise
     */
    public Point intersectionWith(Line other) {
        //in case the segments are point and they are the same
        if (isTheSamePoint(other)) {
            //the intersection is the point
            return new Point(this.start().getX(), this.end().getY());
        }
        //very small distance for lower the accuracy of the distances
        double epsilon = Math.pow(10, -2);
        //in case just one them is point
        if ((!isPoint() && other.isPoint())) {
            //the distance between other (is a point) to the start point of this line
            double dis1 = other.start().distance(start());
            //the distance between other (is a point) to the end point of this line
            double dis2 = other.start().distance(end());
            //the length of the line
            double len = start().distance(end());
            //triangle inequality
            if (Math.abs(dis1 + dis2 - len) <= epsilon) {
                //return the point
                return isPoint() ? this.start() : other.start();
            }
            return null; //the point is not intersection with the line
        }
        if (isPoint() && !other.isPoint()) {
            //the distance between this (is a point) to the start point of other line
            double dis1 = start().distance(other.start());
            //the distance between this (is a point) to the end point of other line
            double dis2 = start().distance(other.end());
            //the length of the line
            double len = other.start().distance(other.end());
            //triangle inequality
            if (Math.abs(dis1 + dis2 - len) <= epsilon) {
                //return the point
                return isPoint() ? this.start() : other.start();
            }
            return null; //the point is not intersection with the line
        }
        //in case the both lines are verticals to the x axis
        if (isVertical() && other.isVertical()) {
            //in case the lines are parallels
            if (start().getX() != other.start().getX()) {
                return null;
            }
        }
        double m1Y = this.start().getY() - this.end().getY();
        double m2Y = other.start().getY() - other.end().getY();
        double m1X = this.start().getX() - this.end().getX();
        double m2X = other.start().getX() - other.end().getX();
        //in case the incline of the both lines are 0 or not exist
        if (m1Y == 0 && m2Y == 0) {
            //in case the both lines are not on the same line
            if (this.start().getY() != other.start().getY()) {
                return null;
            }
        }
        //in case one line is vertical to the x axis and the other line is vertical to the y axis
        if ((m1Y == 0 && m2X == 0) || (m2Y == 0 && m1X == 0)) {
            Line verY = m1Y == 0 ? this : other; //the line which is vertical to the y axis
            Line verX = m1X == 0 ? this : other; //the line which is vertical to the x axis
            // in case the both lines have intersection point
            if (verX.start().getX() >= Math.min(verY.start().getX(), verY.end().getX())
                    && verX.start().getX() <= Math.max(verY.start().getX(), verY.end().getX())
                && verY.start().getY() >= Math.min(verX.start().getY(), verX.end().getY())
                && verY.start().getY() <= Math.max(verX.start().getY(), verX.end().getY())) {
                return new Point(verX.start().getX(), verY.start().getY());
            }
            return null;
        }

        //in case 2 segments on the same line
        Point mid1 = this.middle();
        Point mid2 = other.middle();
        double len1 = mid1.distance(this.end()); //the half length of this segment
        double len2 = mid2.distance(other.end()); //the half length of other segment
        double centerDis = mid1.distance(mid2); //the distance between the two middles points
        //in case the both lines are intersection on one point
        if (Math.abs(len1 + len2 - centerDis)  <= epsilon) {
            //return (new Line(mid1, mid2).middle());
            if (start().equals(other.start()) || start().equals(other.end())) {
                return start();
            }
            //return end();
        }
        //in case the both lines are verticals to the x axis and the do not have one intersection point
        if (this.isVertical() && other.isVertical()) {
            return null;
        }
        //in case one of the lines is vertical and the other is not
        if (this.isVertical() || other.isVertical()) {
            Line ver = this.isVertical() ? this : other; //the vertical line
            Line notVer = !this.isVertical() ? this : other; //the not vertical line
            //the max value of x in the segment of the not vertical line
            double notVerMaxX = Math.max(notVer.start().getX(), notVer.end().getX());
            //the min value of x in the segment of the not vertical line
            double notVerMinX = Math.min(notVer.start().getX(), notVer.end().getX());
            //the max value of y in the segment of the vertical line
            double verMaxY = Math.max(ver.start().getY(), ver.end().getY());
            //the min value of y in the segment of the vertical line
            double verMinY = Math.min(ver.start().getY(), ver.end().getY());
            //the y value of the x of the vertical line
            double plugX = notVer.plugX(ver.start().getX());
            //in case the x and the y is in the range
            if (ver.start().getX() >= notVerMinX && ver.start().getX() <= notVerMaxX
                    && plugX >= verMinY && plugX <= verMaxY) {
                return new Point(ver.start().getX(), plugX);
            }
        }
        double m1 = m1Y / m1X; //the incline of this line
        double m2 = m2Y / m2X; //the incline of other line
        double c1 = this.start().getY() - (m1 * this.start().getX());
        double c2 = other.start().getY() - (m2 * other.start().getX());
        //in case the inclines are not the same
        if (m1 != m2) {
            double x = (c1 - c2) / (m2 - m1); //the value of the x of the intersection point
            double y = x * m1 + c1;
            //in case the the x is on the both segments
            if (x + epsilon >= Math.min(start().getX(), end().getX())
                    && x <= Math.max(start().getX(), end().getX() + epsilon)
                    && x + epsilon >= Math.min(other.start().getX(), other.end().getX())
                    && x <= Math.max(other.start().getX(), other.end().getX()) + epsilon
                    && y + epsilon >= Math.min(start().getY(), end().getY())
                    && y <= Math.max(start().getY(), end().getY() + epsilon)
                    && y + epsilon >= Math.min(other.start().getY(), other.end().getY())
                    && y <= Math.max(other.start().getY(), other.end().getY()) + epsilon) {
                return new Point(x, x * m1 + c1);
            }
        }
        return null; //there is no just one intersection point
    }
    /**
     * equals function receives another line and return true if
     * the lines are equal , false otherwise.
     * @param other Line another line.
     * @return true/false boolean.
     */
    public boolean equals(Line other) {
        //in case the the start point is equals to the start point of other and the end point
        // is equal to the end point of other.
        if (this.x1 == other.start().getX() && this.y1 == other.start().getY()
                && this.x2 == other.end().getX() && this.y2 == other.end().getY()) {
            return true;
        }
        //in case the the start point is equals to the end point of other and the end point
        // is equal to the start point of other.
        if (this.x1 == other.end().getX() && this.y1 == other.end().getY()
                && this.x2 == other.start().getX() && this.y2 == other.start().getY()) {
            return true;
        }
        return false; //the 2 lines are not equals.
    }
    /**
     * closestIntersectionToStartOfLine function receives rectangle.
     * return null if this line does not intersect with the rectangle
     * Otherwise, return the closest intersection point to the start of the line.
     * @param rect Rectangle
     * @return Point - the closest intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersection;
        //the list of the points in this line which intersection with the rectangle
        intersection = rect.intersectionPoints(this);
        //in case there are no intersection with the rectangle
        if (intersection.isEmpty()) {
            return null;
        }
        //the distance between the start point of the line to the first intersection with the rectangle
        double firstDistance = this.start().distance((Point) intersection.get(0));
        //in case there is only one intersection with the rectangle
        if (intersection.size() == 1) {
            return (Point) intersection.get(0);
        }
        //the distance between the start point of the line to the second intersection with the rectangle
        double secondDistance = this.start().distance((Point) intersection.get(1));
        //return the closest point to the start point of the line
        if (firstDistance > secondDistance) {
            return (Point) intersection.get(1);
        }
        return (Point) intersection.get(0);
    }
}
