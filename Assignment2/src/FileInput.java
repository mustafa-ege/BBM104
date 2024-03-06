import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * The FileInput class provides utility methods for reading files and creating lists from file input.
 */

public class FileInput {
    /**
     * Reads a file from the specified path and returns its content as an array of strings.
     *
     * @param path The path of the file to be read.
     * @return An array of strings representing the content of the file.
     */
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
    /**
     * Creates a nested list from a file, where each line in the file represents a list item.
     * Items in each line are separated by tabs ("\t").
     *
     * @param fileName The name of the file to be read.
     * @return An ArrayList of ArrayList of strings representing the nested list created from the file input.
     */
    public static ArrayList<ArrayList<String>> listCreator(String fileName){
        ArrayList<ArrayList<String>> nestedList = new ArrayList<>();
        String[] inputLine = FileInput.readFile(fileName);
        int k = 0;
        for (int i = 0 ; i<inputLine.length; i++){
            if (!(inputLine[i].isEmpty())){
                String[] lineItems = inputLine[i].split("\t");
                nestedList.add(new ArrayList<String>());
                for (int j = 0 ; j < lineItems.length ; j++){
                    nestedList.get(k).add(lineItems[j]);
                }
                k++;
            }
        }
        return nestedList;
    }

}
