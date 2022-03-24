import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

public class showTheScore extends KeyPressStoppableAnimation {
    private boolean stop;
    public showTheScore(KeyboardSensor sensor, String key, Animation animation) {
        super(sensor, key, animation);
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        java.awt.Color color = new Color(100, 100, 2);
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
        try {
            OutputStreamWriter os = null;
            Path file = Path.of("src//highscores.txt");
            FileReader fr = new FileReader(file.toFile());  //Creation of File Reader object
            BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
            String s = new String();
            while ((s = br.readLine()) != null) {  //Reading Content from the file
                String[] word = s.split(":");
                d.drawText(160, d.getHeight() / 2 - 80, "the highest score is: " + word[1], 50);
            }
            br.close();
        } catch (Exception e) {
            ;
        }
        super.doOneFrame(d);
    }
    public void setStop() {
        super.setStop();
    }

    @Override
    public boolean shouldStop() {
        return super.shouldStop();
    }
}
