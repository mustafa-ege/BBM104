import java.util.ArrayList;

public class Main {
    public static void boardPrinter(ArrayList<ArrayList<String>> board){
        for (ArrayList<String> row : board){
            for (int columnNo=0 ; columnNo<row.size();columnNo++){
                FileOutput.writeToFile("output.txt", row.get(columnNo), true, false);
                if (columnNo != row.size()-1){
                    FileOutput.writeToFile("output.txt", " ", true, false);
                }
            }
            FileOutput.writeToFile("output.txt","",true,true);
        }
        FileOutput.writeToFile("output.txt","",true,true);

    }
    public static String[] ARGS;

    public static void main(String[] args) {
        ARGS = args;
        FileOutput.writeToFile("output.txt",  "", false, false);
        for (String i : InputArrays.moveList){
            Playing.movingBalls(i);
            if (Playing.gameOver ==1){
                break;
            }
        }
        FileOutput.writeToFile("output.txt", "Game board:", true, true);
        boardPrinter(InputArrays.boardList());
        FileOutput.writeToFile("output.txt","Your movement is:",true,true);
        for (int moveNo =0 ; moveNo< InputArrays.moveList.size(); moveNo++){
            FileOutput.writeToFile("output.txt",InputArrays.moveList.get(moveNo),true,false);
            if (moveNo != InputArrays.moveList().size()-1){
                FileOutput.writeToFile("output.txt", " ", true, false);
            }
        }
        FileOutput.writeToFile("output.txt","\n\nYour output is:",true,true);
        boardPrinter(InputArrays.boardList);
        if (Playing.gameOver == 1){
            FileOutput.writeToFile("output.txt","Game Over!",true,true);
        }
        FileOutput.writeToFile("output.txt","Score: " + Integer.toString(Playing.score.getScore()),true,true);

    }
}