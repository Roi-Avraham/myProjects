import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author Roi Avraham id:318778081.
 * this class is the 2 level in the game.
 */
public class WideEasy implements LevelInformation {
    private int numOfBalls;
    private Ball[] balls;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private Sprite sprite;
    private List<Block> blocks;
    private Counter counterBlocks;
    private Counter counterBalls;
    private Color color;
    /**
     * WideEasy is the constructor of this class.
     */
    public WideEasy() {
        numOfBalls = 10;
        balls = new Ball[numOfBalls];
        velocities = new ArrayList<Velocity>();
        blocks = new ArrayList<Block>();
        counterBlocks = new Counter(15);
        paddleSpeed = 1;
        paddleWidth = 400;
        color = Color.WHITE;
        counterBalls = new Counter(numOfBalls);
    }
    @Override
    public int numberOfBalls() {
        return numOfBalls;
    }
    @Override
    public List<Velocity> initialBallVelocities() {
        for (int i = 0; i < numOfBalls; i++) {
            velocities.add(new Velocity(3, 4));
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
        return "Wide Easy";
    }
    @Override
    public Sprite getBackground() {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        java.awt.Color colorBackGround = new Color(140, 140, 240);
        Block backGround = new Block(rectangle, colorBackGround);
        return backGround;
    }
    @Override
    public List<Block> blocks() {
        for (int i = 0; i < 2; i++) {
            Point uper = new Point(20 + (50 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.red);
            blocks.add(block);
        }
        for (int i = 0; i < 2; i++) {
            Point uper = new Point(120 + (50 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.orange);
            blocks.add(block);
        }
        for (int i = 0; i < 2; i++) {
            Point uper = new Point(220 + (50 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.yellow);
            blocks.add(block);
        }
        for (int i = 0; i < 3; i++) {
            Point uper = new Point(320 + (50 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.green);
            blocks.add(block);
        }
        for (int i = 0; i < 2; i++) {
            Point uper = new Point(470 + (50 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.blue);
            blocks.add(block);
        }
        for (int i = 0; i < 2; i++) {
            Point uper = new Point(570 + (50 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 50, 30);
            Block block = new Block(rectangle, Color.pink);
            blocks.add(block);
        }
        for (int i = 0; i < 2; i++) {
            Point uper = new Point(670 + (55 * i), 250);
            Rectangle rectangle = new Rectangle(uper, 55, 30);
            Block block = new Block(rectangle, Color.CYAN);
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
    public void initializeLevel(GameLevel gameLevel) {
        Point uperLeftDown = new Point(0, 40); //the upper left point of the down border
        Point uperLeftRight = new Point(780, 600); //the upper left point of the right border
        Point uperLeftLeft = new Point(0, 600); //the upper left point of the left border
        Point uperLeftUp = new Point(0, 600); //the upper left point of the up border
        Point uperLeftPaddle = new Point(140, 580); //the upper left point of the paddle
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
        gameLevel.setBallRemover(ballRemover);
        int numOfBlocks =  blocks().size();
        for (int i = 0; i < numOfBlocks; i++) {
            blocks().get(i).addToGame(gameLevel);
            blocks().get(i).addHitListener(gameLevel.getBlockRemover());
            blocks().get(i).addHitListener(gameLevel.getScoreTrackingListener());
        }
        Ball[] ballArray = new Ball[numberOfBalls()]; //the array of the ball
        for (int i = 0; i < numberOfBalls(); i++) {
            //the position of the center in the x axis
            double x1 = ThreadLocalRandom.current().nextDouble(100, 700);
            //the position of the center in the y axis
            double y1 = ThreadLocalRandom.current().nextDouble(325, 550);
            //the radius of the ball
            int size = ThreadLocalRandom.current().nextInt(3, 6);
            Point center = new Point(x1, y1);
            ballArray[i] = new Ball(center, size, Color.black, gameLevel.getGameEnvironment());
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
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.orange);
        surface.fillCircle(200, 150, 100);
        surface.setColor(Color.yellow);
        surface.fillCircle(200, 150, 50);
        surface.setColor(Color.orange);
        for (int i = 0; i <= 50; i++) {
            surface.drawLine(200, 170, 350 + i * 10, 300);
            surface.drawLine(200, 170, 150 + i * 10, 300);
        }
    }
}
