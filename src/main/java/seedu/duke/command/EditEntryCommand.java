package seedu.duke.command;

import seedu.duke.financial.FinancialList;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger(EditEntryCommand.class.getName());
    private int index;
    private double amount;
    private String description;

    /**
     * Constructs an EditEntryCommand with the specified index, amount, and description.
     *
     * @param index       The index of the entry to be edited.
     * @param amount      The new amount for the entry. Must be non-negative.
     * @param description The new description for the entry. Must not be null or empty.
     * @throws IllegalArgumentException if amount is negative or description is null/empty.
     */
    public EditEntryCommand(int index, double amount, String description) {
        this.index = index;
        this.amount = amount;
        this.description = description;

        assert amount >= 0 : "Amount should be non-negative";
        assert description !=null && !description.isEmpty() : "Description should not be empty";
    }

    /**
     * Executes the command to edit an entry in the financial list.
     *
     * @param list The financial list containing the entries.
     * @throws IllegalArgumentException if the financial list is null.
     */
    @Override
    public void execute(FinancialList list) {
        if (list == null) {
            logger.log(Level.SEVERE, "Financial list is null");
            throw new IllegalArgumentException("Financial list cannot be null");
        }
        if (index >= 0 && index <= list.getEntryCount()) {
            list.editEntry(index - 1, amount, description);
            assert list.getEntry(index - 1).getAmount() == amount : "Amount should be updated";
            assert list.getEntry(index - 1).getDescription().equals(description) : "Description should be updated";
            System.out.println("--------------------------------------------");
            System.out.println("Got it. I've edited this expense:");
            System.out.println(list.getEntry(index - 1));
            System.out.println("--------------------------------------------");
            logger.log(Level.INFO, "Edited entry at index " + index + " to " + amount + " " + description);
        } else {
            System.out.println("OOPS!!! The entry does not exist.");
            System.out.println(index);
            System.out.println(list.getEntryCount());
            logger.log(Level.WARNING, "Entry does not exist at index " + index);
        }
    }
}
