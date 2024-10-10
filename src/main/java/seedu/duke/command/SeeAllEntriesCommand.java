package seedu.duke.command;

import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;

/**
 * Command to print all incomes recorded in the financial list.
 */
public class SeeAllEntriesCommand extends Command {

    public SeeAllEntriesCommand() {}

    @Override
    public void execute(FinancialList list) {
        System.out.println("Here's a list of all recorded entries:");
        for (int i = 0; i < list.getEntryCount(); i++) {
            FinancialEntry entry = list.getEntry(i);
            System.out.println((i + 1) + ". " + entry);
        }
    }
}
