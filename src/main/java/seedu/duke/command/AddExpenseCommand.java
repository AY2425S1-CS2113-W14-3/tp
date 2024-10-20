package seedu.duke.command;

import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialList;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.parser.DateParser;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;

/**
 * Command to add an expense to the financial list.
 */
public class AddExpenseCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddExpenseCommand.class.getName());
    private double amount;
    private String description;
    private LocalDate date;

    /**
     * Constructs an AddExpenseCommand with the specified amount and description.
     *
     * @param amount The amount of the expense.
     * @param description The description of the expense.
     */
    public AddExpenseCommand(double amount, String description, String date) throws FinanceBuddyException {
        assert amount > 0 : "Amount should be positive";
        assert description != null && !description.isEmpty() : "Description should not be null or empty";

        this.amount = amount;
        this.description = description;
        this.date = DateParser.parse(date);
    }

    /**
     * Executes the command by adding the expense to the provided financial list.
     *
     * @param list The financial list where the expense will be added.
     */
    @Override
    public void execute(FinancialList list) {
        if (list == null) {
            logger.log(Level.SEVERE, "Financial list is null");
            throw new IllegalArgumentException("Financial list cannot be null");
        }

        int preEntryCount = list.getEntryCount();
        Expense expense = new Expense(amount, description, date);
        list.addEntry(expense);
        assert list.getEntryCount() == preEntryCount + 1 : "Expense not added";
        System.out.println("--------------------------------------------");
        System.out.println("Got it! I've added this expense:");
        System.out.println(expense);
        System.out.println("--------------------------------------------");
        logger.log(Level.INFO, "Income added to list: " + expense);

    }
}
