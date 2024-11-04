package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
     * Verifies that the income is added to the financial list and that the correct output is printed
     * when a specific date is provided.
     *
     * @throws FinanceBuddyException if the date is invalid or other issues occur while adding the income
     */
    @Test
    void execute_addIncome_expectAddedToFinancialList() throws FinanceBuddyException {
        String specificDate = "14/10/24";
        addIncomeCommand = new AddIncomeCommand(500.0, "allowance", specificDate, Income.Category.SALARY);
        addIncomeCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this income:" + System.lineSeparator() +
                "[Income] - allowance $ 500.00 (on " + specificDate + ") [SALARY]" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(1, financialList.getEntryCount());  // Verify the entry count
        Income addedIncome = (Income) financialList.getEntry(0);
        assertEquals(500.0, addedIncome.getAmount());
        assertEquals("allowance", addedIncome.getDescription());
        assertEquals(LocalDate.of(2024, 10, 14), addedIncome.getDate());
        assertEquals(Income.Category.SALARY, addedIncome.getCategory());
        assertEquals(expectedOutput, output);  // Verify the printed output
    }


    /**
     * Test adding multiple incomes to the financial list.
     * Verifies that all incomes are added correctly and that the output is printed for each.
     *
     * @throws FinanceBuddyException if any issues occur while adding the incomes
     */
    @Test
    void execute_addMultipleIncome_expectAllAddedToFinancialList() throws FinanceBuddyException {
        String earlierDate = "21/10/24";
        String laterDate = "23/10/24";
        addIncomeCommand = new AddIncomeCommand(400, "Cost of Living payment", earlierDate,
                Income.Category.GIFT);
        addIncomeCommand.execute(financialList);

        addIncomeCommand = new AddIncomeCommand(10.50, "friend return money", laterDate,
                Income.Category.OTHER);
        addIncomeCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this income:" + System.lineSeparator() +
                "[Income] - Cost of Living payment $ 400.00 (on "+ earlierDate + ") [GIFT]" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "Got it! I've added this income:" + System.lineSeparator() +
                "[Income] - friend return money $ 10.50 (on " + laterDate + ") [OTHER]" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator();

        assertEquals(2, financialList.getEntryCount());  // Verify the entry count
        Income firstIncome = (Income) financialList.getEntry(0); //Assert first income index
        assertEquals(400.0, firstIncome.getAmount());
        assertEquals("Cost of Living payment", firstIncome.getDescription());
        assertEquals(LocalDate.of(2024, 10, 21), firstIncome.getDate());
        assertEquals(Income.Category.GIFT, firstIncome.getCategory());
        Income secondIncome = (Income) financialList.getEntry(1); //Assert second income index
        assertEquals(10.50, secondIncome.getAmount());
        assertEquals("friend return money", secondIncome.getDescription());
        assertEquals(LocalDate.of(2024,10,23), secondIncome.getDate());
        assertEquals(Income.Category.OTHER, secondIncome.getCategory());

        assertEquals(expectedOutput, output);  // Verify the printed output for both
    }

    /**
     * Test adding multiple incomes to the financial list, not in order of date.
     * Verifies that all incomes are added correctly and sorted by date within the list.
     *
     * @throws FinanceBuddyException if any issues occur while adding the incomes
     */
    @Test
    void execute_addMultipleIncomeNotInDateOrder_expectSortedByDate() throws FinanceBuddyException {
        String dateOne = "21/10/24";
        String dateTwo = "23/10/24";
        String dateThree = "11/09/24";

        addIncomeCommand = new AddIncomeCommand(400, "Cost of Living payment", dateOne,
                Income.Category.GIFT);
        addIncomeCommand.execute(financialList);

        addIncomeCommand = new AddIncomeCommand(10.50, "friend return money", dateTwo,
                Income.Category.OTHER);
        addIncomeCommand.execute(financialList);

        addIncomeCommand = new AddIncomeCommand(5.00, "rebate", dateThree, Income.Category.OTHER);
        addIncomeCommand.execute(financialList);

        String output = outputStream.toString();
        String expectedOutput =
                "--------------------------------------------" + System.lineSeparator() +
                        "Got it! I've added this income:" + System.lineSeparator() +
                        "[Income] - Cost of Living payment $ 400.00 (on "+ dateOne + ") [GIFT]" +
                        System.lineSeparator() +
                        "--------------------------------------------" + System.lineSeparator() +
                        "--------------------------------------------" + System.lineSeparator() +
                        "Got it! I've added this income:" + System.lineSeparator() +
                        "[Income] - friend return money $ 10.50 (on " + dateTwo + ") [OTHER]" +
                        System.lineSeparator() +
                        "--------------------------------------------" + System.lineSeparator() +
                        "--------------------------------------------" + System.lineSeparator() +
                        "Got it! I've added this income:" + System.lineSeparator() +
                        "[Income] - rebate $ 5.00 (on " + dateThree + ") [OTHER]" + System.lineSeparator() +
                        "--------------------------------------------" + System.lineSeparator();

        assertEquals(3, financialList.getEntryCount());  // Verify the entry count
        Income firstIncome = (Income) financialList.getEntry(0); //Assert first income index
        assertEquals(5.0, firstIncome.getAmount());
        assertEquals("rebate", firstIncome.getDescription());
        assertEquals(LocalDate.of(2024, 9, 11), firstIncome.getDate());
        assertEquals(Income.Category.OTHER, firstIncome.getCategory());
        Income secondIncome = (Income) financialList.getEntry(1); //Assert first income index
        assertEquals(400.0, secondIncome.getAmount());
        assertEquals("Cost of Living payment", secondIncome.getDescription());
        assertEquals(LocalDate.of(2024, 10, 21), secondIncome.getDate());
        assertEquals(Income.Category.GIFT, secondIncome.getCategory());
        Income thirdIncome = (Income) financialList.getEntry(2); //Assert second income index
        assertEquals(10.50, thirdIncome.getAmount());
        assertEquals("friend return money", thirdIncome.getDescription());
        assertEquals(LocalDate.of(2024,10,23), thirdIncome.getDate());
        assertEquals(Income.Category.OTHER, thirdIncome.getCategory());

        assertEquals(expectedOutput, output);  // Verify the printed output for both
    }

    /**
     * Test the execute method of AddIncomeCommand with a negative amount.
     * Verifies that a FinanceBuddyException is thrown, and no entries are added to financiallist.
     */
    @Test
    void execute_addIncomeWithNegativeAmount_expectErrorMessage() {

        Exception exception = assertThrows(FinanceBuddyException.class, () -> {
            addIncomeCommand = new AddIncomeCommand(-15.20, "grab", null,
                    Income.Category.UNCATEGORIZED);
            addIncomeCommand.execute(financialList);
        });

        // Verify the error message
        assertEquals("Invalid amount. Amount must be $0.01 or greater.", exception.getMessage());
        assertEquals(0, financialList.getEntryCount());
    }

    /**
     * Test the execute method of AddIncomeCommand with a very small amount.
     * Verifies that a FinanceBuddyException is thrown, and no entries are added to financiallist.
     */
    @Test
    void execute_addIncomeWithVerySmallAmount_expectErrorMessage() {

        Exception exception = assertThrows(FinanceBuddyException.class, () -> {
            addIncomeCommand = new AddIncomeCommand(0.0001, "random", null,
                    Income.Category.UNCATEGORIZED);
            addIncomeCommand.execute(financialList);
        });

        // Verify the error message
        assertEquals("Invalid amount. Amount must be $0.01 or greater.", exception.getMessage());
        assertEquals(0, financialList.getEntryCount());
    }

    /**
     * Test the execute method of AddIncomeCommand with a date after the system date.
     * Verifies that a FinanceBuddyException is thrown, and no entries are added to financiallist.
     */
    @Test
    void execute_addIncomeWithDateAfterCurrentDate_expectErrorMessage() {
        LocalDate laterDate = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String laterDateAsString = laterDate.format(formatter);

        Exception exception = assertThrows(FinanceBuddyException.class, () -> {
            addIncomeCommand = new AddIncomeCommand(1, "random", laterDateAsString, Income.Category.OTHER);
            addIncomeCommand.execute(financialList);
        });

        // Verify the error message
        assertEquals("Income date cannot be after current date.", exception.getMessage());
        assertEquals(0, financialList.getEntryCount());
    }
}
