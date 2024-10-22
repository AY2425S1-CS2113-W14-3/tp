package seedu.duke.command;

import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;

/**
 * Command to print all entries recorded in the financial list.
 */
public class SeeAllEntriesCommand extends Command {

    public SeeAllEntriesCommand() {}

    @Override
    public void execute(FinancialList list) {
        System.out.println("--------------------------------------------");
        if (list.getEntryCount() == 0) {
            System.out.println("No entries found.");
            System.out.println("--------------------------------------------");
            return;
        }
        System.out.println("Here's a list of all recorded entries:");
        for (int i = 0; i < list.getEntryCount(); i++) {
            FinancialEntry entry = list.getEntry(i);
            System.out.println((i + 1) + ". " + entry);
        }
        System.out.println("--------------------------------------------");
    }
}
