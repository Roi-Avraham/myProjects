import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author Roi Avraham id:318778081.
 * Green3 is the 3 level of the game.
 */
public class Green3 implements LevelInformation {
    private int numOfBalls;
    private Ball[] balls;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private Sprite sprite;
    private List<Block> blocks;
    private Counter counterBlocks;
    private Color color;
    private Counter counterBalls;
    /**
     * Green3 is the constructor of this class.
     */
    public Green3() {
        numOfBalls = 2;
        balls = new Ball[numOfBalls];
        velocities = new ArrayList<Velocity>();
        blocks = new ArrayList<Block>();
        counterBlocks = new Counter(40);
        paddleSpeed = 10;
        paddleWidth = 120;
        color = Color.green;
        counterBalls = new Counter(numOfBalls);
    }
    @Override
    public int numberOfBalls() {
        return numOfBalls;
    }
    @Override
    public List<Velocity> initialBallVelocities() {
        for (int i = 0; i < numOfBalls; i++) {
            velocities.add(new Velocity(0, 6));
        }
        return velocities;
    }
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }
    @Override
    public String levelName() {
        return "Green 3";
    }
    @Override
    public Sprite getBackground() {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        color = new Color(0, 102, 0);
        Block backGround = new Block(rectangle, color);
        return backGround;
    }
    @Override
    public List<Block> blocks() {
        for (int i = 0; i < 6; i++) {
            Point uper = new Point(480 + (50 * i), 290);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.WHITE);
            blocks.add(block);
        }
        for (int i = 0; i < 7; i++) {
            Point uper = new Point(430 + (50 * i), 260);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.blue);
            blocks.add(block);
        }
        for (int i = 0; i < 8; i++) {
            Point uper = new Point(380 + (50 * i), 230);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.orange);
            blocks.add(block);
        }
        for (int i = 0; i < 9; i++) {
            Point uper = new Point(330 + (50 * i), 200);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.red);
            blocks.add(block);
        }
        for (int i = 0; i < 10; i++) {
            Point uper = new Point(280 + (50 * i), 170);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.gray);
            blocks.add(block);
        }

        return blocks;
    }
    @Override
    public int numberOfBlocksToRemove() {
        return counterBlocks.getValue();
    }
    @Override
    public int numberOfBallsToRemove() {
        return counterBalls.getValue();
    }
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.white);
        surface.fillRectangle(100, 400, 125, 200);
        surface.setColor(Color.black);
        surface.fillRectangle(100, 400, 15, 200);
        surface.fillRectangle(225, 400, 15, 200);
        for (int i = 0; i < 18; i++) {
            surface.fillRectangle(100, 400 + (30 * i), 125, 15);
        }
        for (int i = 0; i < 5; i++) {
            surface.fillRectangle(100 + (30 * i), 400, 15, 200);
        }
        surface.setColor(Color.gray);
        surface.fillRectangle(150, 350, 30, 50);
        surface.setColor(Color.DARK_GRAY);
        surface.fillRectangle(160, 200, 10, 150);
        surface.setColor(Color.ORANGE);
        surface.fillCircle(165, 185, 15);
        surface.setColor(Color.red);
        surface.fillCircle(165, 185, 10);
        surface.setColor(Color.WHITE);
        surface.fillCircle(165, 185, 5);
    }

    @Override
    public void initializeLevel(GameLevel gameLevel) {
        Point uperLeftDown = new Point(0, 40); //the upper left point of the down border
        Point uperLeftRight = new Point(780, 600); //the upper left point of the right border
        Point uperLeftLeft = new Point(0, 600); //the upper left point of the left border
        Point uperLeftUp = new Point(0, 600); //the upper left point of the up border
        Point uperLeftPaddle = new Point(320, 580); //the upper left point of the paddle
        //all the blocks which are the borders
        Rectangle rectangle1 = new Rectangle(uperLeftDown, 800, 40);
        Rectangle rectangle2 = new Rectangle(uperLeftRight, 20, 600);
        Rectangle rectangle3 = new Rectangle(uperLeftLeft, 800, 20);
        Rectangle rectangle4 = new Rectangle(uperLeftUp, 20, 600);
        Block b1 = new Block(rectangle1, Color.gray);
        //b1.addHitListener(print);
        Block b2 = new Block(rectangle2, Color.gray);
        //b2.addHitListener(print);
        Block b3 = new Block(rectangle3, Color.gray);
        //b3.addHitListener(print);
        Block b4 = new Block(rectangle4, Color.gray);
        b1.addToGame(gameLevel);
        b2.addToGame(gameLevel);
        b3.addToGame(gameLevel);
        b4.addToGame(gameLevel);
        BallRemover ballRemover = new BallRemover(gameLevel, counterBalls, b3);
        int numOfBlocks =  blocks().size();
        for (int i = 0; i < numOfBlocks; i++) {
            blocks().get(i).addToGame(gameLevel);
            blocks().get(i).addHitListener(gameLevel.getBlockRemover());
            blocks().get(i).addHitListener(gameLevel.getScoreTrackingListener());
        }
        Ball[] ballArray = new Ball[numberOfBalls()]; //the array of the ball
        for (int i = 0; i < numberOfBalls(); i++) {
            //the position of the center in the x axis
            double x1 = ThreadLocalRandom.current().nextDouble(320, 330);
            //the position of the center in the y axis
            double y1 = ThreadLocalRandom.current().nextDouble(325, 550);
            //the radius of the ball
            int size = ThreadLocalRandom.current().nextInt(3, 6);
            Point center = new Point(x1, y1);
            ballArray[i] = new Ball(center, size, Color.white, gameLevel.getGameEnvironment());
            Velocity v = initialBallVelocities().get(i);
            ballArray[i].setVelocity(v);
            ballArray[i].addToGame(gameLevel); //add the ball into the game
            ballArray[i].addHitListener(ballRemover);
        }

        //create the paddle
        Rectangle rectangle5 = new Rectangle(uperLeftPaddle, paddleWidth(), 15);
        Paddle paddle = new Paddle(rectangle5, Color.orange, gameLevel.getKeyboard());
        paddle.addToGame(gameLevel); //add the paddle into the game


        Point uperLeftScore = new Point(0, 20);
        Rectangle scoreRec = new Rectangle(uperLeftScore, 800, 20);
        ScoreIndicator indicator = new ScoreIndicator(scoreRec, Color.white, gameLevel);
        indicator.addToGame(gameLevel);
    }
}
