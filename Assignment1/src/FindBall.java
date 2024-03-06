import java.util.ArrayList;

public class FindBall {
    public static ArrayList<Integer> ballFinder(ArrayList<ArrayList<String>> nestedList){
        int outerIndex = -1;
        int innerIndex = -1;
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < nestedList.size(); i++) {
            ArrayList<String> innerList = nestedList.get(i);
            for (int j = 0; j < innerList.size(); j++) {
                if (innerList.get(j).equals("*")) {
                    outerIndex = i;
                    innerIndex = j;
                    break;
                }
            }
        }
        if (outerIndex != -1 && innerIndex != -1) {
            indices.add(outerIndex);
            indices.add(innerIndex);
            return indices;
        } else{
            return null;
        }
    }
    public static void main(String[] args){
        System.out.println(ballFinder(InputArrays.boardList));
    }
}

