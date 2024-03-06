import java.util.ArrayList;

public class Playing {
    static Score score = new Score();
    static int gameOver = 0;



    public static String targetItem(String move){
        int row = NextMove.nextIndices(move).get(0);
        int col = NextMove.nextIndices(move).get(1);
        return InputArrays.boardList.get(row).get(col);
    }
    public static String collidedSpecialBall(String targetItem){
        if (targetItem.equals("R")){
            score.addRedBallPoints();
            return "X";
        } else if (targetItem.equals("Y")) {
            score.addYellowBallPoints();
            return "X";
        } else if (targetItem.equals("B")) {
            score.subtractBlackBallPoints();
            return "X";
        }else{
            return targetItem;
        }
    }
    public static void movingBalls(String move){
        int targetRow = NextMove.nextIndices(move).get(0);
        int targetCol = NextMove.nextIndices(move).get(1);
        ArrayList<Integer> whiteBall = FindBall.ballFinder(InputArrays.boardList);
        String targetItem = targetItem(move);
        if (targetItem.equals("H")){
            InputArrays.boardList.get(whiteBall.get(0)).set(whiteBall.get(1)," ");
            gameOver = 1;
        } else if (!targetItem.equals("W")){
            targetItem = collidedSpecialBall(targetItem);
            InputArrays.boardList.get(whiteBall.get(0)).set(whiteBall.get(1),targetItem);
            InputArrays.boardList.get(targetRow).set(targetCol,"*");
        } else if (targetItem.equals("W")) {
            if (move.equals("D")){
                move = "U";
            } else if (move.equals("U")) {
                move = "D";
            }else if (move.equals("R")) {
                move = "L";
            }else if (move.equals("L")) {
                move = "R";
            }
            targetRow = NextMove.nextIndices(move).get(0);
            targetCol = NextMove.nextIndices(move).get(1);
            targetItem = targetItem(move);
            targetItem = collidedSpecialBall(targetItem);
            InputArrays.boardList.get(whiteBall.get(0)).set(whiteBall.get(1),targetItem);
            InputArrays.boardList.get(targetRow).set(targetCol,"*");
            }
    }
}
