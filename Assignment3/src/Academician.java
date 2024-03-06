public class Academician extends LibraryMember{
    final private int maximumBookBorrows = 4;
    private int borrowTimeLimit = 14;
    final private boolean permissionForHandwritten = true;
    public static int numberOfAcademicians =0;
    public Academician() {
        setId(++numberOfMembers);
        numberOfAcademicians++;
    }
    public int getBorrowTimeLimit() {
        return borrowTimeLimit;
    }
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
        return "Academic [id: " + getId() + "]";
    }
    public static void academicianReport() {
        FileOutput.writeToFile("Number of academics: " + numberOfAcademicians);
        allMembers.forEach((key,value)->{
            if (value instanceof Academician) {
                FileOutput.writeToFile(value.toString());
            }
        });
        FileOutput.writeToFile("");
    }
}
