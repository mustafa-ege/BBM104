import java.util.ArrayList;

public class NextMove {
    public static ArrayList<Integer> nextIndices(String move){
        ArrayList<Integer> whiteBall = FindBall.ballFinder(InputArrays.boardList);
        ArrayList<Integer> nextIndex = new ArrayList<Integer>();
        int numberOfColumns = InputArrays.boardList.get(0).size();
        int numberOfRows = InputArrays.boardList.size();
        if (move.equals("L")) {
            nextIndex.add(whiteBall.get(0));
            nextIndex.add((whiteBall.get(1)-1+numberOfColumns)%numberOfColumns);
        } else if (move.equals("R")) {
            nextIndex.add(whiteBall.get(0));
            nextIndex.add((whiteBall.get(1)+1)%numberOfColumns);
        } else if (move.equals("D")) {
            nextIndex.add((whiteBall.get(0)+1)%numberOfRows);
            nextIndex.add(whiteBall.get(1));
        } else if (move.equals("U")) {
            nextIndex.add((whiteBall.get(0)-1+numberOfRows)%numberOfRows);
            nextIndex.add(whiteBall.get(1));
        }
        //System.out.println(whiteBall);


        return nextIndex;
    }
    /*
    public static void main(String[] args){
        System.out.println(nextIndices("L"));
        System.out.println(nextIndices("R"));
        System.out.println(nextIndices("U"));
        System.out.println(nextIndices("D"));
    }*/
}
