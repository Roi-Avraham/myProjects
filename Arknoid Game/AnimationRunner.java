import biuoop.DrawSurface;
import biuoop.GUI;
/**
 * @author Roi Avraham id:318778081.
 * this interface runs a animation.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;
    private GameLevel game;
    /**
     * AnimationRunner is first constructor of this class.
     * @param gui Gui.
     * @param framesPerSecond int.
     * @param sleeper biuoop.Sleeper.
     * @param game GameLevel.
     */
    public AnimationRunner(GUI gui, int framesPerSecond, biuoop.Sleeper sleeper, GameLevel game) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = sleeper;
        this.game = game;
    }
    /**
     * AnimationRunner is second constructor of this class.
     * @param gui Gui.
     * @param framesPerSecond int.
     * @param sleeper biuoop.Sleeper.
     */
    public AnimationRunner(GUI gui, int framesPerSecond, biuoop.Sleeper sleeper) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = sleeper;
    }
    /**
     * getGui returns the gui of this class.
     * @return gui.
     */
    public GUI getGui() {
        return this.gui;
    }
    /**
     * getFramesPerSecond returns the FramesPerSecond of this class.
     * @return framesPerSecond.
     */
    public int getFramesPerSecond() {
        return framesPerSecond;
    }
    /**
     * getSleeper returns the sleeper of this class.
     * @return sleeper.
     */
    public biuoop.Sleeper getSleeper() {
        return sleeper;
    }
    /**
     * runs the given animation.
     * @param animation Animation.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d);
//            if (animation.shouldStop()) {
//               //this.game.setRunner(this);
//               //this.game.getRunner().run(game);
//            }

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
