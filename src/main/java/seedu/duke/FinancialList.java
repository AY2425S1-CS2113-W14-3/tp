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

}