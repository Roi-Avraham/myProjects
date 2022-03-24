import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import java.awt.Color;
import java.io.*;
import java.nio.file.Path;

/**
 * @author Roi Avraham id:318778081.
 * this class is frame when the game ends.
 */
public class EndScreen extends KeyPressStoppableAnimation {
    private GameLevel gameLevel;
    /**
     * EndScreen is the constructor of this class.
     * @param key KeyboardSensor.
     * @param animation Animation.
     * @param gameLevel GameLevel.
     * @param sensor KeyboardSensor.
     */
    public EndScreen(KeyboardSensor sensor, String key, Animation animation, GameLevel gameLevel) {
        super(sensor, key, animation);
        this.gameLevel = gameLevel;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Point uper = new Point(0, 600);
        Rectangle rectangle = new Rectangle(uper, 800, 600);
        java.awt.Color color = new Color(40, 40, 100);
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
        if (this.gameLevel.getLevelInformation().numberOfBallsToRemove() == 0) {
            d.setColor(Color.RED);
            d.drawText(200, d.getHeight() / 2, "Game Over. Your score is "
                    + gameLevel.getScore().getValue(), 32);
        } else {
            d.setColor(Color.GREEN);
            d.drawText(200, d.getHeight() / 2, "You Win! Your score is "
                    + gameLevel.getScore().getValue(), 32);
        }

        super.doOneFrame(d);
        if (super.getStop()) {
            File tempFile;
            tempFile = new File("src//highscores.txt");
            boolean exists = tempFile.exists();
            if (!exists) {
                File file = new File("src//highscores.txt");
                try {
                    file.createNewFile();
                    OutputStreamWriter os = null;
                    os = new OutputStreamWriter(new FileOutputStream(file));
                    os.write("The highest score so far is:" + gameLevel.getScore().getValue());
                    os.close();
                } catch (Exception e) {
                    ;
                }
            } else {
                try {
                    OutputStreamWriter os = null;
                    Path file = Path.of("src//highscores.txt");
                    FileReader fr = new FileReader(file.toFile());  //Creation of File Reader object
                    BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                    String s = new String();
                    while ((s = br.readLine()) != null)  {  //Reading Content from the file
                        String[] word = s.split(":");
                        System.out.println(word[1]);
                        if (Integer.parseInt(word[1]) < gameLevel.getScore().getValue()) {
                            os = new OutputStreamWriter(new FileOutputStream(file.toFile()));
                            os.write("The highest score so far is:" + gameLevel.getScore().getValue());
                            os.close();
                        }
                        break;
                    }
                } catch (Exception e) {
                    ;
                }
            }
        }
    }
    @Override
    public boolean shouldStop() {
        return getStop();
    }
}
