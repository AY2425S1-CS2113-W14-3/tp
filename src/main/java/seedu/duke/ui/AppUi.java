package seedu.duke.ui;

import seedu.duke.command.*;
import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.parser.InputParser;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents the user interface (UI) of the FinanceBuddy application.
 * Responsible for interacting with the user, capturing inputs,
 * and processing commands related to financial tracking.
 */
public class AppUi {
    public FinancialList financialList;

    /**
     * Constructor to initialize the AppUi object.
     * Initializes an empty FinancialList.
     */
    public AppUi() {
        financialList = new FinancialList();
    }

    /**
     * Adds a new expense entry to the financial list based on the provided command arguments.
     *
     * The method extracts the description and amount for the expense from the command arguments.
     * An {@link AddExpenseCommand} is created and executed to add the expense to the financial list.
     *
     * @param commandArguments A map of parsed command arguments that contains the description of the expense
     *                         and the amount ("/a").
     */
    public void addExpense(HashMap<String, String> commandArguments) {
        String description = commandArguments.get("argument");
        double amount = Double.parseDouble(commandArguments.get("/a"));

        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(amount, description);
        addExpenseCommand.execute(financialList);
    }

    /**
     * Adds a new income entry to the financial list based on the provided command arguments.
     *
     * The method extracts the description and amount for the income from the command arguments.
     * An {@link AddIncomeCommand} is created and executed to add the income to the financial list.
     *
     * @param commandArguments A map of parsed command arguments that contains the description of the income
     *                         and the amount ("/a").
     */
    public void addIncome(HashMap<String, String> commandArguments) {
        String description = commandArguments.get("argument");
        double amount = Double.parseDouble(commandArguments.get("/a"));

        AddIncomeCommand addIncomeCommand = new AddIncomeCommand(amount, description);
        addIncomeCommand.execute(financialList);

    }

    /**
     * Edits an existing financial entry in the financial list based on the provided command arguments.
     *
     * The method extracts the index of the entry to be edited, as well as new values for the amount
     * and description (if provided). If no new value is provided for amount or description, the
     * existing values are retained. Finally, an {@link EditEntryCommand} is created and executed
     * to apply the changes to the financial list.
     *
     * @param commandArguments A map of parsed command arguments that contains the entry index and
     *                         optional new values for the amount ("/a") and description ("/des").
     */
    public void editEntry(HashMap<String, String> commandArguments) {
        int index = Integer.parseInt(commandArguments.get("argument"));

        FinancialEntry entry = financialList.getEntry(index);

        String amountStr = commandArguments.get("/a");
        double amount = (amountStr != null) ? Double.parseDouble(amountStr) : entry.getAmount();

        String description = commandArguments.getOrDefault("/des", entry.getDescription());

        EditEntryCommand editEntryCommand = new EditEntryCommand(index, amount, description);
        editEntryCommand.execute(financialList);
    }

    /**
     * Deletes an existing entry from the financial list based on the provided command arguments.
     *
     * The method extracts the index of the entry to be deleted from the command arguments.
     * A {@link DeleteCommand} is created and executed to remove the entry from the financial list.
     *
     * @param commandArguments A map of parsed command arguments that contains the index of the entry
     *                         to be deleted.
     */
    public void deleteEntry(HashMap<String, String> commandArguments) {
        int index = Integer.parseInt(commandArguments.get("argument"));

        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.execute(financialList);
    }

    /**
     * Prints help menu when user inputs 'help' command.
     */
    public void printHelpMenu() {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute(financialList);
    }

    /**
     * Matches a given command with its corresponding action.
     *
     * @param command          The command input by the user.
     * @param commandArguments A map of arguments parsed from the user's input.
     * @return A boolean indicating whether the command was successful.
     */
    public boolean matchCommand(String command, HashMap<String, String> commandArguments) {
        final String goodByeMessage = "--------------------------------------------\n" +
                "Goodbye! Hope to see you again soon :)\n" +
                "--------------------------------------------\n";

        final String unrecognizedCommand = "--------------------------------------------\n" +
                "Unrecognized command!\n" +
                "Use the command \"help\" for a list of valid commands\n" +
                "--------------------------------------------\n";

        switch (command) {
        case "list":
            SeeAllEntriesCommand seeAllEntriesCommand = new SeeAllEntriesCommand();
            seeAllEntriesCommand.execute(financialList);
            break;

        case "expense":
            addExpense(commandArguments);
            break;

        case "income":
            addIncome(commandArguments);
            break;

        case "edit":
            editEntry(commandArguments);
            break;

        case "delete":
            deleteEntry(commandArguments);
            break;

        case "help":
            printHelpMenu();
            break;

        case "exit":
            System.out.println(goodByeMessage);
            return false;

        default:
            System.out.println(unrecognizedCommand);
            break;
        }

        return true;
    }

    /**
     * Continuously prompts the user for input and processes commands.
     * It runs in a loop until the input signifies to stop accepting commands.
     */
    public void commandEntry() {
        HashMap<String, String> commandArguments;
        String input;
        Scanner scanner = new Scanner(System.in);

        boolean isAcceptingInput = true;

        while (isAcceptingInput) {
            input = scanner.nextLine();
            commandArguments = InputParser.parseCommands(input);
            String command = commandArguments.get(InputParser.COMMAND);

            try {
                isAcceptingInput = matchCommand(command, commandArguments);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays the welcome message and the application logo to the user
     * upon starting the application.
     */

    public void displayWelcomeMessage() {
        final String logo = " ______  _                                   ____              _      _        \n" +
                "|  ____|(_)                                 |  _ \\            | |    | |       \n" +
                "| |__    _  _ __    __ _  _ __    ___   ___ | |_) | _   _   __| |  __| | _   _ \n" +
                "|  __|  | || '_ \\  / _` || '_ \\  / __| / _ \\|  _ < | | | | / _` | / _` || | | |\n" +
                "| |     | || | | || (_| || | | || (__ |  __/| |_) || |_| || (_| || (_| || |_| |\n" +
                "|_|     |_||_| |_| \\__,_||_| |_| \\___| \\___||____/  \\__,_| \\__,_| \\__,_| \\__, |\n" +
                "                                                                          __/ |\n" +
                "                                                                         |___/ \n";
        final String welcomeMessage = "--------------------------------------------\n" +
                "Welcome to FinanceBuddy!\n" +
                "Your one stop solution for financial peace of mind\n" +
                "How can I help you today? :)\n" +
                "--------------------------------------------\n";

        System.out.println(logo);
        System.out.println(welcomeMessage);
    }

    /**
     * Runs the application, starting with displaying the welcome message.
     */
    public void run() {
        displayWelcomeMessage();
        commandEntry();
    }
}
