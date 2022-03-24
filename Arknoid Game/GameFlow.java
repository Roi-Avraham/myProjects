import biuoop.KeyboardSensor;
import java.util.List;
/**
 * @author Roi Avraham id:318778081.
 * GameFlow is the class that runs all the levels of the game.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    /**
     * GameFlow is the constructor of this class.
     * @param ar AnimationRunner.
     * @param ks KeyboardSensor.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;

    }
    /**
     * runLevels runs all the levels of the game.
     * @param levels List<LevelInformation>.
     */
    public void runLevels(List<LevelInformation> levels) {
        Counter score = new Counter(0);
        boolean lastLevel = false;
        for (LevelInformation levelInfo : levels) {
            if (levelInfo == levels.get(levels.size() - 1)) {
                lastLevel = true;
            }

            GameLevel level = new GameLevel(levelInfo,
                    this.animationRunner, this.keyboardSensor, score);

            level.initialize();

            while (level.getCounter().getValue() != 0
            && level.getLevelInformation().numberOfBallsToRemove() != 0) {
                level.run();
            }

            if (lastLevel && level.getCounter().getValue() == 0) {
                level.getRunner().run(new EndScreen(keyboardSensor, " ", level, level));
                break;
            }

            if (level.getLevelInformation().numberOfBallsToRemove() == 0) {
                level.getRunner().run(new EndScreen(keyboardSensor, " ", level, level));
                break;
            }
        }
    }
}
