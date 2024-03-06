import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
/**

The FileOutput class provides utility methods for writing content to a file.
*/
public class FileOutput {
    public static String outputFile = Main.ARGS[1];
    /**
     * Writes content to the output file, with a new line appended.
     *
     * @param content The content to write to the file.
     */
    public static void writeToFile(String content) {
        String path = outputFile;
        boolean append = true;
        boolean newLine = true;
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.flush();
                ps.close();
            }
        }
    }
    /**
     * Writes content to a specified file path, with options for appending and adding a new line.
     *
     * @param path The path of the file to write content to.
     * @param content The content to write to the file.
     * @param append If true, content will be appended to the file; otherwise, file will be overwritten.
     * @param newLine If true, a new line will be added after the content; otherwise, not.
     */
    public static void writeToFile(String path, String content, boolean append, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.flush();
                ps.close();
            }
        }
    }
    /**
     * Clears the content of the output file.
     */
    public static void clearFile(){
        try {
            FileOutput.writeToFile(outputFile, "", false, false);
        } catch (Exception e){}
    }
    /**
     * Writes a list of strings as a command to the output file, separated by tabs, with a "COMMAND: " prefix.
     *
     * @param content The list of strings representing the command to write to the file.
     */
    public static void commandWriter(ArrayList<String> content) {
        String path = outputFile;
        boolean append = true;
        boolean newLine = true;
        String commands = String.join("\t", content);
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print("COMMAND: "+ commands + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.flush();
                ps.close();
            }
        }
    }

}