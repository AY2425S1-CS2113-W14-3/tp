package seedu.duke.command;

import seedu.duke.financial.FinancialList;

/**
 * The abstract base class for all command types.
 * Defines the structure of commands in the application.
 */
public abstract class Command {

    /**
     * Executes the command with the provided financial list.
     *
     * @param list The financial list on which the command will operate.
     */
    public abstract void execute(FinancialList list);

}
