/**
 * The WrongValueError class represents an exception that is thrown when a wrong value is encountered.
 */
public class WrongValueError extends Exception {
    /**
     * Constructs a new WrongValueError instance with the specified error message.
     *
     * @param msg The error message associated with the exception.
     */
    public WrongValueError(String msg) {
        super(msg);
    }
}
