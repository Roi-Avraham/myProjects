import java.util.Map;

public class FourthRegularEx extends FirstUntilFourth{
    public static final String TEXT
            = "<np>[^<>]*?</np> ( ,)?especially <np>[^<>]*?</np>(( ,)?( and| or)? <np>(.*?)</np>)*";

    public FourthRegularEx() {
        super();
    }

    public boolean isFourthRegularEx(String s) {
        return super.isRegularEx(s, TEXT);
    }

    public Map<String, Map<String, Integer>> enterToDict(Map<String, Map<String, Integer>> dictionary, String string) {
        dictionary = super.enterToDict(dictionary, string, TEXT);
        return dictionary;
    }
}
