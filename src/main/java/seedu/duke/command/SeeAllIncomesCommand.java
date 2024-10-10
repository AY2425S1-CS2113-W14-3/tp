package seedu.duke.command;

import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

/**
 * Command to print all incomes recorded in the financial list.
 */
public class SeeAllIncomesCommand extends Command {

    public SeeAllIncomesCommand() {}

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
        } else {
            System.out.println("Here's a list of all recorded incomes:");
            System.out.print(incomeList);
        }
    }
}
