package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Expense;
import seedu.duke.financial.Income;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the DeleteCommand.
 * This class contains unit tests to verify the functionality of the DeleteCommand
 * when removing an entry from the financial list using a 1-based index.
 */
class DeleteCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FinancialList financialList;
    private DeleteCommand deleteCommand;

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
     * Test the execute method of DeleteCommand.
     * Verifies that an entry is deleted from the financial list when a valid 1-based index is given,
     * and checks that the correct output is printed.
     * Additionally, verifies that the correct entry was deleted by checking the remaining entries.
     */
    @Test
    void execute_deleteEntry_expectEntryRemoved() {
        financialList.addEntry(new Expense(3.50, "lunch"));
        financialList.addEntry(new Income(3000.00, "salary"));
        financialList.addEntry(new Expense(20.00, "movie ticket"));

        deleteCommand = new DeleteCommand(2);  // Delete the second entry (1-based index, "salary")
        deleteCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Okay! The following entry has been deleted: " + System.lineSeparator() +
                "[Income] - salary $ 3000.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        // Verify the correct message is printed
        assertEquals(expectedOutput, output);

        // Verify the correct entry was deleted
        assertEquals(2, financialList.getEntryCount());  // Two entries should remain

        // Check the remaining entries by description to ensure the correct entry was deleted
        assertEquals("lunch", financialList.getEntry(0).getDescription());
        assertEquals("movie ticket", financialList.getEntry(1).getDescription());
    }

    /**
     * Test the execute method of DeleteCommand.
     * Verifies that an entry is deleted from the financial list when the last valid index is given,
     * and checks that the correct output is printed.
     * Additionally, verifies that the correct entry was deleted by checking the remaining entries.
     */
    @Test
    void execute_deleteLastEntry_expectEntryRemoved() {
        financialList.addEntry(new Expense(3.50, "lunch"));
        financialList.addEntry(new Income(3000.00, "salary"));
        financialList.addEntry(new Expense(20.00, "movie ticket"));

        deleteCommand = new DeleteCommand(3);  // Delete the 3rd entry
        deleteCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Okay! The following entry has been deleted: " + System.lineSeparator() +
                "[Expense] - movie ticket $ 20.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        // Verify the correct message is printed
        assertEquals(expectedOutput, output);

        // Verify the correct entry was deleted
        assertEquals(2, financialList.getEntryCount());  // Two entries should remain

        // Check the remaining entries by description to ensure the correct entry was deleted
        assertEquals("lunch", financialList.getEntry(0).getDescription());
        assertEquals("salary", financialList.getEntry(1).getDescription());
    }

    /**
     * Test the execute method of DeleteCommand with an invalid 1-based index.
     * Verifies that an error message is printed and no entry is deleted when an invalid index is given.
     */
    @Test
    void execute_invalidIndex_expectError() {
        financialList.addEntry(new Expense(3.50, "lunch"));

        deleteCommand = new DeleteCommand(3);  // Invalid 1-based index
        deleteCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "OOPS!!! The entry does not exist." + System.lineSeparator();

        assertEquals(expectedOutput, output);  // Verify the error message
        assertEquals(1, financialList.getEntryCount());  // Verify that no entry was deleted
    }

    /**
     * Test the execute method of DeleteCommand when there is only one entry.
     * Verifies that the last remaining entry is deleted successfully.
     */
    @Test
    void execute_deleteOnlyEntry_expectEntryRemoved() {
        financialList.addEntry(new Expense(50.00, "groceries"));  // Add one expense

        deleteCommand = new DeleteCommand(1);  // Delete the only entry (1-based index)
        deleteCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Okay! The following entry has been deleted: " + System.lineSeparator() +
                "[Expense] - groceries $ 50.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        // Verify the correct message is printed
        assertEquals(expectedOutput, output);

        // Verify the financial list is now empty
        assertEquals(0, financialList.getEntryCount());
    }
}
