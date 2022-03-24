import biuoop.KeyboardSensor;

import java.util.ArrayList;
import java.util.List;

public class playTheGame implements Task<Void> {
    private String[] args;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Animation animation;
    public playTheGame(String[] args, AnimationRunner animationRunner, KeyboardSensor keyboardSensor,
                       Animation animation) {
       this.args = args;
       this.animationRunner = animationRunner;
       this.keyboardSensor = keyboardSensor;
       this.animation = animation;
    }
    @Override
    public Void run() {
        GameFlow gameFlow = new GameFlow(animationRunner, keyboardSensor);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        if (args.length == 0) {
            DirectHit directHit = new DirectHit();
            WideEasy wideEasy = new WideEasy();
            Green3 green3 = new Green3();
            FinalFour finalFour = new FinalFour();
            levels.add(directHit);
            levels.add(wideEasy);
            levels.add(green3);
            levels.add(finalFour);
            gameFlow.runLevels(levels);
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("1")) {
                    DirectHit directHit = new DirectHit();
                    levels.add(directHit);
                }
                if (args[i].equals("2")) {
                    WideEasy wideEasy = new WideEasy();
                    levels.add(wideEasy);
                }
                if (args[i].equals("3")) {
                    Green3 green3 = new Green3();
                    levels.add(green3);
                }
                if (args[i].equals("4")) {
                    FinalFour finalFour = new FinalFour();
                    levels.add(finalFour);
                }
            }
            gameFlow.runLevels(levels);
        }
        showTheScore showTheScore = new showTheScore(keyboardSensor, "h", animation);
        ShowHiScoresTask showHiScoresTask = new ShowHiScoresTask(animationRunner, showTheScore);
        showHiScoresTask.run();
        return null;
    }
}
