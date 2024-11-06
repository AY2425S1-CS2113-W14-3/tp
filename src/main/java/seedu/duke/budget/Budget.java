package seedu.duke.budget;

import java.time.LocalDate;

/**
 * Represents a budget with an amount, balance, and budget status.
 */
public class Budget {
    LocalDate budgetSetDate;
    private double budgetAmount;
    private double balance;
    private boolean isBudgetSet;

    /**
     * Constructs a new Budget instance with default values.
     * The budget amount and balance are set to 0, and the budget is marked as unset.
     */
    public Budget() {
        this.budgetAmount = 0;
        this.balance = 0;
        this.isBudgetSet = false;
        this.budgetSetDate = null;
    }

    /**
     * Returns the budget amount.
     *
     * @return the budget amount.
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /**
     * Sets a new budget amount, updates the balance to this amount, and marks the budget as set.
     *
     * @param newBudgetAmount the new budget amount to be set.
     */
    public void setBudgetAmount(double newBudgetAmount) {
        this.budgetAmount = newBudgetAmount;
        this.balance = newBudgetAmount;
        this.isBudgetSet = true;
        this.budgetSetDate = LocalDate.now();
    }

    /**
     * Checks if the budget has been set.
     *
     * @return true if the budget is set, false otherwise.
     */
    public boolean isBudgetSet() {
        return isBudgetSet;
    }

    /**
     * Returns the current balance of the budget.
     *
     * @return the current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Updates the balance with a new value.
     *
     * @param newBalance the new balance to be updated.
     */
    public void updateBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void setBudgetSetDate(LocalDate date) {
        this.budgetSetDate = date;
    }

    public String toStorageString() {
        return String.format("%.2f", budgetAmount) + "\n" + budgetSetDate;
    }
}
