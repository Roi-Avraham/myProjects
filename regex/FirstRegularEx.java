import java.util.Map;

public class FirstRegularEx extends FirstUntilFourth{
    public static final String TEXT
            = "<np>[^<>]*?</np> ( ,)?such as <np>[^<>]*?</np>(( ,)?( and| or)? <np>(.*?)</np>)*";
    public FirstRegularEx() {
    }
    public boolean isFirstRegularEx(String s) {
        return super.isRegularEx(s, TEXT);
    }
    public Map<String, Map<String, Integer>> enterToDict(Map<String, Map<String, Integer>> dictionary, String string) {
        dictionary = super.enterToDict(dictionary, string, TEXT);
        return dictionary;
    }
}
