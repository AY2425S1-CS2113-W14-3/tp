package seedu.duke.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link AppUi} class.
 * These tests ensure that various commands are processed correctly by the {@code matchCommand} method.
 */
public class AppUiTest {

    private AppUi appUi;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * Sets up the test environment by initializing the {@link AppUi} instance and its financial list.
     */
    @BeforeEach
    void setUp() {
        appUi = new AppUi();
        appUi.financialList = new FinancialList();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Tests the behavior of the "list" command.
     * Ensures that the {@code matchCommand} method returns {@code true} and no exceptions are thrown.
     */
    @Test
    void testMatchCommand_listCommand() {
        // Prepare command arguments for the "list" command
        HashMap<String, String> commandArguments = new HashMap<>();
        commandArguments.put("command", "list");

        // Execute the command
        boolean result = appUi.matchCommand("list", commandArguments);

        // Validate that the result is true and no exceptions are thrown
        assertTrue(result);
    }

    @Test
    void testMatchCommand_seeAllExpensesCommand() {
        // add an expense to the financial list
        appUi.financialList.addEntry(new Expense(100, "Lunch"));
        // add an income to the financial list
        appUi.financialList.addEntry(new Income(100, "Salary"));

        // Prepare command arguments for the "seeAllExpenses" command
        HashMap<String, String> commandArguments = new HashMap<>();
        commandArguments.put("argument", "expense");

        // Execute the command
        appUi.matchCommand("list", commandArguments);

        String output = outputStream.toString();

        String expectedOutput = 
                "Here's a list of all recorded expenses:" + System.lineSeparator() +
                "1. [Expense] - Lunch $ 100.00" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        // Validate that the expected output is equal to the actual output
        assertEquals(expectedOutput, output);
    }

    /**
     * Tests the behavior of the "expense" command.
     * Ensures that an expense entry is added to the financial list and the method returns {@code true}.
     */
    @Test
    void testMatchCommand_expenseCommand() {
        HashMap<String, String> commandArguments = new HashMap<>();
        commandArguments.put("argument", "Lunch");
        commandArguments.put("/a", "12.00");

        boolean result = appUi.matchCommand("expense", commandArguments);

        // Validate that the result is true and that the financial list has an entry
        assertTrue(result);
        assertEquals(1, appUi.financialList.getEntryCount());
    }

    /**
     * Tests the behavior of the "income" command.
     * Ensures that an income entry is added to the financial list and the method returns {@code true}.
     */
    @Test
    void testMatchCommand_incomeCommand() {
        // Prepare command arguments for the "income" command
        HashMap<String, String> commandArguments = new HashMap<>();
        commandArguments.put("argument", "Salary");
        commandArguments.put("/a", "2500.00");

        // Execute the command
        boolean result = appUi.matchCommand("income", commandArguments);

        // Validate that the result is true and that the financial list has an entry
        assertTrue(result);
        assertEquals(1, appUi.financialList.getEntryCount());
    }

    /**
     * Tests the behavior of the "edit" command.
     * Ensures that an existing financial entry can be edited and the changes are applied correctly.
     */
    @Test
    void testMatchCommand_editCommand() {
        // Add an entry first to edit it later
        appUi.financialList.addEntry(new Expense(100, "Initial Entry"));

        // Prepare command arguments for the "edit" command
        HashMap<String, String> commandArguments = new HashMap<>();
        commandArguments.put("argument", "1");
        commandArguments.put("/a", "25.00");
        commandArguments.put("/des", "Edited Description");

        // Execute the command
        boolean result = appUi.matchCommand("edit", commandArguments);

        // Validate that the entry is edited
        assertTrue(result);
        FinancialEntry editedEntry = appUi.financialList.getEntry(0);
        assertEquals(25.00, editedEntry.getAmount());
        assertEquals("Edited Description", editedEntry.getDescription());
    }

    /**
     * Tests the behavior of the "delete" command.
     * Ensures that an entry can be deleted from the financial list and the method returns {@code true}.
     */
    @Test
    void testMatchCommand_deleteCommand() {
        // Add an entry first to delete it later
        appUi.financialList.addEntry(new Expense(100, "Entry to delete"));

        // Prepare command arguments for the "delete" command
        HashMap<String, String> commandArguments = new HashMap<>();
        commandArguments.put("argument", "1");  // Deleting first entry

        // Execute the command
        boolean result = appUi.matchCommand("delete", commandArguments);

        // Validate that the entry is deleted
        assertTrue(result);
        assertEquals(0, appUi.financialList.getEntryCount());
    }

    /**
     * Tests the behavior of the "help" command.
     * Ensures that the help menu is displayed and the method returns {@code true}.
     */
    @Test
    void testMatchCommand_helpCommand() {
        // Prepare command arguments for the "help" command
        HashMap<String, String> commandArguments = new HashMap<>();

        // Execute the command
        boolean result = appUi.matchCommand("help", commandArguments);

        // Validate that the result is true (help does not modify the financial list)
        assertTrue(result);
    }

    /**
     * Tests the behavior of the "exit" command.
     * Ensures that the method returns {@code false} to indicate that the program should exit.
     */
    @Test
    void testMatchCommand_exitCommand() {
        // Prepare command arguments for the "exit" command
        HashMap<String, String> commandArguments = new HashMap<>();

        // Execute the command
        boolean result = appUi.matchCommand("exit", commandArguments);

        // Validate that the result is false (indicating the program should exit)
        assertFalse(result);
    }

    /**
     * Tests the behavior of an unrecognized command.
     * Ensures that unrecognized commands are handled gracefully and the method returns {@code true}.
     */
    @Test
    void testMatchCommand_unrecognizedCommand() {
        // Prepare command arguments for an unrecognized command
        HashMap<String, String> commandArguments = new HashMap<>();

        // Execute the command
        boolean result = appUi.matchCommand("unknownCommand", commandArguments);

        // Validate that an unrecognized command does not cause errors and returns true
        assertTrue(result);
    }
}
