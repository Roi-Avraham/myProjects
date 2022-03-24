import biuoop.DrawSurface;
import java.awt.Color;
/**
 * @author Roi Avraham id:318778081.
 * this class describe the score
 */
public class ScoreIndicator implements Sprite {
    private Block indicator;
    private GameLevel game;
    /**
     * ScoreIndicator is the constructor of this class.
     * @param game Game.
     * @param rectangle Rectangle.
     * @param color Color.
     */
    public ScoreIndicator(Rectangle rectangle, Color color, GameLevel game) {
        indicator = new Block(rectangle, color);
        this.game = game;
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(indicator.getColor()); //draw the ball with its color
        //draw the borders of the rectangle.
        d.fillRectangle((int) indicator.getRectangle().getUpperLeft().getX(),
                (int) indicator.getRectangle().getUpperLeft().getY()
                        - (int) indicator.getRectangle().getHeight(), (int) indicator.getRectangle().getWidth(),
                   (int) indicator.getRectangle().getHeight());
        d.setColor(Color.BLACK); //draw the ball with its color
        //draw the rectangle
        d.drawRectangle((int) indicator.getRectangle().getUpperLeft().getX(),
                (int) indicator.getRectangle().getUpperLeft().getY()
                        - (int) indicator.getRectangle().getHeight(), (int) indicator.getRectangle().getWidth(),
                (int) indicator.getRectangle().getHeight());
        d.drawText(400, 15, "Score:" + game.getScore().getValue(), 15);
        d.drawText(100, 15, "Lives:" + 7, 15);
        d.drawText(600, 15, "Level Name:" + game.getLevelInformation().levelName(), 15);

    }
    @Override
    public void timePassed() {
        return;
    }
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
