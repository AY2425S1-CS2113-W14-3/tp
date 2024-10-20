package seedu.duke.exception;

/**
 * Exception thrown for errors related to financial operations
 */
public class FinanceBuddyException extends Exception {

    /**
     * Constructs a FinanceBuddyException with the specified detail message.
     *
     * @param message The detail message.
     */
    public FinanceBuddyException(String message) {
        super(message);
    }
}
