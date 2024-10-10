package seedu.duke.financial;

/**
 * Represents an expense transaction.
 * An expense reduces the available balance.
 */
public class Expense extends FinancialEntry {

    /**
     * Constructs an Expense object with the specified amount and description.
     *
     * @param amount The amount of the expense.
     * @param description A description of the expense.
     */
    public Expense(double amount, String description) {
        super(amount, description, "Expense");
    }

    /**
     * Returns a string representation of the expense.
     *
     * @return A string in the format "[Expense] $amount - description".
     */
    @Override
    public String toString() {
        return "[Expense] - " + description + " $ " + String.format("%.2f", amount);
    }
}
