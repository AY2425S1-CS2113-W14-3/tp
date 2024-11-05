package seedu.duke.command;

import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.FinancialList;
import seedu.duke.log.Log;
import seedu.duke.log.LogLevels;
import seedu.duke.parser.DateParser;

import java.time.LocalDate;
/**
 * The EditEntryCommand class is responsible for editing an existing entry in the financial list.
 * It extends the Command class and provides functionality to update the amount and description
 * of a specified entry.
 * 
 * <p>Usage example:
 * <pre>
 *     FinancialList list = new FinancialList();
 *     EditEntryCommand command = new EditEntryCommand(1, 50.0, "Groceries");
 *     command.execute(list);
 * </pre>
 * </p>
 * 
 * <p>Class Invariants:
 * <ul>
 *     <li>The amount must be non-negative.</li>
 *     <li>The description must not be null or empty.</li>
 * </ul>
 * </p>
 * 
 * <p>Logging:
 * <ul>
 *     <li>Logs a severe message if the financial list is null.</li>
 *     <li>Logs an info message when an entry is successfully edited.</li>
 *     <li>Logs a warning message if the specified index is invalid.</li>
 * </ul>
 * </p>
 * 
 * @see Command
 * @see FinancialList
 */
public class EditEntryCommand extends Command {
    private static final Log logger = Log.getInstance();
    private int index;
    private double amount;
    private String description;
    private LocalDate date;
    private final Enum<?> category;

    /**
     * Constructs an EditEntryCommand with the specified index, amount, and description.
     *
     * @param index       The index of the entry to be edited.
     * @param amount      The new amount for the entry. Must be non-negative.
     * @param description The new description for the entry. Must not be null or empty.
     * @param date The new date for the entry.
     * @throws IllegalArgumentException if amount is negative or description is null/empty.
     */
    public EditEntryCommand(int index, double amount, String description, String date, Enum<?> category) {
        this.index = index;
        this.amount = amount;
        this.description = description;
        this.category = category;
        try {
            this.date = DateParser.parse(date);
        } catch (FinanceBuddyException e) {
            logger.log(LogLevels.SEVERE, "Error parsing date: " + date, e);
            throw new IllegalArgumentException("Invalid date format: " + date, e);
        }

        assert amount >= 0 : "Amount should be non-negative";
        assert description !=null && !description.isEmpty() : "Description should not be empty";
    }

    /**
     * Executes the command to edit an entry in the financial list.
     *
     * @param list The financial list containing the entries.
     * @throws FinanceBuddyException if the financial list is null.
     */
    @Override
    public void execute(FinancialList list) throws FinanceBuddyException {

        if (amount < 0.01) {
            throw new FinanceBuddyException("Invalid amount. Amount must be $0.01 or greater.");
        }
        if (amount > 9999999.00) {
            throw new FinanceBuddyException("Invalid amount. Amount must be $9999999.00 or less.");
        }
        if (this.date.isAfter(LocalDate.now())) {
            throw new FinanceBuddyException("Entered date cannot be after current date.");
        }

        if (list == null) {
            logger.log(LogLevels.SEVERE, "Financial list is null");
            throw new FinanceBuddyException("Financial list cannot be null");
        }
        if (index >= 0 && index <= list.getEntryCount()) {
            list.editEntry(index - 1, amount, description, date, category);
            assert list.getEntry(index - 1).getAmount() == amount : "Amount should be updated";
            assert list.getEntry(index - 1).getDescription().equals(description) : "Description should be updated";
            System.out.println("--------------------------------------------");
            System.out.println("Got it. I've edited this expense:");
            System.out.println(list.getEntry(index - 1));
            System.out.println("--------------------------------------------");
            logger.log(LogLevels.INFO, "Edited entry at index " + index + " to " + amount + " " + description);
        } else {
            System.out.println("OOPS!!! The entry does not exist.");
            System.out.println(index);
            System.out.println(list.getEntryCount());
            logger.log(LogLevels.WARNING, "Entry does not exist at index " + index);
        }
    }
}
