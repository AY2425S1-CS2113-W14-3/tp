package seedu.duke.command;

import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;
import seedu.duke.exception.FinanceBuddyException;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to add an expense to the financial list.
 */
public class AddIncomeCommand extends AddEntryCommand {

    private static Logger logger = Logger.getLogger("Income");
    private final Income.Category category;

    /**
     * Constructs an AddIncomeCommand with the specified amount and description.
     *
     * @param amount The amount of the income.
     * @param description The description of the income.
     */
    public AddIncomeCommand(double amount, String description, String date, Income.Category category)
            throws FinanceBuddyException{
        super(amount, description, date);
        this.category = category;

    }

    /**
     * Executes the command by adding the income to the provided financial list.
     * Assertion error if entry is not added.
     * Logs the added income entry at INFO level.
     *
     * @param list The financial list where the income will be added.
     */
    @Override
    public void execute(FinancialList list) throws FinanceBuddyException {
        Income income = new Income(amount, description, date, category);
        int preEntryCount = list.getEntryCount();
        list.addEntry(income);
        Map<Income.Category, Double> incomeTotals = list.getTotalIncomeByCategory();
        incomeTotals.put(category, incomeTotals.getOrDefault(category, 0.0) + amount);
        assert list.getEntryCount() == preEntryCount + 1 : "Income not added";
        System.out.println("--------------------------------------------");
        System.out.println("Got it! I've added this income:");
        System.out.println(income);
        System.out.println("--------------------------------------------");
        logger.log(Level.INFO, "Income added to list: " + income);

    }
}
