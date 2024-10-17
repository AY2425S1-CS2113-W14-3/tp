package seedu.duke.command;

import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Expense;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SeeAllExpensesCommand class is responsible for displaying all recorded expenses
 * from the provided FinancialList. It extends the Command class and overrides the 
 * execute method to perform this functionality.
 */
public class SeeAllExpensesCommand extends Command{
    private static final Logger logger = Logger.getLogger(SeeAllExpensesCommand.class.getName());
    public SeeAllExpensesCommand() {}

    /**
     * Executes the command to display all recorded expenses in the financial list.
     * Iterates through the financial list and collects all entries that are instances of Expense.
     * If no expenses are found, it prints a message indicating no recorded expenses.
     * Otherwise, it prints a list of all recorded expenses.
     *
     * @param list The financial list containing financial entries.
     */
    @Override
    public void execute(FinancialList list) {
        if (list == null) {
            logger.log(Level.SEVERE, "Financial list is null");
            assert list != null : "Financial list cannot be null";
            throw new IllegalArgumentException("Financial list cannot be null");
        }
        String expenseList = "";
        int expenseCount = 0;
        for (int i = 0; i < list.getEntryCount(); i++) {
            FinancialEntry entry = list.getEntry(i);
            if (entry instanceof Expense) {
                expenseList += ((++expenseCount) + ". " + entry + System.lineSeparator());
            }
        }
        if (expenseCount == 0) {
            System.out.println("No recorded expenses found.");
            System.out.println("--------------------------------------------");
        } else {
            System.out.println("Here's a list of all recorded expenses:");
            System.out.print(expenseList);
            System.out.println("--------------------------------------------");
        }
    }

}
