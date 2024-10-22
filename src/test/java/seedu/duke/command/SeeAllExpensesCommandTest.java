package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test class for SeeAllExpensesCommand.
 * This class contains unit tests for the SeeAllExpensesCommand class, 
 * which is responsible for displaying all recorded expenses.
 */
public class SeeAllExpensesCommandTest {

    private FinancialList financialList;
    private SeeAllExpensesCommand seeAllExpensesCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes the financial list and the see all expenses command.
     * Redirects the standard output to a PrintStream for capturing output.
     */
    @BeforeEach
    public void setUp() {
        financialList = new FinancialList();
        seeAllExpensesCommand = new SeeAllExpensesCommand();
        System.setOut(new PrintStream(outContent));
    }
    /**
     * Restores the original standard output stream after each test.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Tests the execute method of SeeAllExpensesCommand when there are no expenses recorded.
     * Verifies that the output message indicates no recorded expenses.
     */
    @Test
    public void execute_noExpenses_printsNoRecordedExpenses() {
        seeAllExpensesCommand.execute(financialList);
        assertEquals("No recorded expenses found." + System.lineSeparator() +
            "--------------------------------------------" + System.lineSeparator(), outContent.toString());
    }

    /**
     * Tests the execute method of SeeAllExpensesCommand when there are expenses in the financial list.
     * 
     * This test case verifies that the execute method correctly prints all the expenses in the financial list.
     * It adds two expenses to the financial list and then calls the execute method.
     * The expected output is a formatted string listing all the expenses.
     * The test asserts that the actual output matches the expected output.
     */
    @Test
    public void execute_withExpenses_printsAllExpenses() {
        FinancialEntry expense1 = new Expense(10.0, "food");
        FinancialEntry expense2 = new Expense(5.0, "transport");
        financialList.addEntry(expense1);
        financialList.addEntry(expense2);

        seeAllExpensesCommand.execute(financialList);

        String expectedOutput = "Here's a list of all recorded expenses:" + System.lineSeparator() +
                "1. " + expense1 + System.lineSeparator() +
                "2. " + expense2 + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
