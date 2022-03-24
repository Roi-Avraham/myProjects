import java.util.HashMap;
import java.util.Map;

public class FirstUntilFourth extends AllRegularEx {
    public FirstUntilFourth() {
        super();
    }
    public Map<String, Map<String, Integer>> enterToDict(Map<String, Map<String, Integer>> dictionary, String string,
                                                         String regularEx) {
        java.util.List  np = super.cutTheRegularEx(string, regularEx);
        Map<String, Integer> values = new HashMap<>();
        for (int i = 1; i < np.size(); i++) {
            values.put((String) np.get(i), 1);
        }
        boolean isKeyPresent = dictionary.containsKey((String) np.get(0));
        if (!isKeyPresent) {
            dictionary.put((String) np.get(0), values);
        } else {
            Map<String, Integer> oldValues = dictionary.get((String) np.get(0));
            for (int i = 1; i < np.size(); i++) {
                boolean isKeyPresent1 = oldValues.containsKey((String) np.get(i));
                if (isKeyPresent1) {
                    int j = oldValues.get((String) np.get(i));
                    j++;
                    oldValues.put((String) np.get(i), j);
                } else {
                    oldValues.put((String) np.get(i), values.get((String) np.get(i)));
                }
            }
            dictionary.put((String) np.get(0), oldValues);
        }
        return dictionary;
    }
}
