package seedu.duke;

public class AddIncomeCommand extends Command {
    private double amount;
    private String description;

    public AddIncomeCommand(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public void execute(FinancialList list) {
        Income income = new Income(amount, description);
        list.addEntry(income);
        System.out.println("Got it. I've added this income:");
        System.out.println(income);
    }
}
