package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.financial.FinancialList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the AddExpenseCommand.
 * This class contains unit tests to verify the functionality of the AddExpenseCommand
 * when adding a new expense to the financial list.
 */
class AddExpenseCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FinancialList financialList;
    private AddExpenseCommand addExpenseCommand;

    /**
     * Set up the test environment before each test.
     * Initializes the FinancialList and redirects System.out to capture console output.
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
     * Test the execute method of AddExpenseCommand.
     * Verifies that the expense is added to the financial list and that the correct output is printed.
     */
    @Test
    void execute_addExpense_expectAddedToFinancialList() {
        addExpenseCommand = new AddExpenseCommand(50.00, "groceries");
        addExpenseCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this expense:" + System.lineSeparator() +
                "[Expense] - groceries $ 50.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(1, financialList.getEntryCount());  // Verify the entry count
        assertEquals(expectedOutput, output);  // Verify the printed output
    }

    /**
     * Test adding multiple expenses to the financial list.
     * Verifies that all expenses are added correctly and that the output is printed for each.
     */
    @Test
    void execute_addMultipleExpenses_expectAllAddedToFinancialList() {
        addExpenseCommand = new AddExpenseCommand(30.00, "lunch");
        addExpenseCommand.execute(financialList);

        addExpenseCommand = new AddExpenseCommand(100.00, "electronics");
        addExpenseCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this expense:" + System.lineSeparator() +
                "[Expense] - lunch $ 30.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this expense:" + System.lineSeparator() +
                "[Expense] - electronics $ 100.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(2, financialList.getEntryCount());  // Verify the entry count
        assertEquals(expectedOutput, output);  // Verify the printed output for both
    }
}
