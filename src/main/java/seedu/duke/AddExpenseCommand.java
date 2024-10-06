package seedu.duke;

public class AddExpenseCommand extends Command {
    private double amount;
    private String description;

    public AddExpenseCommand(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public void execute(FinancialList list) {
        Expense expense = new Expense(amount, description);
        list.addEntry(expense);
        System.out.println("Got it. I've added this expense:");
        System.out.println(expense);
    }
}
