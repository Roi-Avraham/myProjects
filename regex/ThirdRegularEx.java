import java.util.Map;

public class ThirdRegularEx extends FirstUntilFourth {
    public static final String TEXT
            = "<np>[^<>]*?</np> ( ,)? including <np>[^<>]*?</np>(( ,)?( and| or)? <np>(.*?)</np>)*";

    public ThirdRegularEx() {
        super();
    }

    public boolean isThirdRegularEx(String s) {
        return super.isRegularEx(s, TEXT);
    }

    public Map<String, Map<String, Integer>> enterToDict(Map<String, Map<String, Integer>> dictionary, String string) {
        dictionary = super.enterToDict(dictionary, string, TEXT);
        return dictionary;
    }
}
