package seedu.duke.command;

import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.parser.DateParser;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;

/**
 * Command to add an expense to the financial list.
 */
public class AddIncomeCommand extends Command {

    private static Logger logger = Logger.getLogger("Income");
    private double amount;
    private String description;
    private LocalDate date;

    /**
     * Constructs an AddIncomeCommand with the specified amount and description.
     *
     * @param amount The amount of the income.
     * @param description The description of the income.
     */
    public AddIncomeCommand(double amount, String description, String date) throws FinanceBuddyException{
        this.amount = amount;
        this.description = description;
        this.date = DateParser.parse(date);
    }

    /**
     * Executes the command by adding the income to the provided financial list.
     * Assertion error if entry is not added.
     * Logs the added income entry at INFO level.
     *
     * @param list The financial list where the income will be added.
     */
    @Override
    public void execute(FinancialList list) {
        Income income = new Income(amount, description, date);
        int preEntryCount = list.getEntryCount();
        list.addEntry(income);
        assert list.getEntryCount() == preEntryCount + 1 : "Income not added";
        System.out.println("--------------------------------------------");
        System.out.println("Got it! I've added this income:");
        System.out.println(income);
        System.out.println("--------------------------------------------");
        logger.log(Level.INFO, "Income added to list: " + income);

    }
}
