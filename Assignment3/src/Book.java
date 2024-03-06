import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Book {
    public static HashMap<Integer, Book> allBooks = new HashMap<>();
    public static int numberOfBooks = 0;

    private int id;
    private LocalDate readDate;

    private LibraryMember memberBorrowingOrReading;
    private String borrowOrRead;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getReadDate() {
        return readDate;
    }
    public void setReadDate(LocalDate readDate) {
        this.readDate = readDate;
    }

    public LibraryMember getMemberBorrowingOrReading() {
        return memberBorrowingOrReading;
    }
    public void setMemberBorrowingOrReading(LibraryMember idOfMemberBorrowingOrReading) {
        this.memberBorrowingOrReading = idOfMemberBorrowingOrReading;
    }
    public String getBorrowOrRead() {
        return borrowOrRead;
    }
    public void setBorrowOrRead(String borrowOrRead) {
        this.borrowOrRead = borrowOrRead;
    }

    public static void bookAdder(ArrayList<String> inputAddBook){
        String memberType = inputAddBook.get(1);
        if (memberType.equals("P")){
            PrintedBook createdBook = new PrintedBook();
            allBooks.put(createdBook.getId(), createdBook);
            FileOutput.writeToFile("Created new book: "+createdBook.toString());
        } else if (memberType.equals("H")) {
            HandwrittenBook createdBook = new HandwrittenBook();
            allBooks.put(createdBook.getId(), createdBook);
            FileOutput.writeToFile("Created new book: "+createdBook.toString());
        }
    }
    public static void bookCommands(ArrayList<String> inputBookCommands) throws BookError {
        String commandType = inputBookCommands.get(0);
        int bookId = Integer.parseInt(inputBookCommands.get(1));
        int memberId = Integer.parseInt(inputBookCommands.get(2));
        LocalDate date = timeObjectReturner(inputBookCommands.get(3));
        Book book = allBooks.get(bookId);
        LibraryMember member = LibraryMember.allMembers.get(memberId);
        // extra error for nonexistent book
        if (book == null){
            throw new BookError("This book does not exist!");
        }
        if (commandType.equals("returnBook")){
            if (book.getMemberBorrowingOrReading() != null){
                if (book.getBorrowOrRead().equals("read")){
                    book.returnReadBook(member, date);
                } else {
                    ((PrintedBook) book).returnBorrowBook(member, date);
                }
            } else {
                // extra error for books not available for returning
                throw new BookError("You cannot return a book has not been borrowed or being read in library!");
            }
        } else if (commandType.equals("extendBook")) {
            try{
                ((PrintedBook) book).extendBook(member, date);
            } catch (NullPointerException e){
                // extra error for books not available for extending
                FileOutput.writeToFile("This book has not been borrowed! You cannot extend it!");
            }
        } else {
            if (book.getMemberBorrowingOrReading() == null) {
                switch (commandType){
                    case ("borrowBook"):
                        try{
                            ((PrintedBook) book).borrowBook(member, date);
                        } catch (ClassCastException e){
                            FileOutput.writeToFile("Handwritten books cannot be borrowed!");
                        }
                        break;
                    case ("readInLibrary"):
                        book.readInLibrary(member, date);
                        break;
                }
            } else {
                FileOutput.writeToFile("You can not read this book!");
            }
        }
    }
    public void readInLibrary(LibraryMember member, LocalDate date) {
        if (this.getClass().equals(HandwrittenBook.class) && !member.isPermissionForHandwritten()) {
            FileOutput.writeToFile("Students can not read handwritten books!");
        } else {
            this.setReadDate(date);
            this.setMemberBorrowingOrReading(member);
            this.setBorrowOrRead("read");
            FileOutput.writeToFile(String.format("The book [%d] was read in library by member [%d] at %s"
                    , this.getId(), member.getId(), timeStringReturner(date)));
        }
    }
    public void returnReadBook(LibraryMember member, LocalDate returnDate){
        FileOutput.writeToFile(String.format("The book [%d] was returned by member [%d] at %s Fee: 0",
                this.getId(),member.getId(),returnDate));
        this.resetBook();
    }
    public void resetBook(){
        this.setReadDate(null);
        this.setMemberBorrowingOrReading(null);
        this.setBorrowOrRead(null);
    }
    public static LocalDate timeObjectReturner(String dateToBeSet){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newTime = LocalDate.parse(dateToBeSet, formatter);
        return newTime;}
    public static String timeStringReturner(LocalDate date){
        try{
            String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
            return dateString;
        } catch (NullPointerException e){
            return null;}
    }
    public static void readInLibraryBookReport() {
        ArrayList<String> booksRead = new ArrayList<>();
        for (Book value : allBooks.values()) {
            try{
                if (value.getBorrowOrRead().equals("read")) {
                    booksRead.add(String.format("The book [%d] was read in library by member [%d] at %s", value.getId(),
                            value.getMemberBorrowingOrReading().getId(),Book.timeStringReturner(value.getReadDate())));
                }
            } catch (NullPointerException e){}
        }
        FileOutput.writeToFile("Number of books read in library: " + booksRead.size());
        booksRead.forEach(line -> FileOutput.writeToFile(line));
    }

}
