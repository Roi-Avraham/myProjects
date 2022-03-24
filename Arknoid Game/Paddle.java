import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * @author Roi Avraham id:318778081.
 * this class describe the paddle in the game.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddle; //the shape of the paddle
    private java.awt.Color color; //the color of the paddle
    /**
     * Paddle is the constructor of this class.
     * @param paddle Rectangle
     * @param color java.awt.Color
     * @param keyboard biuoop.KeyboardSensor
     */
    public Paddle(Rectangle paddle, java.awt.Color color, biuoop.KeyboardSensor keyboard) {
        this.paddle = paddle;
        this.color = color;
        this.keyboard = keyboard;
    }
    /**
     * moveLeft moves the paddle to the left.
     */
    public void moveLeft() {
        //in case the paddle gets to the left border
        if ((int) paddle.getUpperLeft().getX() - 20 <= 0) {
            return;
        }
        //move the paddle to the left
        Point newUperLeft = new Point(paddle.getUpperLeft().getX() - 10, paddle.getUpperLeft().getY());
        paddle = new Rectangle(newUperLeft, paddle.getWidth(), paddle.getHeight());
    }
    /**
     * moveRight moves the paddle to the left.
     */
    public void moveRight() {
        //in case the paddle gets to the right border
        if (paddle.getUpperLeft().getX() - 10 >= 770 - paddle.getWidth()) {
            return;
        }
        //move the paddle to the right
        Point newUperLeft = new Point(paddle.getUpperLeft().getX() + 10, paddle.getUpperLeft().getY());
        paddle = new Rectangle(newUperLeft, paddle.getWidth(), paddle.getHeight());
    }
    @Override
    public void timePassed() {
        //in case the user press the left arrow in the keyboard
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            //move the paddle to the left
            moveLeft();
        }
        //in case the user press the right arrow in the keyboard
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            //move the paddle to the right
            moveRight();
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color); //draw the ball with its color
        //draw the paddle
        d.fillRectangle((int) paddle.getUpperLeft().getX(),
                (int) paddle.getUpperLeft().getY()
                        - (int) paddle.getHeight(), (int) paddle.getWidth(), (int) paddle.getHeight());
        d.setColor(Color.BLACK); //draw the ball with its color
        //draw the rectangle
        d.drawRectangle((int) paddle.getUpperLeft().getX(),
                (int) paddle.getUpperLeft().getY()
                        - (int) paddle.getHeight(), (int) paddle.getWidth(), (int) paddle.getHeight());
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return paddle;
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //the region of the paddle
        double segment = (paddle.getWidth() / 5);
        Line l = new Line(collisionPoint, collisionPoint);
        //the absolute speed
        double absSpeed = Math.sqrt((currentVelocity.getDx() * currentVelocity.getDx())
                + (currentVelocity.getDy() * currentVelocity.getDy()));
        //in case the ball hit the left edge of the paddle
        if (paddle.edgeLeft().intersectionWith(l) != null) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            //in case the ball hit the right edge of the paddle
        } else if (paddle.edgeRight().intersectionWith(l) != null) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            //in case the ball hit the up edge
        } else if (paddle.edgeDown().intersectionWith(l) != null) {
            double collisionPointX = collisionPoint.getX();
            double x = paddle.getUpperLeft().getX();
            if (collisionPointX < x + segment) {
                return Velocity.fromAngleAndSpeed(300, absSpeed);
            } else if (collisionPointX < x + 2 * segment) {
                return Velocity.fromAngleAndSpeed(330, absSpeed);
            } else if (collisionPointX < x + 3 * segment) {
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (collisionPointX < x + 4 * segment) {
                return Velocity.fromAngleAndSpeed(30, absSpeed);
            } else {
                return Velocity.fromAngleAndSpeed(60, absSpeed);
            }
        }
        return currentVelocity;
    }
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
//    @Override
//    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
//        //the region of the paddle
//        double segment = (paddle.getWidth() / 5);
//        Line l = new Line(collisionPoint, collisionPoint);
//        //the absolute speed
//        double absSpeed = Math.sqrt((currentVelocity.getDx() * currentVelocity.getDx())
//                + (currentVelocity.getDy() * currentVelocity.getDy()));
//        //in case the ball hit the left edge of the paddle
//        if (paddle.edgeLeft().intersectionWith(l) != null) {
//            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
//            //in case the ball hit the right edge of the paddle
//        } else if (paddle.edgeRight().intersectionWith(l) != null) {
//            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
//            //in case the ball hit the up edge
//        } else if (paddle.edgeDown().intersectionWith(l) != null) {
//            double collisionPointX = collisionPoint.getX();
//            double x = paddle.getUpperLeft().getX();
//            if (collisionPointX < x + segment) {
//                return Velocity.fromAngleAndSpeed(300, absSpeed);
//            } else if (collisionPointX < x + 2 * segment) {
//                return Velocity.fromAngleAndSpeed(330, absSpeed);
//            } else if (collisionPointX < x + 3 * segment) {
//                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
//            } else if (collisionPointX < x + 4 * segment) {
//                return Velocity.fromAngleAndSpeed(30, absSpeed);
//            } else {
//                return Velocity.fromAngleAndSpeed(60, absSpeed);
//            }
//        }
//        return currentVelocity;
//    }
}


