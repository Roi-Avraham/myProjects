import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author Roi Avraham id:318778081.
 * this class runs the game.
 */
public class Ass6Game {
    /**
     * this main is for creating and showing the game.
     * @param args - the args from the command line
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(keyboard);
        Sleeper sleeper = new Sleeper();
        AnimationRunner animationRunner = new AnimationRunner(gui, 60, sleeper);
        showTheScore showTheScore = new showTheScore(keyboard, "h", menu);
        quitTheGame quitTheGame = new quitTheGame(gui);
        menu.addSelection("s", "play a game", new playTheGame(args, animationRunner, keyboard, menu));
        menu.addSelection("h", "Hi score", new ShowHiScoresTask(animationRunner, showTheScore));
        menu.addSelection("q", "quit the game", new ShowHiScoresTask(animationRunner, quitTheGame));
        while (true) {
            animationRunner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            showTheScore.setStop();
            menu.setStop();
        }
    }
}
