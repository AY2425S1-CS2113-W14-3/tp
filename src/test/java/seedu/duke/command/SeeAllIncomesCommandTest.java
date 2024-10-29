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
 * Test class for the SeeAllIncomesCommand.
 * This class contains unit tests to verify the functionality of the SeeAllIncomesCommand
 * when executed on a FinancialList containing various financial entries.
 */
class SeeAllIncomesCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FinancialList financialList;
    private SeeAllIncomesCommand testCommand;

    /**
     * Set up the test environment before each test.
     * Initializes the FinancialList and SeeAllIncomesCommand instances,
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
     * Expects only income entries to be printed with index relative to
     * income entries only.
     */
    @Test
    void execute_mixedList_expectPrintedIncomes() throws FinanceBuddyException {
        testCommand = new SeeAllIncomesCommand(null, null);
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24,10,22), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24,10,22), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24,10,22), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24,10,22),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24,10,22), Income.Category.GIFT));
        financialList.addEntry(new Income(15.00, "ang pow money", LocalDate.of(24,10,22), Income.Category.GIFT));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded incomes:" + System.lineSeparator() +
                "1. [Income] - salary $ 3000.00 (on 22/10/24) [SALARY]" + System.lineSeparator() +
                "2. [Income] - allowance $ 100.00 (on 22/10/24) [GIFT]" + System.lineSeparator() +
                "3. [Income] - ang pow money $ 15.00 (on 22/10/24) [GIFT]" + System.lineSeparator() +
                System.lineSeparator() +
                "Total income: $ 3115.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Income Category: SALARY ($3000.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    /**
     * Test the execute method with a list containing only expenses.
     * Expects a message indicating no incomes were found.
     */
    @Test
    void execute_onlyExpenseList_expectNothing() throws FinanceBuddyException {
        testCommand = new SeeAllIncomesCommand(null, null);
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.now(), Expense.Category.FOOD));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.now(), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.now(),
                Expense.Category.ENTERTAINMENT));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "No incomes found." + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    /**
     * Test the execute method, specifying that only Incomes before 10/10/24 should be printed.
     */
    @Test
    void execute_mixedListBeforeCertainDate_expectPrintedIncomes() throws FinanceBuddyException {
        testCommand = new SeeAllIncomesCommand(null, LocalDate.of(24, 10, 10));
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24, 10, 10), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24, 10, 1), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24, 10, 10), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24, 10, 10),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24, 11, 2), Income.Category.GIFT));
        financialList.addEntry(new Income(15.00, "ang pow money", LocalDate.of(24, 9, 12), Income.Category.GIFT));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded incomes:" + System.lineSeparator() +
                "1. [Income] - ang pow money $ 15.00 (on 12/09/24) [GIFT]" + System.lineSeparator() +
                "2. [Income] - salary $ 3000.00 (on 01/10/24) [SALARY]" + System.lineSeparator() +
                System.lineSeparator() +
                "Total income: $ 3015.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Income Category: SALARY ($3000.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    /**
     * Test the execute method, specifying that only Incomes after 10/10/24 should be printed.
     */
    @Test
    void execute_mixedListAfterCertainDate_expectPrintedIncomes() throws FinanceBuddyException {
        testCommand = new SeeAllIncomesCommand(LocalDate.of(24, 10, 10), null);
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24, 10, 10), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24, 10, 1), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24, 10, 10), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24, 10, 10),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24, 11, 2), Income.Category.GIFT));
        financialList.addEntry(new Income(15.00, "ang pow money", LocalDate.of(24, 9, 12), Income.Category.GIFT));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded incomes:" + System.lineSeparator() +
                "1. [Income] - allowance $ 100.00 (on 02/11/24) [GIFT]" + System.lineSeparator() +
                System.lineSeparator() +
                "Total income: $ 100.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Income Category: GIFT ($100.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    /**
     * Test the execute method, specifying that only Incomes
     * between 20/9/2024 and 10/10/24 exclusive should be printed.
     */
    @Test
    void execute_mixedListBeforeAndAfterCertainDate_expectPrintedIncomes() throws FinanceBuddyException {
        testCommand = new SeeAllIncomesCommand(LocalDate.of(24, 9, 20), LocalDate.of(24, 10, 10));
        financialList.addEntry(new Expense(3.50, "lunch", LocalDate.of(24, 10, 10), Expense.Category.FOOD));
        financialList.addEntry(new Income(3000.00, "salary", LocalDate.of(24, 10, 1), Income.Category.SALARY));
        financialList.addEntry(new Expense(4.50, "dinner", LocalDate.of(24, 10, 10), Expense.Category.FOOD));
        financialList.addEntry(new Expense(20.00, "movie ticket", LocalDate.of(24, 10, 10),
                Expense.Category.ENTERTAINMENT));
        financialList.addEntry(new Income(100.00, "allowance", LocalDate.of(24, 11, 2), Income.Category.GIFT));
        financialList.addEntry(new Income(15.00, "ang pow money", LocalDate.of(24, 9, 12), Income.Category.GIFT));
        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded incomes:" + System.lineSeparator() +
                "1. [Income] - salary $ 3000.00 (on 01/10/24) [SALARY]" + System.lineSeparator() +
                System.lineSeparator() +
                "Total income: $ 3000.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Income Category: SALARY ($3000.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }
}
