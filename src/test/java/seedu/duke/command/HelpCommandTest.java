package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.financial.FinancialList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the HelpCommand.
 */
class HelpCommandTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FinancialList financialList;
    private HelpCommand testCommand;

    /**
     * Set up the test environment before each test.
     * Initializes the FinancialList and SeeAllIncomesCommand instances,
     * and redirects System.out to capture console output.
     */
    @BeforeEach
    void setUp() {
        financialList = new FinancialList();
        testCommand = new HelpCommand();
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
     * Test the execute method.
     * Expects help menu to be printed out.
     */
    @Test
    void execute_printHelpMenu() {
        testCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput = "--------------------------------------------\n" +
                "List of commands:\n" +
                "--------------------------------------------\n" +
                "1. list [income|expense]\n " +
                "   - Shows a list of logged transactions\n" +
                "    - Optional: Specify 'income' or 'expense' to filter the list\n" +
                "2. expense DESCRIPTION /a AMOUNT\n " +
                "   - Adds a new expense\n" +
                "3. income DESCRIPTION /a AMOUNT\n " +
                "   - Adds a new income\n" +
                "4. edit INDEX [/des DESCRIPTION] [/a AMOUNT]\n " +
                "   - Edits the transaction at the specified INDEX\n" +
                "5. delete INDEX\n " +
                "   - Deletes the transaction at the specified INDEX\n" +
                "6. exit\n " +
                "   - Exits the program\n" +
                "7. help\n " +
                "   - Shows a list of all valid commands\n" +
                "--------------------------------------------\n";

        assertEquals(expectedOutput, output);
    }
}
