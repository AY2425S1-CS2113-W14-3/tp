package seedu.duke.financial;

/**
 * Represents an income transaction.
 * An income increases the available balance.
 */
public class Income extends FinancialEntry {

    /**
     * Constructs an Income object with the specified amount and description.
     *
     * @param amount The amount of the income.
     * @param description A description of the income.
     */
    public Income(double amount, String description) {
        super(amount, description, "Income");
    }

    /**
     * Returns a string representation of the income.
     *
     * @return A string in the format "[Income] $amount - description".
     */
    @Override
    public String toString() {
        return "[Income] - " + description + " $ " + String.format("%.2f", amount);
    }
}
