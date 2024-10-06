package seedu.duke;

public class Income extends FinancialEntry {
    public Income(double amount, String description) {
        super(amount, description, "Income");
    }

    @Override
    public String toString() {
        return "[Income] $" + amount + " - " + description;
    }
}
