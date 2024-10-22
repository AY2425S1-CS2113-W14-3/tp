package seedu.duke.command;

import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

/**
 * Command to print all incomes recorded in the financial list.
 */
public class SeeAllIncomesCommand extends Command {

    public SeeAllIncomesCommand() {}

    /**
     * Executes the command to display all recorded incomes in the financial list.
     * Iterates through the financial list and collects all entries that are instances of Income.
     * If no incomes are found, it prints a message indicating no recorded incomes.
     * Otherwise, it prints a list of all recorded incomes.
     *
     * @param list The financial list containing financial entries.
     */
    @Override
    public void execute(FinancialList list) {
        String incomeList = "";
        int incomeCount = 0;
        for (int i = 0; i < list.getEntryCount(); i++) {
            FinancialEntry entry = list.getEntry(i);
            if (entry instanceof Income) {
                incomeList += ((++incomeCount) + ". " + entry + System.lineSeparator());
            }
        }
        if (incomeCount == 0) {
            System.out.println("No recorded incomes found.");
            System.out.println("--------------------------------------------");
        } else {
            System.out.println("Here's a list of all recorded incomes:");
            System.out.print(incomeList);
            System.out.println("--------------------------------------------");
        }
    }
}
