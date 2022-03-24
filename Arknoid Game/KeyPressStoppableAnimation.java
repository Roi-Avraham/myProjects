import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * @author Roi Avraham id:318778081.
 * KeyPressStoppableAnimation reasponsable of the keys that stops the game.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private Animation animation;
    private String key;
    private boolean stop;
    private boolean isAlreadyPressed;
    /**
     * KeyPressStoppableAnimation is the constructor of this class.
     * @param animation Animation.
     * @param key String.
     * @param sensor KeyboardSensor
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }
    @Override
    public void doOneFrame(DrawSurface d) {

        if (!this.sensor.isPressed(KeyboardSensor.SPACE_KEY)) {
            setIsAlreadyPressed(false);
        }
        if (this.sensor.isPressed(KeyboardSensor.SPACE_KEY) && isAlreadyPressed) {
            return;
        } else if (this.sensor.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }
    /**
     * getStop return stop.
     * @return stop.
     */
    public boolean getStop() {
        return this.stop;
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
    public void setStop() {
        this.stop = false;
    }
    /**
     * getIsAlreadyPressed return isAlreadyPressed.
     * @return isAlreadyPressed.
     */
    public boolean getIsAlreadyPressed() {
        return isAlreadyPressed;
    }
    /**
     * setIsAlreadyPressed change and return isAlreadyPressed.
     * @param b true/false
     * @return isAlreadyPressed.
     */
    public boolean setIsAlreadyPressed(boolean b) {
        this.isAlreadyPressed = b;
        return this.isAlreadyPressed;
    }
    public Animation getAnimation() {
        return this.animation;
    }
    public String getKey() {
        return this.key;
    }
}
