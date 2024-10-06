package seedu.duke;

public abstract class FinancialEntry {
    protected String description;
    protected double amount;
    protected String type;

    public FinancialEntry(double amount, String type, String description){
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public abstract String toString();
}
