package seedu.duke.financial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public Income(double amount, String description, LocalDate date) {
        super(amount, description, date);
    }

    /**
     * Returns a string representation of the income.
     *
     * @return A string in the format "[Income] $amount - description".
     */
    @Override
    public String toString() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("[Income] - %s $ %.2f (on %s)", description, amount, date.format(pattern));
    }

    public String toStorageString() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("I | %.2f | %s | %s", amount, description, date.format(pattern));
    }
}
