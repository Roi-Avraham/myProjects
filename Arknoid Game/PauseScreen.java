import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import java.awt.Color;
/**
 * @author Roi Avraham id:318778081.
 * PauseScreen is the screen that show up when the user stops the game.
 */
public class PauseScreen extends  KeyPressStoppableAnimation {
    /**
     * PauseScreen is the constructor of this class.
     * @param sensor KeyboardSensor.
     * @param key String.
     * @param animation Animation.
     */
    public PauseScreen(KeyboardSensor sensor, String key, Animation animation) {
        super(sensor, key, animation);
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        java.awt.Color color = new Color(160, 240, 50);
        d.setColor(color); //draw the ball with its color
        //draw the borders of the rectangle.
        d.fillRectangle((int) rectangle.getUpperLeft().getX(),
                (int) rectangle.getUpperLeft().getY()
                        - (int) rectangle.getHeight(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
        d.setColor(Color.BLACK); //draw the ball with its color
        //draw the rectangle
        d.drawRectangle((int) rectangle.getUpperLeft().getX(),
                (int) rectangle.getUpperLeft().getY()
                        - (int) rectangle.getHeight(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
        d.setColor(Color.black);
        d.drawCircle(410, 300, 250);
        d.drawText(180, d.getHeight() / 2, "paused -- press space to continue", 32);
        super.doOneFrame(d);
    }
    @Override
    public boolean shouldStop() {
        return super.shouldStop();
    }
}
