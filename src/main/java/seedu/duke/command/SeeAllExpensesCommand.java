package seedu.duke.command;

import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Expense;

/**
 * The SeeAllExpensesCommand class is responsible for displaying all recorded expenses
 * from the provided FinancialList. It extends the Command class and overrides the 
 * execute method to perform this functionality.
 */
public class SeeAllExpensesCommand extends Command{

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
