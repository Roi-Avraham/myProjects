import biuoop.DrawSurface;

public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop = false;
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
    }
    public void doOneFrame(DrawSurface d) {
        gameScreen.drawAllOn(d);
        d.drawText(10, d.getHeight() / 2, "" + countFrom, 32);
        d.drawText(10, d.getHeight() / 2, "" + (countFrom - 1), 32);
        //the background of the game
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        sleeper.sleepFor((long) (2000 * numOfSeconds));
        stop = true;
    }
    public boolean shouldStop() {
        return stop;
    }
}
