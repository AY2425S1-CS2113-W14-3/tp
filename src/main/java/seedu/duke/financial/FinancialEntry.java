package seedu.duke.financial;

import java.time.LocalDate;

/**
 * Abstract base class for all financial transactions (Income and Expense).
 */
public abstract class FinancialEntry {
    protected String description;
    protected double amount;
    protected String type;
    protected LocalDate date;

    /**
     * Constructs a FinancialEntry with the specified amount, description, and type.
     *
     * @param amount The amount of the transaction.
     * @param description A description of the transaction.
     * @param type The type of the transaction ("Income" or "Expense").
     */
    public FinancialEntry(double amount, String description, String type, LocalDate date){
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }
    
    /**
     * Returns the description of the transaction.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of the transaction.
     *
     * @param newDescription The new description.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return The transaction amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Updates the amount of the transaction.
     *
     * @param newAmount The new amount.
     */
    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    /**
     * Returns the type of the transaction.
     *
     * @return "Income" or "Expense".
     */
    public String getType() {
        return type;
    }

    /**
     * Returns a string representation of the financial entry.
     * Subclasses (Income, Expense) must implement this.
     *
     * @return A string representation of the financial entry.
     */
    public abstract String toString();
}
