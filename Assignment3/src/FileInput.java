import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileInput {

    public static String[] readFile(String path) {
        try {
            int i =0;
            int length= Files.readAllLines(Paths.get(path)).size();
            String[] results = new String[length];
            for (String line : Files.readAllLines(Paths.get(path))) {
                results[i++] = line;
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<ArrayList<String>> listCreator(String fileName){
        ArrayList<ArrayList<String>> nestedList = new ArrayList<>();
        String[] inputLine = FileInput.readFile(fileName);
        int k = 0;
        for (String s : inputLine) {
            if (!(s.isEmpty())) {
                String[] lineItems = s.split("\t");
                nestedList.add(new ArrayList<String>());
                for (String lineItem : lineItems) {
                    nestedList.get(k).add(lineItem);
                }
                k++;
            }
        }
        return nestedList;
    }

}
