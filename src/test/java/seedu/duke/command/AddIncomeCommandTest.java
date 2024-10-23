package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.FinancialList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the AddIncomeCommand.
 * This class contains unit tests to verify the functionality of the AddIncomeCommand
 * when adding a new expense to the financial list.
 */
class AddIncomeCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FinancialList financialList;
    private AddIncomeCommand addIncomeCommand;

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
     * Test the execute method of AddIncomeCommand.
     * Verifies that the income is added to the financial list and that the correct output is printed.
     */
    @Test
    void execute_addExpense_expectAddedToFinancialList() throws FinanceBuddyException {
        addIncomeCommand = new AddIncomeCommand(500.0, "allowance", "24/10/24");
        addIncomeCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this income:" + System.lineSeparator() +
                "[Income] - allowance $ 500.00 24/10/24" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(1, financialList.getEntryCount());  // Verify the entry count
        assertEquals(expectedOutput, output);  // Verify the printed output
    }


    /**
     * Test adding multiple incomes to the financial list.
     * Verifies that all incomes are added correctly and that the output is printed for each.
     */
    @Test
    void execute_addMultipleExpenses_expectAllAddedToFinancialList() throws FinanceBuddyException {
        addIncomeCommand = new AddIncomeCommand(400, "Cost of Living payment", "01/09/24");
        addIncomeCommand.execute(financialList);

        addIncomeCommand = new AddIncomeCommand(10.50, "friend return money", "01/10/24");
        addIncomeCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this income:" + System.lineSeparator() +
                "[Income] - Cost of Living payment $ 400.00 01/09/24" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this income:" + System.lineSeparator() +
                "[Income] - friend return money $ 10.50 01/10/24" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(2, financialList.getEntryCount());  // Verify the entry count
        assertEquals(expectedOutput, output);  // Verify the printed output for both
    }
}

