package seedu.duke.command;

import seedu.duke.financial.FinancialList;

/**
 * Command to print help menu.
 */
public class HelpCommand extends Command{
    /**
     * Constructor for Help Command object.
     */
    public HelpCommand() {}

    /**
     * Executes help command by printing out help menu.
     */
    @Override
    public void execute(FinancialList financialList) {
        String helpMenu = "--------------------------------------------\n" +
                "List of commands:\n" +
                "--------------------------------------------\n" +
                "1. list [income|expense]\n " +
                "   - Shows a list of logged transactions\n" +
                "    - Optional: Specify 'income' or 'expense' to filter the list\n" +
                "2. expense DESCRIPTION /a AMOUNT\n " +
                "   - Adds a new expense\n" +
                "3. income DESCRIPTION /a AMOUNT\n " +
                "   - Adds a new income\n" +
                "4. edit INDEX [/des DESCRIPTION] [/a AMOUNT]\n " +
                "   - Edits the transaction at the specified INDEX\n" +
                "5. delete INDEX\n " +
                "   - Deletes the transaction at the specified INDEX\n" +
                "6. exit\n " +
                "   - Exits the program\n" +
                "7. help\n " +
                "   - Shows a list of all valid commands\n" +
                "--------------------------------------------\n";
        System.out.print(helpMenu);
    }
}
