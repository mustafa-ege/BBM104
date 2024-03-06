public class Student extends LibraryMember{
    final private int maximumBookBorrows = 2;
    private int borrowTimeLimit = 7;
    final private boolean permissionForHandwritten = false;
    public static int numberOfStudents=0;

    public Student() {
        setId(++numberOfMembers);
        numberOfStudents++;
    }
    @Override
    public int getBorrowTimeLimit() {
        return borrowTimeLimit;
    }
    @Override
    public void setBorrowTimeLimit(int borrowTimeLimit) {
        this.borrowTimeLimit = borrowTimeLimit;
    }

    @Override
    public boolean isPermissionForHandwritten() {
        return permissionForHandwritten;
    }
    @Override
    public void addToBorrowedBooks() throws BookError {
        if (getCurrentNumberOfBorrows()<maximumBookBorrows){
            setCurrentNumberOfBorrows(getCurrentNumberOfBorrows()+1);
        } else {
            throw new BookError("You have exceeded the borrowing limit!");
        }
    }
    @Override
    public long feeCalculator(long daysPassed) {
        if (daysPassed<borrowTimeLimit){
            return 0;
        } else{
            return daysPassed-borrowTimeLimit;
        }
    }

    @Override
    public String toString() {
        return "Student [id: " + getId() + "]";
    }

    public static void studentReport() {
        FileOutput.writeToFile("Number of students: " + numberOfStudents);
        allMembers.forEach((key,value)->{
            if (value instanceof Student) {
                FileOutput.writeToFile(value.toString());
            }
        });
        FileOutput.writeToFile("");
    }
}
