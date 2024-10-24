package seedu.duke.financial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public Expense(double amount, String description, LocalDate date) {
        super(amount, description, date);
    }

    /**
     * Returns a string representation of the expense including the date.
     *
     * @return A string in the format "[Expense] $amount - description (on date)".
     */
    @Override
    public String toString() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("[Expense] - %s $ %.2f (on %s)", description, amount, date.format(pattern));
    }

    public String toStorageString() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("E | %.2f | %s | %s", amount, description, date.format(pattern));
    }
}
