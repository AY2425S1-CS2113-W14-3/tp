package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the SeeAllEntriesCommand.
 * This class contains unit tests to verify the functionality of the SeeAllEntriesCommand
 * when executed on a FinancialList containing various financial entries.
 */
class SeeAllEntriesCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FinancialList financialList;
    private SeeAllEntriesCommand testCommand;

    /**
     * Set up the test environment before each test.
     * Initializes the FinancialList and SeeAllEntriesCommand instances,
     * and redirects System.out to capture console output.
     */
    @BeforeEach
    void setUp() {
        financialList = new FinancialList();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Clean up the test environment after each test.
     * Restores the original System.out PrintStream.
     */
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Test the execute method with a mixed list of incomes and expenses.
     * Expects all entries to be printed with indexes.
     */
    @Test
    void execute_mixedList_expectPrintedList() throws FinanceBuddyException {
        testCommand = new SeeAllEntriesCommand(null, null);
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24,10,22), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24,10,22), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24,10,22), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24,10,22),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24,10,22), Income.Category.GIFT));
        financialList.addEntry(new Income(15.00, "ang pow money", LocalDate.of(24,10,22), Income.Category.GIFT));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded entries:" + System.lineSeparator() +
                "1. [Expense] - lunch $ 3.50 (on 22/10/24) [FOOD]" + System.lineSeparator() +
                "2. [Income] - salary $ 3000.00 (on 22/10/24) [SALARY]" + System.lineSeparator() +
                "3. [Expense] - dinner $ 4.50 (on 22/10/24) [FOOD]" + System.lineSeparator() +
                "4. [Expense] - movie ticket $ 20.00 (on 22/10/24) [ENTERTAINMENT]" + System.lineSeparator() +
                "5. [Income] - allowance $ 100.00 (on 22/10/24) [GIFT]" + System.lineSeparator() +
                "6. [Income] - ang pow money $ 15.00 (on 22/10/24) [GIFT]" + System.lineSeparator() +
                System.lineSeparator() +
                "Net cashflow: $ 3087.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: ENTERTAINMENT ($20.00)" + System.lineSeparator() +
                "Highest Income Category: SALARY ($3000.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    /**
     * Test the execute method with an empty list.
     * Expects a message indicating no entries were found.
     */
    @Test
    void execute_emptyList_expectNothing() throws FinanceBuddyException {
        testCommand = new SeeAllEntriesCommand(null, null);
        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "No entries found." + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    @Test
    void execute_listBeforeCertainDate_expectSomeEntries() throws FinanceBuddyException {
        testCommand = new SeeAllEntriesCommand(null, LocalDate.of(24, 10, 10));
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24, 10, 23), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24, 11, 2), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24, 9, 1), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24, 10, 1),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24, 10, 10), Income.Category.GIFT));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded entries:" + System.lineSeparator() +
                "1. [Expense] - dinner $ 4.50 (on 01/09/24) [FOOD]" + System.lineSeparator() +
                "2. [Expense] - movie ticket $ 20.00 (on 01/10/24) [ENTERTAINMENT]" + System.lineSeparator() +
                System.lineSeparator() +
                "Net cashflow: $ -24.50" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: ENTERTAINMENT ($20.00)" + System.lineSeparator() +
                "Highest Income Category: UNCATEGORIZED ($0.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }

    @Test
    void execute_listAfterCertainDate_expectSomeEntries() throws FinanceBuddyException {
        testCommand = new SeeAllEntriesCommand(LocalDate.of(24, 10, 10), null);
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24, 10, 23),
                Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24, 11, 2),
                Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24, 9, 1), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24, 10, 1),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24, 10, 10), Income.Category.GIFT));


        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded entries:" + System.lineSeparator() +
                "1. [Expense] - lunch $ 3.50 (on 23/10/24) [FOOD]" + System.lineSeparator() +
                "2. [Income] - salary $ 3000.00 (on 02/11/24) [SALARY]" + System.lineSeparator() +
                System.lineSeparator() +
                "Net cashflow: $ 2996.50" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: FOOD ($3.50)" + System.lineSeparator() +
                "Highest Income Category: SALARY ($3000.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() ;
        assertEquals(expectedOutput, output);
    }

    @Test
    void execute_listBeforeAndAfterCertainDate_expectSomeEntries() throws FinanceBuddyException {
        testCommand = new SeeAllEntriesCommand(LocalDate.of(24, 10, 10), LocalDate.of(24, 11, 1));
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24, 10, 23), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24, 11, 2), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24, 9, 1), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24, 10, 1),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24, 10, 10), Income.Category.GIFT));


        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded entries:" + System.lineSeparator() +
                "1. [Expense] - lunch $ 3.50 (on 23/10/24) [FOOD]" + System.lineSeparator() +
                System.lineSeparator() +
                "Net cashflow: $ -3.50" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: FOOD ($3.50)" + System.lineSeparator() +
                "Highest Income Category: UNCATEGORIZED ($0.00)" + System.lineSeparator()+
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, output);
    }
}
