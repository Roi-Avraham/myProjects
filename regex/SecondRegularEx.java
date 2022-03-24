import java.util.Map;

public class SecondRegularEx extends FirstUntilFourth {
    public static final String TEXT
            = "such <np>[^<>]*?</np> as <np>[^<>]*?</np>(( ,)?( and| or)? <np>(.*?)</np>)*";
    public SecondRegularEx() {
        super();
    }

    public boolean isSecondRegularEx(String s) {
        return super.isRegularEx(s, TEXT);
    }

    public Map<String, Map<String, Integer>> enterToDict(Map<String, Map<String, Integer>> dictionary, String string) {
        dictionary = super.enterToDict(dictionary, string, TEXT);
        return dictionary;
    }
}
