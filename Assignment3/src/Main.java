import java.util.ArrayList;

public class Main {
    public static String[] ARGS;
    public static void main(String[] args) {
        ARGS = args;
        FileOutput.clearFile();
        ArrayList<ArrayList<String>> inputFileArray = FileInput.listCreator(args[0]);
        LibraryManager.manageCommands(inputFileArray);
    }
}