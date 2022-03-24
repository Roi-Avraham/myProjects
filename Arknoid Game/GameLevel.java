import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
/**
 * @author Roi Avraham id:318778081.
 * this class runs each level in the game.
 */
public class GameLevel implements Animation {
    //the list of all the spirits in the game
    private SpriteCollection sprites;
    //the list of all the collidables in the game
    private java.util.List<Collidable> collisions;
    //the environment of this game
    private GameEnvironment environment;
    //the gui of this game
    private GUI gui;
    private Counter counter;
    private Counter counterBalls;
    private Counter score;
    private BlockRemover blockRemover;
    private ScoreTrackingListener scoreTrackingListener;
    private AnimationRunner runner;
    private boolean running;
    private biuoop.Sleeper sleeper;
    private biuoop.KeyboardSensor keyboard;
    private LevelInformation levelInformation;
    private BallRemover ballRemover;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    /**
     * GameLevel is the constructor of this class.
     * @param levelInformation LevelInformation.
     * @param score Counter.
     * @param ks KeyboardSensor.
     * @param ar AnimationRunner.
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner ar, KeyboardSensor ks,
                     Counter score) {
        this.levelInformation = levelInformation;
        this.sprites = new SpriteCollection();
        this.collisions = new ArrayList<Collidable>();
        this.environment = new GameEnvironment(collisions);
        this.gui = ar.getGui();
        this.counter = new Counter(levelInformation.numberOfBlocksToRemove());
        this.counterBalls = new Counter(levelInformation.numberOfBallsToRemove());
        this.blockRemover = new BlockRemover(this, counter);
        this.scoreTrackingListener = new ScoreTrackingListener(score);
        this.running = true;
        this.sleeper = new biuoop.Sleeper();
        this.keyboard = gui.getKeyboardSensor();
        this.ballRemover = new BallRemover(this, counterBalls);
        this.animationRunner = new AnimationRunner(ar.getGui(), ar.getFramesPerSecond(), ar.getSleeper(), this);
        this.runner = this.animationRunner;
        this.keyboardSensor = ks;
        this.score = score;
    }
    /**
     * getCounter return the counter of the blocks.
     * @return counter int.
     */
    public Counter getCounter() {
        return counter;
    }
    /**
     * getScoreTrackingListener return the ScoreTracking.
     * @return scoreTrackingListener.
     */
    public ScoreTrackingListener getScoreTrackingListener() {
        return scoreTrackingListener;
    }
    /**
     * getBlockRemover return the blockRemover.
     * @return blockRemover.
     */
    public BlockRemover getBlockRemover() {
        return blockRemover;
    }
    /**
     *  getLevelInformation return the levelInformation.
     * @return levelInformation.
     */
    public LevelInformation getLevelInformation() {
        return levelInformation;
    }
    /**
     *  getGameEnvironment return the environment.
     * @return environment.
     */
    public GameEnvironment getGameEnvironment() {
        return environment;
    }
    /**
     *  getKeyboard return the keyboard.
     * @return keyboard.
     */
    public biuoop.KeyboardSensor getKeyboard() {
        return keyboard;
    }
    /**
     *  setBlockRemover change the blockRemover.
     * @param blockRe BlockRemover
     */
    public void setBlockRemover(BlockRemover blockRe) {
        this.blockRemover = blockRe;
    }
    /**
     *  setBallRemover change the BallRemover.
     * @param ballRe BallRemover.
     */
    public void setBallRemover(BallRemover ballRe) {
        this.ballRemover = ballRemover;
    }
    /**
     * getRunner return the runner.
     * @return runner.
     */
    public AnimationRunner getRunner() {
        return this.runner;
    }
    /**
     * setRunner change the runner.
     * @param runner1 AnimationRunner.
     */
    public void setRunner(AnimationRunner runner1) {
        this.runner = runner1;
    }
    /**
     * getGui return the gui.
     * @return gui.
     */
    public GUI getGui() {
        return this.gui;
    }
    /**
     * getScore return the score.
     * @return score.
     */
    public Counter getScore() {
        return score;
    }
    /**
     * addCollidable gets collidable and add it to the list.
     * @param c Collidable
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }
    /**
     * addSprite gets spirit and add it to the list.
     * @param s Spirit
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }
    /**
     * removeCollidable remove collidable from the list.
     * @param c Collidable.
     */
    public void removeCollidable(Collidable c) {
        environment.getCollisions().remove(c);
    }
    /**
     * removeSprite remove sprite from the list.
     * @param s Sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.getSprites().remove(s);
    }
    /**
     * drawRow gets start point and color and draw row of blocks in the game.
     * @param start Point - the start point of the row.
     * @param len int - the number of the blocks in the row.
     * @param color Color - the color of all the blocks in the row.
     * @param print PrintingHitListener - print to the console that block was hit.
     */
    public void drawRow(Point start, int len, Color color, PrintingHitListener print) {
        java.util.List<Point> points = new ArrayList<Point>();
        points.add(start); //add the start point into the list
        //the first block in the row
        Rectangle rectangle = new Rectangle(start, 50, 25);
        Block b = new Block(rectangle, color);
        b.addToGame(this); //add the block into the game
        b.addHitListener(blockRemover);
        b.addHitListener(scoreTrackingListener);
        //creating all the blocks in the row
        for (int i = 1; i < len; i++) {
            Point uperLeftNew = new Point(points.get(i - 1).getX() - 50,
                    points.get(i - 1).getY());
            points.add(uperLeftNew); //add the point into the list
            //create the block
            Rectangle rectangleNew = new Rectangle(uperLeftNew, 50, 25);
            Block newBlock = new Block(rectangleNew, color);
            newBlock.addToGame(this); //add the block into the game
            newBlock.addHitListener(blockRemover);
            newBlock.addHitListener(scoreTrackingListener);
        }
    }
    /**
     * initialize the level.
     */
    public void initialize() {
        levelInformation.initializeLevel(this);
        Point uperLeftScore = new Point(0, 20);
        Rectangle scoreRec = new Rectangle(uperLeftScore, 800, 20);
        ScoreIndicator indicator = new ScoreIndicator(scoreRec, Color.white, this);
        indicator.addToGame(this);
    }
    /**
     * run the game -- start the animation loop.
     */
    public void run() {
        //this.runner.run(new CountdownAnimation(2, 3, sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }
    /**
     * shouldStop return true if the level ends and false otherwise.
     * @return boolean true/false.
     */
    public boolean shouldStop() {
        return !this.running;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        //the background of the game

        levelInformation.getBackground().drawOn(d);
        levelInformation.drawOn(d);


        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard, " ", this));
        }

        if (counter.getValue() == 0) {
            score.increase(100);
            this.sprites.drawAllOn(d);
            sleeper.sleepFor(500);
            this.running = false;
        }


        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        if (levelInformation.numberOfBallsToRemove() == 0) {
            this.running = false;
        }

    }
}
