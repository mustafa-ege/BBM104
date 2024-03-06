import java.util.ArrayList;

public class LibraryManager {
    public static void manageCommands(ArrayList<ArrayList<String>> commandInput){
        for (ArrayList<String> commandLine : commandInput){
            String commandType = commandLine.get(0);
            switch (commandType){
                case ("addBook"):
                    Book.bookAdder(commandLine);
                    break;
                case ("addMember"):
                    LibraryMember.memberAdder(commandLine);
                    break;
                case ("borrowBook"):
                case ("readInLibrary"):
                case ("returnBook"):
                case ("extendBook"):
                    try{
                        Book.bookCommands(commandLine);
                    } catch (BookError e){
                        FileOutput.writeToFile(e.getMessage());
                    }
                    break;
                case ("getTheHistory"):
                    getHistory();
                    break;
            }
        }
    }
    public static void getHistory(){
        FileOutput.writeToFile("History of library:\n");
        Student.studentReport();
        Academician.academicianReport();
        PrintedBook.printedBookReport();
        HandwrittenBook.handwrittenBookReport();
        PrintedBook.borrowedBookReport();
        Book.readInLibraryBookReport();
    }
}
