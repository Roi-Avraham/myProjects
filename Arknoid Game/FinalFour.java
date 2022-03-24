import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author Roi Avraham id:318778081.
 * this class is the final and hardest level of the game.
 */
public class FinalFour implements LevelInformation {
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
     * FinalFour is the constructor of this class.
     */
    public FinalFour() {
        numOfBalls = 3;
        balls = new Ball[numOfBalls];
        velocities = new ArrayList<Velocity>();
        blocks = new ArrayList<Block>();
        counterBlocks = new Counter(105);
        paddleSpeed = 10;
        paddleWidth = 120;
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
            velocities.add(new Velocity(5 + i, 6 + i));
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
        return "Final Four";
    }
    @Override
    public Sprite getBackground() {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        color = new Color(51, 153, 255);
        Block backGround = new Block(rectangle, color);
        return backGround;
    }
    @Override
    public List<Block> blocks() {
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 150);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
            Block block = new Block(rectangle, Color.gray);
            blocks.add(block);
        }
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 180);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
            Block block = new Block(rectangle, Color.red);
            blocks.add(block);
        }
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 210);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
            Block block = new Block(rectangle, Color.orange);
            blocks.add(block);
        }
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 240);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
            Block block = new Block(rectangle, Color.green);
            blocks.add(block);
        }
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 270);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
            Block block = new Block(rectangle, Color.white);
            blocks.add(block);
        }
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 300);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
            Block block = new Block(rectangle, Color.pink);
            blocks.add(block);
        }
        for (int i = 0; i < 15; i++) {
            Point uper = new Point(20 + (50.66 * i), 330);
            Rectangle rectangle = new Rectangle(uper, 50.66, 30);
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
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.gray);
        surface.fillCircle(110, 420, 30);
        surface.fillCircle(115, 410, 30);
        surface.fillCircle(150, 410, 30);
        surface.fillCircle(140, 430, 30);
        surface.fillCircle(160, 440, 30);
        surface.setColor(Color.white);
        for (int i = 0; i <= 15; i++) {
            surface.drawLine(130 + 2 * i, 460 + i, 110 + i * 5, 580);
        }


        surface.setColor(Color.gray);
        surface.fillCircle(610, 420, 30);
        surface.fillCircle(615, 410, 30);
        surface.fillCircle(650, 410, 30);
        surface.fillCircle(640, 430, 30);
        surface.fillCircle(660, 440, 30);
        surface.setColor(Color.white);
        for (int i = 0; i <= 15; i++) {
            surface.drawLine(640, 470, 640 + i * 5, 580);
        }
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
            double x1 = ThreadLocalRandom.current().nextDouble(300, 500);
            //the position of the center in the y axis
            double y1 = ThreadLocalRandom.current().nextDouble(350, 550);
            //the radius of the ball
            int size = ThreadLocalRandom.current().nextInt(4, 6);
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
