package seedu.duke.ui;

import seedu.duke.command.AddExpenseCommand;
import seedu.duke.command.AddIncomeCommand;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.EditEntryCommand;
import seedu.duke.command.SeeAllEntriesCommand;
import seedu.duke.command.SeeAllExpensesCommand;
import seedu.duke.command.SeeAllIncomesCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.exception.FinanceBuddyException;
import seedu.duke.financial.FinancialEntry;
import seedu.duke.financial.FinancialList;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.InputParser;

import seedu.duke.storage.Storage;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents the user interface (UI) of the FinanceBuddy application.
 * Responsible for interacting with the user, capturing inputs,
 * and processing commands related to financial tracking.
 */
public class AppUi {
    public FinancialList financialList;
    public Storage mainStorage;

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
     *                         and the amount ("/a") and the date/time ("/dt")
     */
    public void addExpense(HashMap<String, String> commandArguments) {
        String description = commandArguments.get("argument");
        double amount = Double.parseDouble(commandArguments.get("/a"));
        String date = commandArguments.get("/d");

        try {
            AddExpenseCommand addExpenseCommand = new AddExpenseCommand(amount, description, date);
            addExpenseCommand.execute(financialList);
        } catch (FinanceBuddyException e) {
            System.out.println(e.getMessage());  // Display error message when invalid date is provided
        }

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
        String date = commandArguments.get("/d");

        try {
            AddIncomeCommand addIncomeCommand = new AddIncomeCommand(amount, description, date);
            addIncomeCommand.execute(financialList);
        } catch (FinanceBuddyException e) {
            System.out.println(e.getMessage());  // Display error message when invalid date is provided
        }

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

        assert index > 0 : "Index of entry to edit must be greater than 0";
        assert index <= financialList.getEntryCount() : "Index of entry to edit must be within the list size";

        FinancialEntry entry = financialList.getEntry(index - 1);

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
     * This method helps execute the appropriate command based on the "argument"
     * provided in the commandArguments HashMap. If the argument is "expense", it will
     * execute the SeeAllExpensesCommand. If the argument is "income", it will execute
     * the SeeAllIncomesCommand. If no argument or an unknown argument is provided,
     * it defaults to executing SeeAllEntriesCommand to list all entries.
     *
     * @param commandArguments A HashMap containing the command argument with the key "argument".
     *                         The value can be "expense", "income", or null/empty for listing all entries.
     */
    public void listHelper(HashMap<String, String> commandArguments) throws FinanceBuddyException {
        String type = commandArguments.get("argument");
        String start = commandArguments.get("/from");
        String end = commandArguments.get("/to");

        if ((start != null && start.isBlank()) || (end != null && end.isBlank())) {
            throw new FinanceBuddyException("Please enter a valid start/end date");
        }

        LocalDate startDate = start != null ? DateParser.parse(commandArguments.get("/from")) : null;
        LocalDate endDate = end != null ? DateParser.parse(commandArguments.get("/to")) : null;

        if (type != null) {
            if (type.equals("expense")) {
                SeeAllExpensesCommand seeAllExpensesCommand = new SeeAllExpensesCommand(startDate, endDate);
                seeAllExpensesCommand.execute(financialList);
            } else if (type.equals("income")) {
                SeeAllIncomesCommand seeAllIncomesCommand = new SeeAllIncomesCommand(startDate, endDate);
                seeAllIncomesCommand.execute(financialList);
            } else {
                System.out.println("Unknown argument: " + type);
                System.out.println("--------------------------------------------");
            }
        } else {
            SeeAllEntriesCommand seeAllEntriesCommand = new SeeAllEntriesCommand(startDate, endDate);
            seeAllEntriesCommand.execute(financialList);
        }
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

        final String unrecognizedCommand = "--------------------------------------------\n" +
                "Unrecognized command!\n" +
                "Use the command \"help\" for a list of valid commands\n" +
                "--------------------------------------------\n";

        switch (command) {
        case "list":
            try {
                listHelper(commandArguments);
            } catch (FinanceBuddyException e) {
                System.out.println(e.getMessage());
            }
            break;

        case "expense":
            addExpense(commandArguments);
            this.mainStorage.update(financialList);
            break;

        case "income":
            addIncome(commandArguments);
            this.mainStorage.update(financialList);
            break;

        case "edit":
            editEntry(commandArguments);
            this.mainStorage.update(financialList);
            break;

        case "delete":
            deleteEntry(commandArguments);
            this.mainStorage.update(financialList);
            break;

        case "help":
            printHelpMenu();
            break;

        case "exit":
            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute(financialList);
            return exitCommand.shouldContinueLoop();

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
     * Runs the main application logic.
     * Displays the welcome message, initializes the storage object,
     * and starts the command entry process.
     */
    public void run() {
        displayWelcomeMessage();

        // create a new storage object and load the financial list from the file
        this.mainStorage = new Storage();
        financialList = mainStorage.loadFromFile();
        
        commandEntry();
    }
}
