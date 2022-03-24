import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * @author Roi Avraham id:318778081.
 * CreateHypernymDatabase is the main class.
 */
public class CreateHypernymDatabase {
    /**
     * the main of this class.
     * @param args String[]
     * @throws FileNotFoundException io
     */
    public static void main(String[] args) throws FileNotFoundException {
        String directoryOfTheCorpus = args[0];
        String pathOutputFile = args[1];
        Map<String, Map<String, Integer>> dictionary = new HashMap<>();
        File directoryPath = new File(directoryOfTheCorpus);
        File[] filesList = directoryPath.listFiles();
        Scanner sc = null;
        FirstRegularEx firstRegularEx = new FirstRegularEx();
        SecondRegularEx secondRegularEx = new SecondRegularEx();
        ThirdRegularEx thirdRegularEx = new ThirdRegularEx();
        FourthRegularEx fourthRegularEx = new FourthRegularEx();
        FifthRegularEx fifthRegularEx = new FifthRegularEx();
        for (File file : filesList) {
            sc = new Scanner(file);
            String input;
            StringBuffer sb = new StringBuffer();
            while (sc.hasNextLine()) {
                input = sc.nextLine();
                if (firstRegularEx.isFirstRegularEx(input)) {
                    firstRegularEx.enterToDict(dictionary, input);
                } else if (secondRegularEx.isSecondRegularEx(input)) {
                    secondRegularEx.enterToDict(dictionary, input);
                } else if (thirdRegularEx.isThirdRegularEx(input)) {
                    thirdRegularEx.enterToDict(dictionary, input);
                } else if (fourthRegularEx.isFourthRegularEx(input)) {
                    fourthRegularEx.enterToDict(dictionary, input);
                } else if (fifthRegularEx.isFifthRegularEx(input)) {
                    fifthRegularEx.enterToDict(dictionary, input);
                }
                sb.append(input + " ");
            }
        }

        Map<String, Map<String, Integer>> treeMap = new TreeMap<String, Map<String, Integer>>(dictionary);

        File tempFile;
        tempFile = new File("src//output.txt");
        boolean exists = tempFile.exists();
        BufferedWriter bf = null;
        if (!exists) {
            File file = new File("src//output.txt");
        }
        try {
            Path file1 = Path.of("src//output.txt");
            File file = file1.toFile();
            bf = new BufferedWriter(new FileWriter(file));

            //iterate map entries
            for (Map.Entry<String, Map<String, Integer>> entry : treeMap.entrySet()) {


                Map<String, Integer> unsortedMap = new LinkedHashMap<String, Integer>(entry.getValue());
                LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
                unsortedMap.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
                int numOfValues = 0;
                for (Map.Entry<String, Integer> entry1 : reverseSortedMap.entrySet()) {
                   numOfValues++;
                }
                if (numOfValues >= 1) {
                    //put key and value separated by a colon
                    bf.write(entry.getKey() + ":");
                    int count = 0;
                    for (Map.Entry<String, Integer> entry1 : reverseSortedMap.entrySet()) {
                        count++;
                        if (count != numOfValues) {
                            bf.write(entry1.getKey() + "(" + entry1.getValue() + "), ");
                        } else {
                            bf.write(entry1.getKey() + "(" + entry1.getValue() + ")");
                        }
                    }
                    //new line
                    bf.newLine();
                }
            }

            bf.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                //always close the writer
                bf.close();
            } catch (Exception e) {
            }
        }
    }

}
