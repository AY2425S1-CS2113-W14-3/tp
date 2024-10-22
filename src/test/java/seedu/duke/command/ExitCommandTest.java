package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.financial.FinancialList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class ExitCommandTest {

    @Test
    public void testExecute() {
        ExitCommand exitCommand = new ExitCommand();
        FinancialList list = new FinancialList();

        final String goodByeMessage = "--------------------------------------------\n" +
                "Goodbye! Hope to see you again soon :)\n" +
                "--------------------------------------------\n";

        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        exitCommand.execute(list);

        // Restore the original System.out
        System.setOut(originalOut);

        assertEquals(goodByeMessage + System.lineSeparator(), outputStream.toString());
    }

    @Test
    public void testIsExit() {
        ExitCommand exitCommand = new ExitCommand();
        assertFalse(exitCommand.shouldContinueLoop());
    }
}
