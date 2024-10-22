package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        testCommand = new SeeAllIncomesCommand();
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
    void execute_mixedList_expectPrintedIncomes() {

        financialList.addEntry(new Expense(3.50, "lunch"));
        financialList.addEntry(new Income(3000.00, "salary"));
        financialList.addEntry(new Expense(4.50, "dinner"));
        financialList.addEntry(new Expense(20.00, "movie ticket"));
        financialList.addEntry(new Income(100.00, "allowance"));
        financialList.addEntry(new Income(15.00, "ang pow money"));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "Here's a list of all recorded incomes:" + System.lineSeparator() +
                "1. [Income] - salary $ 3000.00" + System.lineSeparator() +
                "2. [Income] - allowance $ 100.00" + System.lineSeparator() +
                "3. [Income] - ang pow money $ 15.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }

    /**
     * Test the execute method with a list containing only expenses.
     * Expects a message indicating no incomes were found.
     */
    @Test
    void execute_onlyExpenseList_expectNothing() {

        financialList.addEntry(new Expense(3.50, "lunch"));
        financialList.addEntry(new Expense(4.50, "dinner"));
        financialList.addEntry(new Expense(20.00, "movie ticket"));

        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "No recorded incomes found." + System.lineSeparator() +
            "--------------------------------------------" + System.lineSeparator();

        assertEquals(expectedOutput, output);
    }
}
