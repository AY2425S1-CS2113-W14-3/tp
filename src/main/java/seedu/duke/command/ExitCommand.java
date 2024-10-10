package seedu.duke.command;

import seedu.duke.financial.FinancialList;

public class ExitCommand extends Command {

    @Override
    public void execute(FinancialList list) {
        System.out.println("Bye! Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }

}
