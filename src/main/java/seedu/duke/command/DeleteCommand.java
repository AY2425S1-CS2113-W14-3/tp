package seedu.duke.command;

import seedu.duke.financial.FinancialList;

/**
 * Command to delete a financial entry from the financial list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a DeleteCommand with the specified index.
     *
     * @param index The index of the entry to be deleted (1-based index).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command by deleting the financial entry from the provided list.
     * Adjusts the index to match the 0-based index used by the list.
     *
     * @param list The financial list where the entry will be deleted.
     */
    @Override
    public void execute(FinancialList list) {
        if (index >= 0 && index < list.getEntryCount()) {
            list.deleteEntry(index);
            System.out.println("Entry deleted.");
        } else {
            System.out.println("OOPS!!! The entry does not exist.");
        }
    }
}
