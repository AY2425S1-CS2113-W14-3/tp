package seedu.duke.command;

import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialList;
import seedu.duke.exception.FinanceBuddyException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;

/**
 * Command to add an expense to the financial list.
 */
public class AddExpenseCommand extends AddEntryCommand {
    private static final Logger logger = Logger.getLogger(AddExpenseCommand.class.getName());
    private final Expense.Category category;
    /**
     * Constructs an AddExpenseCommand with the specified amount and description.
     *
     * @param amount The amount of the expense.
     * @param description The description of the expense.
     * @param date The date of the expense.
     * @param category The category of the expense.
     */
    public AddExpenseCommand(double amount, String description, String date, Expense.Category category)
            throws FinanceBuddyException {
        super(amount, description, date);
        this.category = category;
        assert amount > 0 : "Amount should be positive";
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
    }

    /**
     * Executes the command by adding the expense to the provided financial list.
     *
     * @param list The financial list where the expense will be added.
     */
    @Override
    public void execute(FinancialList list) throws FinanceBuddyException {
        if (list == null) {
            logger.log(Level.SEVERE, "Financial list is null");
            throw new IllegalArgumentException("Financial list cannot be null");
        }

        int preEntryCount = list.getEntryCount();
        Expense expense = new Expense(amount, description, date, category);
        list.addEntry(expense);
        Map<Expense.Category, Double> expenseTotals = list.getTotalExpenseByCategory();
        expenseTotals.put(category, expenseTotals.getOrDefault(category, 0.0) + amount);
        assert list.getEntryCount() == preEntryCount + 1 : "Expense not added";
        System.out.println("--------------------------------------------");
        System.out.println("Got it! I've added this expense:");
        System.out.println(expense);
        System.out.println("--------------------------------------------");
        logger.log(Level.INFO, "Expense added to list: " + expense);

    }
}
