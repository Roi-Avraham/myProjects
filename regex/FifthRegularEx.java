import java.util.HashMap;
import java.util.Map;

public class FifthRegularEx extends AllRegularEx{
    public static final String TEXT
            = "<np>[^<>]*?<.np> , which is ((an example|a kind|a class))? of ? (<np>(.*?)<.np>)*";
    public FifthRegularEx() {
    }
    public boolean isFifthRegularEx(String s) {
        return super.isRegularEx(s, TEXT);
    }
    public Map<String, Map<String, Integer>> enterToDict(Map<String, Map<String, Integer>> dictionary, String string) {
        java.util.List  np = super.cutTheRegularEx(string, TEXT);
        Map<String, Integer> values = new HashMap<>();
        for (int i = 0; i < np.size() - 1; i++) {
            values.put((String) np.get(i), 1);
        }
        boolean isKeyPresent = dictionary.containsKey((String) np.get(np.size() - 1));
        if (!isKeyPresent) {
            dictionary.put((String) np.get(np.size() - 1), values);
        } else {
            Map<String, Integer> oldValues = dictionary.get((String) np.get(np.size() - 1));
            for (int i = 0; i < np.size() - 1; i++) {
                boolean isKeyPresent1 = oldValues.containsKey((String) np.get(i));
                if (isKeyPresent1) {
                    int j = oldValues.get((String) np.get(i));
                    j++;
                    oldValues.put((String) np.get(i), j);
                } else {
                    oldValues.put((String) np.get(i), values.get((String) np.get(i)));
                }
            }
            dictionary.put((String) np.get(np.size() - 1), oldValues);
        }
        return dictionary;
    }
}
