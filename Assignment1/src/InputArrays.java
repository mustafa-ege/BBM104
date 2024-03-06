import java.util.ArrayList;

public class InputArrays {
    public static ArrayList<ArrayList<String>> arrayCreator(String path) {
        ArrayList<ArrayList<String>> arrList = new ArrayList<>();
        String[] lines = ReadFromFile.readFile(path);
        int place = -1;
        for (String line :lines){
            arrList.add(new ArrayList<String>());
            String[] splittedline= line.split(" ");
            place++;
            for (String letter:splittedline){
                arrList.get(place).add(letter);
            }
        }
        return arrList;
    }

    public static ArrayList<String> moveList(){
        ArrayList<ArrayList<String>> longMove = InputArrays.arrayCreator(Main.ARGS[1]);
        ArrayList<String> move = longMove.get(0);
        return move;
    }
    public static ArrayList<String> moveList = moveList();

    public static ArrayList<ArrayList<String>> boardList(){
        ArrayList<ArrayList<String>> board = InputArrays.arrayCreator(Main.ARGS[0]);
        return board;
    }
    public static ArrayList<ArrayList<String>> boardList = boardList();
}




