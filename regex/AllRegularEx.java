import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllRegularEx {
    public static final String NP = "<np>[^<>]*?</np>";
    public AllRegularEx() {
    }
    public boolean isRegularEx(String s, String regularEx) {
        Pattern pattern = Pattern.compile(regularEx);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
    public java.util.List cutTheNp(String string) {
        java.util.List np = new ArrayList();
        Matcher matcher = Pattern.compile(NP).matcher(string);
        String groupOfNp;
        while (matcher.find()) {
            groupOfNp = matcher.group();
            np.add(groupOfNp.substring(4, groupOfNp.length() - 5));
        }
        return np;
    }
    public  java.util.List cutTheRegularEx(String string, String regularEx) {
        java.util.List np = new ArrayList();
        Matcher matcher = Pattern.compile(regularEx).matcher(string);
        while (matcher.find()) {
            np = cutTheNp(string.substring(matcher.start(), matcher.end()));
        }
        return np;
    }
}
