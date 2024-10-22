package seedu.duke.financial;

import java.util.ArrayList;

/**
 * Represents the list of financial entries (income and expenses).
 * Provides methods for adding, deleting, and viewing entries.
 */
public class FinancialList {
    private ArrayList<FinancialEntry> entries;

    /**
     * Constructs a FinancialList object with an empty list.
     */
    public FinancialList() {
        entries = new ArrayList<>();
    }

    /**
     * Adds a new financial entry to the list.
     *
     * @param entry The financial entry (income or expense) to be added.
     */
    public void addEntry(FinancialEntry entry) {
        entries.add(entry);
    }

    /**
     * Deletes a financial entry from the list by index.
     *
     * @param index The index of the entry to be deleted.
     */
    public void deleteEntry(int index) {
        entries.remove(index);
    }

    /**
     * Returns the number of entries in the list.
     *
     * @return The number of entries in the list.
     */
    public int getEntryCount() {
        return entries.size();
    }

    /**
     * Returns the financial entry at the specified index.
     *
     * @param index The index of the entry to be retrieved.
     * @return The financial entry at the specified index.
     */
    public FinancialEntry getEntry(int index) {
        if (index >= 0 && index <= entries.size()) {
            return entries.get(index);
        } else {
            System.out.println("OOPS!!! The entry does not exist.");
            return null;
        }
    }


    /**
     * Edits an existing financial entry in the list.
     *
     * @param index The index of the entry to be edited.
     * @param amount The new amount to be set for the entry.
     * @param description The new description to be set for the entry.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= entries.size()).
     */
    public void editEntry(int index, double amount, String description) {
        FinancialEntry entry = entries.get(index);
        entry.setAmount(amount);
        entry.setDescription(description);
    }
}
