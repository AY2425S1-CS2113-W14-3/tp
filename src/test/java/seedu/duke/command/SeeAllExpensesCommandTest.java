package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

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
    public void execute_noExpenses_printsNoRecordedExpenses() throws FinanceBuddyException {
        FinancialEntry income1 = new Income(10.0, "bonus", LocalDate.of(24,10,22),
                Income.Category.GIFT);
        FinancialEntry income2 = new Income(15.5, "salary", LocalDate.of(24,10,22),
                Income.Category.SALARY);
        financialList.addEntry(income1);
        financialList.addEntry(income2);
        seeAllExpensesCommand = new SeeAllExpensesCommand(null, null);
        seeAllExpensesCommand.execute(financialList);
        assertEquals("--------------------------------------------" + System.lineSeparator() +
                "No expenses found." + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator(), outContent.toString());
    }

    /**
     * Tests the execute method of SeeAllExpensesCommand when there are expenses in the financial list.
     * This test case verifies that the execute method correctly prints all the expenses in the financial list.
     * It adds two expenses and two incomes to the financial list and then calls the execute method.
     * The expected output is a formatted string listing all the expenses.
     * The test asserts that the actual output matches the expected output.
     */
    @Test
    public void execute_withExpenses_printsAllExpenses() throws FinanceBuddyException {
        FinancialEntry expense1 = new Expense(10.0, "food", LocalDate.of(24, 10, 22),
                Expense.Category.FOOD);
        FinancialEntry expense2 = new Expense(5.0, "transport", LocalDate.of(24, 10, 12),
                Expense.Category.TRANSPORT);
        FinancialEntry income1 = new Income(10.0, "bonus", LocalDate.of(24, 10, 22),
                Income.Category.GIFT);
        FinancialEntry income2 = new Income(15.5, "salary", LocalDate.of(24, 10, 12),
                Income.Category.SALARY);
        financialList.addEntry(expense1);
        financialList.addEntry(expense2);
        financialList.addEntry(income1);
        financialList.addEntry(income2);

        seeAllExpensesCommand = new SeeAllExpensesCommand(null, null);
        seeAllExpensesCommand.execute(financialList);

        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded expenses:" + System.lineSeparator() +
                "1. " + expense2 + System.lineSeparator() +
                "2. " + expense1 + System.lineSeparator() +
                System.lineSeparator() +
                "Total expense: $ 15.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: FOOD ($10.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Test the execute method, specifying that only Expenses up to 20/10/24 inclusive should be printed.
     */
    @Test
    public void execute_beforeDate_printSomeExpenses() throws FinanceBuddyException {
        FinancialEntry expense1 = new Expense(10.0, "food", LocalDate.of(24, 10, 22),
                Expense.Category.FOOD);
        FinancialEntry expense2 = new Expense(5.0, "transport", LocalDate.of(24, 10, 12),
                Expense.Category.TRANSPORT);
        FinancialEntry expense3 = new Expense(10.0, "table", LocalDate.of(24,10,20),
                Expense.Category.OTHER);
        FinancialEntry income1 = new Income(10.0, "bonus", LocalDate.of(24, 10, 22),
                Income.Category.GIFT);
        FinancialEntry income2 = new Income(15.5, "salary", LocalDate.of(24, 10, 12),
                Income.Category.SALARY);
        financialList.addEntry(expense1);
        financialList.addEntry(expense2);
        financialList.addEntry(expense3);
        financialList.addEntry(income1);
        financialList.addEntry(income2);

        seeAllExpensesCommand = new SeeAllExpensesCommand(null, LocalDate.of(24, 10, 20));
        seeAllExpensesCommand.execute(financialList);

        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded expenses:" + System.lineSeparator() +
                "1. " + expense2 + System.lineSeparator() +
                "2. " + expense3 + System.lineSeparator() +
                System.lineSeparator() +
                "Total expense: $ 15.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: OTHER ($10.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Test the execute method, specifying that only Expenses starting from 20/10/24 inclusive should be printed.
     */
    @Test
    public void execute_afterDate_printSomeExpenses() throws FinanceBuddyException {
        FinancialEntry expense1 = new Expense(9.0, "food", LocalDate.of(24, 10, 22),
                Expense.Category.FOOD);
        FinancialEntry expense2 = new Expense(5.0, "transport", LocalDate.of(24, 10, 12),
                Expense.Category.TRANSPORT);
        FinancialEntry expense3 = new Expense(10.0, "table", LocalDate.of(24,10,20),
                Expense.Category.OTHER);
        FinancialEntry income1 = new Income(10.0, "bonus", LocalDate.of(24, 10, 22),
                Income.Category.GIFT);
        FinancialEntry income2 = new Income(15.5, "salary", LocalDate.of(24, 10, 12),
                Income.Category.SALARY);
      
        financialList.addEntry(expense1);
        financialList.addEntry(expense2);
        financialList.addEntry(expense3);
        financialList.addEntry(income1);
        financialList.addEntry(income2);

        seeAllExpensesCommand = new SeeAllExpensesCommand(LocalDate.of(24, 10, 20), null);
        seeAllExpensesCommand.execute(financialList);

        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded expenses:" + System.lineSeparator() +
                "1. " + expense3 + System.lineSeparator() +
                "2. " + expense1 + System.lineSeparator() +
                System.lineSeparator() +
                "Total expense: $ 19.00" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: OTHER ($10.00)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Test the execute method, specifying that only Expenses
     * between 15/10/24 and 21/10/24 inclusive should be printed.
     */
    @Test
    public void execute_beforeAndAfterDate_printSomeExpenses() throws FinanceBuddyException {
        FinancialEntry expense1 = new Expense(10.0, "food", LocalDate.of(24, 10, 22),
                Expense.Category.FOOD);
        FinancialEntry expense2 = new Expense(5.0, "transport", LocalDate.of(24, 10, 12),
                Expense.Category.TRANSPORT);
        FinancialEntry expense3 = new Expense(15.5, "snacks", LocalDate.of(24, 10, 20),
                Expense.Category.FOOD);
        FinancialEntry expense4 = new Expense(10.0, "table", LocalDate.of(24, 10, 21),
                Expense.Category.OTHER);
        FinancialEntry expense5 = new Expense(7.0, "shampoo", LocalDate.of(24, 10, 15),
                Expense.Category.UTILITIES);
        FinancialEntry income1 = new Income(10.0, "bonus", LocalDate.of(24, 10, 22),
                Income.Category.GIFT);
        FinancialEntry income2 = new Income(15.5, "salary", LocalDate.of(24, 10, 12),
                Income.Category.SALARY);

        financialList.addEntry(expense1);
        financialList.addEntry(expense2);
        financialList.addEntry(expense3);
        financialList.addEntry(income1);
        financialList.addEntry(income2);
        financialList.addEntry(expense4);
        financialList.addEntry(expense5);

        seeAllExpensesCommand = new SeeAllExpensesCommand(LocalDate.of(24, 10, 15), LocalDate.of(24, 10, 21));
        seeAllExpensesCommand.execute(financialList);

        String expectedOutput = "--------------------------------------------" + System.lineSeparator() +
                "Here's a list of all recorded expenses:" + System.lineSeparator() +
                "1. " + expense5 + System.lineSeparator() +
                "2. " + expense3 + System.lineSeparator() +
                "3. " + expense4 + System.lineSeparator() +
                System.lineSeparator() +
                "Total expense: $ 32.50" + System.lineSeparator() +
                System.lineSeparator() +
                "Highest Expense Category: FOOD ($15.50)" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
