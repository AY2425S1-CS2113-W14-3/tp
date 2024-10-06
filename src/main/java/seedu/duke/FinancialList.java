package seedu.duke;

import java.util.ArrayList;

public class FinancialList {
    private ArrayList<FinancialEntry> entries;

    public FinancialList() {
        entries = new ArrayList<>();
    }

    public void addEntry(FinancialEntry entry) {
        entries.add(entry);
    }

    public void deleteEntry(int index) {
        if (index >= 0 && index < entries.size()) {
            entries.remove(index);
        } else {
            System.out.println("OOPS!!! The entry does not exist.");
        }
    }

}
