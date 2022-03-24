import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MenuAnimation<T> implements Menu {
    private List<String> keyList;
    private List<String> messageList;
    private List<Object> valList;
    private Object status;
    private KeyboardSensor keyboard;
    private boolean stop = false;
    public MenuAnimation(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.messageList = new ArrayList<>();
        this.keyList = new ArrayList<>();
        this.valList = new ArrayList<>();
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        java.awt.Color color = new Color(200, 100, 2);
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
        d.drawText(320, d.getHeight() / 2 - 80, "Arkanoid", 50);
        for (int i = 0; i < keyList.size(); i++) {
            d.drawText(250, d.getHeight() / 2 + (60 * i), "Press " + keyList.get(i)
                    + " to " + messageList.get(i), 32);
        }
        for (int i = 0; i < keyList.size(); i++) {
           if (keyboard.isPressed(keyList.get(i))) {
               this.status = valList.get(i);
               this.stop = true;
           }
        }

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
    public void setStop() {
        this.stop = false;
    }

    @Override
    public void addSelection(String key, String message, Object returnVal) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.valList.add(returnVal);
    }

    @Override
    public Object getStatus() {
        return this.status;
    }
}
