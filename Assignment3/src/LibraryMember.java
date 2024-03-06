import java.util.ArrayList;
import java.util.HashMap;

public abstract class LibraryMember {
    public static HashMap<Integer, LibraryMember> allMembers = new HashMap<>();
    public static int numberOfMembers = 0;
    private int id;
    private int currentNumberOfBorrows = 0;
    private int borrowTimeLimit;
    private boolean permissionForHandwritten;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCurrentNumberOfBorrows() {
        return currentNumberOfBorrows;
    }
    public void setCurrentNumberOfBorrows(int currentNumberOfBorrows) {
        this.currentNumberOfBorrows = currentNumberOfBorrows;
    }
    public int getBorrowTimeLimit() {
        return borrowTimeLimit;
    }
    public void setBorrowTimeLimit(int borrowTimeLimit) {
        this.borrowTimeLimit = borrowTimeLimit;
    }
    public boolean isPermissionForHandwritten() {
        return permissionForHandwritten;
    }

    public abstract void addToBorrowedBooks() throws BookError;
    public abstract long feeCalculator(long daysPassed);


    public static void memberAdder(ArrayList<String> inputAddMember){
        String memberType = inputAddMember.get(1);
        if (memberType.equals("S")){
            Student createdMember = new Student();
            allMembers.put(createdMember.getId(), createdMember);
            FileOutput.writeToFile("Created new member: "+createdMember.toString());
        } else if (memberType.equals("A")) {
            Academician createdMember = new Academician();
            allMembers.put(createdMember.getId(), createdMember);
            FileOutput.writeToFile("Created new member: "+createdMember.toString());
        }
    }


}
