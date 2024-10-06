package seedu.duke;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(FinancialList list) {
        list.deleteEntry(index - 1);  // Index correction as list is 0-based
        System.out.println("Entry deleted.");
    }
}