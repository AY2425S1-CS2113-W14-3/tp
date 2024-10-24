package seedu.duke.financial;

import java.time.LocalDate;

/**
 * Abstract base class for all financial transactions (Income and Expense).
 */
public abstract class FinancialEntry {
    protected String description;
    protected double amount;
    protected LocalDate date;

    /**
     * Constructs a FinancialEntry with the specified amount, description, and type.
     *
     * @param amount The amount of the transaction.
     * @param description A description of the transaction.
     * @param date The date of the transaction (dd/mm/yy).
     */
    public FinancialEntry(double amount, String description, LocalDate date){
        this.description = description;
        this.amount = amount;
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
     * Returns the date of the transaction.
     *
     * @return The transaction date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Updates the date of the transaction.
     *
     * @param newDate The new date.
     */
    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }
  
    /**
     * Returns a string representation of the financial entry.
     * Subclasses (Income, Expense) must implement this.
     *
     * @return A string representation of the financial entry.
     */
    public abstract String toString();

    public abstract String toStorageString();
}
