package seedu.duke.command;

import seedu.duke.financial.Expense;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map.Entry;

/**
 * Command to print all entries recorded in the financial list.
 */
public class SeeAllEntriesCommand extends Command {
    protected static final String LINE_SEPARATOR = "--------------------------------------------";
    protected static Logger logger = Logger.getLogger(SeeAllExpensesCommand.class.getName());
    protected final String entriesListedMessage = "Here's a list of all recorded entries:";
    protected final String noEntriesMessage = "No entries found.";
    protected final String cashflowHeader = "Net cashflow: $ ";

    protected LocalDate start;
    protected LocalDate end;

    /**
     * Constructor for SeeAllEntriesCommand.
     *
     * @param start Start date of all entries to be listed, null if no start date to be specified.
     * @param end End date of all entries to be listed, null if no end date to be specified.
     */
    public SeeAllEntriesCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Method to return message when no entries are to be listed as a String.
     *
     * @return Message when no entries are to be listed.
     */
    protected String getNoEntriesMessage() {
        return this.noEntriesMessage;
    }

    /**
     * Method to return header when there are entries to be listed as a String.
     *
     * @return Header when there are entries to be listed.
     */
    protected String getEntriesListedMessage() {
        return this.entriesListedMessage;
    }

    /**
     * Method to return header when displaying cashflow.
     *
     * @return Header for cashflow display.
     */
    protected String getCashflowHeader() {
        return this.cashflowHeader;
    }

    protected String getHighestCategoryInfo(FinancialList list) {
        Entry<Expense.Category, Double> highestExpenseCategory = list.getHighestExpenseCategory();
        Entry<Income.Category, Double> highestIncomeCategory = list.getHighestIncomeCategory();
        return formatHighestCategory(highestExpenseCategory, "Highest Expense") + "\r\n"
                + formatHighestCategory(highestIncomeCategory, "Highest Income");
    }

    /**
     * Method to express cashflow as a String for printing.
     *
     * @param cashflow Net cashflow to be printed.
     * @return String of cashflow as a 2d.p. decimal number.
     */
    protected String getCashflowString(double cashflow) {
        return String.format("%.2f", cashflow);
    }

    /**
     * Formats the highest category and amount as a string.
     *
     * @param categoryEntry Entry representing the category and amount.
     * @param label Label to indicate if it is income or expense.
     * @return Formatted string of the category and amount.
     */
    protected String formatHighestCategory(Entry<?, Double> categoryEntry, String label) {
        return label + " Category: " + categoryEntry.getKey()
                + " ($" + String.format("%.2f", categoryEntry.getValue()) + ")";
    }

    /**
     * Method to determine if an entry should be listed out based on its date.
     *
     * @param entry Financial Entry to analyze.
     * @return true if entry should be listed out, false otherwise.
     */
    protected boolean shouldBeIncluded(FinancialEntry entry) {
        return (end == null || entry.getDate().isBefore(end)) && (start == null || entry.getDate().isAfter(start));
    }

    /**
     * Executes the command to display all recorded entries in the financial list.
     * Iterates through the financial list and collects all entries that are within the date range.
     * If no entries are found, it prints a message indicating no recorded entries.
     * Otherwise, it prints a list of all recorded entries, and the net cashflow.
     *
     * @param list The financial list on which the command will operate.
     */
    @Override
    public void execute(FinancialList list) throws FinanceBuddyException {
        if (list == null) {
            logger.log(Level.SEVERE, "Financial list is null");
            assert list != null : "Financial list cannot be null";
            throw new FinanceBuddyException("Financial list cannot be null");
        }

        System.out.println(LINE_SEPARATOR);
        String entryList = "";
        int entryCount = 0;
        double cashflow = 0;
        list.clearCategoryTotals();

        for (int i = 0; i < list.getEntryCount(); i++) {
            FinancialEntry entry = list.getEntry(i);
            if (this.shouldBeIncluded(entry)) {
                entryList += (++entryCount) + ". " + entry + System.lineSeparator();
                if (entry instanceof Income) {
                    cashflow += entry.getAmount();
                    Income income = (Income) entry;
                    list.getTotalIncomeByCategory().merge(income.getCategory(), income.getAmount(), Double::sum);
                } else if (entry instanceof Expense) {
                    cashflow -= entry.getAmount();
                    Expense expense = (Expense) entry;
                    list.getTotalExpenseByCategory().merge(expense.getCategory(), expense.getAmount(), Double::sum);
                }
            }
        }

        if (entryCount == 0) {
            System.out.println(this.getNoEntriesMessage());
            System.out.println(LINE_SEPARATOR);
            return;
        }

        System.out.println(this.getEntriesListedMessage());
        System.out.println(entryList);
        String cashflowString = this.getCashflowString(cashflow);
        System.out.println(this.getCashflowHeader() + cashflowString);
        System.out.println();
        System.out.println(getHighestCategoryInfo(list));
        System.out.println(LINE_SEPARATOR);
    }
}
