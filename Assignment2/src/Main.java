import java.util.ArrayList;
/**
 * The Main class represents the entry point of the program and contains the main method.
 * It starts by clearing the output file, reading the input file and creating the input list,
 * then calling the method that handles the input.
 */
public class Main {
    public static String[] ARGS;
    /**
     * The main method is the entry point of the program.
     * It reads the command line arguments, clears the output file, and manages commands.
     *
     * @param args The command line arguments passed to the program.
     */
    public static void main(String[] args) {
        ARGS = args;
        FileOutput.clearFile();
        ArrayList<ArrayList<String>> inputFileArray = FileInput.listCreator(args[0]);
        CommandManager.manageCommands(inputFileArray);
    }
}