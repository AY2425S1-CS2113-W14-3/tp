package seedu.duke;

public class Expense extends FinancialEntry {
    public Expense(double amount, String description) {
        super(amount, description, "Expense");
    }

    @Override
    public String toString() {
        return "[Expense] - " + description + " $ " + amount;
    }
}
