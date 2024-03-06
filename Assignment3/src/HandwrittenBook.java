public class HandwrittenBook extends Book{
    public static int numberOfHandwrittenBooks = 0;
    public HandwrittenBook() {
        setId(++numberOfBooks);
        numberOfHandwrittenBooks++;
    }

    @Override
    public String toString() {
        return "Handwritten [id: " + this.getId() + "]";
    }
    public static void handwrittenBookReport() {
        FileOutput.writeToFile("Number of handwritten books: " + numberOfHandwrittenBooks);
        allBooks.forEach((key,value)->{
            if (value instanceof HandwrittenBook) {
                FileOutput.writeToFile(value.toString());
            }
        });
        FileOutput.writeToFile("");
    }
}
