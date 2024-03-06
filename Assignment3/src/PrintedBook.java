import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PrintedBook extends Book{
    private LocalDate borrowDate;
    private boolean timeExtended = false;
    public static int numberOfPrintedBooks = 0;
    public PrintedBook() {
        setId(++numberOfBooks);
        numberOfPrintedBooks++;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    public boolean isTimeExtended() {
        return timeExtended;
    }
    public void setTimeExtended(boolean timeExtended) {
        this.timeExtended = timeExtended;
    }

    public void borrowBook(LibraryMember member, LocalDate date){
        try{
            member.addToBorrowedBooks();
            this.setBorrowDate(date);
            this.setMemberBorrowingOrReading(member);
            this.setBorrowOrRead("borrow");
            FileOutput.writeToFile(String.format("The book [%d] was borrowed by member [%d] at %s",
                    this.getId(),member.getId(),Book.timeStringReturner(date)));
        } catch (BookError e){
            FileOutput.writeToFile(e.getMessage());
        }
    }
    public void returnBorrowBook(LibraryMember member, LocalDate returnDate){
        LocalDate borrowedDate = this.getBorrowDate();
        long daysInBetween = ChronoUnit.DAYS.between(borrowedDate,returnDate);
        long fee = member.feeCalculator(daysInBetween);
        FileOutput.writeToFile(String.format("The book [%d] was returned by member [%d] at %s Fee: %d",
                this.getId(),member.getId(),returnDate,fee));
        this.resetBook();
    }
    public void resetBook(){
        this.setBorrowDate(null);
        this.setMemberBorrowingOrReading(null);
        this.setBorrowOrRead(null);
        this.setTimeExtended(false);
    }
    public void extendBook(LibraryMember member, LocalDate extendDate){
        LocalDate borrowedDate = this.getBorrowDate();
        if (borrowedDate.isBefore(extendDate) && !isTimeExtended()){
            member.setBorrowTimeLimit(member.getBorrowTimeLimit()*2);
            setTimeExtended(true);
            LocalDate newDeadline = this.getBorrowDate().plusDays(member.getBorrowTimeLimit());
            FileOutput.writeToFile(String.format("The deadline of book [%d] was extended by member [%d] at %s"
            ,this.getId(),member.getId(),extendDate));
            FileOutput.writeToFile(String.format("New deadline of book [%d] is %s",this.getId(),newDeadline));
        } else {
            FileOutput.writeToFile("You cannot extend the deadline!");
        }
    }

    @Override
    public String toString() {
        return "Printed [id: " + this.getId() + "]";
    }
    public static void printedBookReport() {
        FileOutput.writeToFile("Number of printed books: " + numberOfPrintedBooks);
        allBooks.forEach((key, value) -> {
            if (value instanceof PrintedBook) {
                FileOutput.writeToFile(value.toString());
            }
        });
        FileOutput.writeToFile("");
    }
    public static void borrowedBookReport() {
        ArrayList<String> booksBorrowed = new ArrayList<>();
        for (Book value : allBooks.values()) {
            try{
                if (value.getBorrowOrRead().equals("borrow")) {
                    booksBorrowed.add(String.format("The book [%d] was borrowed by member [%d] at %s", value.getId(),
                            value.getMemberBorrowingOrReading().getId(),Book.timeStringReturner(((PrintedBook) value).getBorrowDate())));
                }
            } catch (NullPointerException e){}
        }
        FileOutput.writeToFile("Number of borrowed books: " + booksBorrowed.size());
        booksBorrowed.forEach(line -> FileOutput.writeToFile(line));
        FileOutput.writeToFile("");
    }
}
