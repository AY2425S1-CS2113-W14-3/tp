package seedu.duke.storage;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.financial.Expense;
import seedu.duke.financial.FinancialList;
import seedu.duke.financial.Income;

/**
 * The Storage class handles the reading and writing of financial data to a storage file.
 * It ensures that the storage file and its parent directories are created if they do not exist.
 * The class provides methods to update the storage file with financial entries, parse expenses and incomes,
 * and load financial data from the storage file into a FinancialList.
 * 
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * Storage storage = new Storage();
 * FinancialList financialList = storage.loadFromFile();
 * storage.update(financialList);
 * }
 * </pre>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #getStorageFile()} - Retrieves the storage file, creating it if necessary.</li>
 *   <li>{@link #update(FinancialList)} - Updates the storage file with the entries from the given FinancialList.</li>
 *   <li>{@link #parseExpense(String[])} - Parses a string array into an Expense object.</li>
 *   <li>{@link #parseIncome(String[])} - Parses a string array into an Income object.</li>
 *   <li>{@link #loadFromFile()} - Loads financial data from the storage file into a FinancialList.</li>
 * </ul>
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li>{@link #logger} - Logger for logging information and errors.</li>
 *   <li>{@link #STORAGE_FILE_PATH} - Path to the storage file.</li>
 * </ul>
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    public static final String STORAGE_FILE_PATH = "data/FinancialList.txt";

    public Storage() {
    }

    /**
     * Retrieves the log file. If the storage file does not exist, it creates the file
     * and its parent directories if necessary.
     *
     * @return The storage file.
     */
    public static File getStorageFile() {
        File file = new File(STORAGE_FILE_PATH);
        // check if the file exists
        if (!file.exists()) {
            try {
                // check if the dictionary exists
                File directory = new File(file.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * Updates the storage file with the current entries in the provided FinancialList.
     * 
     * This method iterates through the entries in the given FinancialList, converts each entry
     * to its storage string representation, and writes it to the storage file. If the file does
     * not exist, it will be created. If an error occurs during the file writing process, the 
     * exception stack trace will be printed.
     * 
     * @param theList The FinancialList containing the entries to be written to the storage file.
     */
    public void update(FinancialList theList) {
        try {
            // run through the list of tasks and write them to the file
            File file = getStorageFile();
            FileWriter fileWritter = new FileWriter(file);
            for (int i = 0; i < theList.getEntryCount(); i++) {
                seedu.duke.financial.FinancialEntry entry = theList.getEntry(i);
                fileWritter.write(entry.toStorageString() + "\n");
            }
            fileWritter.close();
            logger.log(Level.INFO, "Updated file with " + theList.getEntryCount() + " entries.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses an array of strings to create an Expense object.
     *
     * @param tokens An array of strings where:
     *               tokens[1] is the amount as a string,
     *               tokens[2] is the description,
     *               tokens[3] is the date in ISO-8601 format (yyyy-MM-dd).
     * @return An Expense object created from the parsed data.
     * @throws NumberFormatException if the amount cannot be parsed as a double.
     * @throws DateTimeParseException if the date cannot be parsed as a LocalDate.
     */
    public Expense parseExpense(String[] tokens) {
        try {
            double amount = Double.parseDouble(tokens[1]);
            String description = tokens[2];
            LocalDate date = LocalDate.parse(tokens[3]);
            assert amount >= 0 : "Amount should be non-negative";
            assert description != null && !description.isEmpty() : "Description should not be empty";
            return new Expense(amount, description, date);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Error parsing amount in expense: " + tokens[1]);
            throw e;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error parsing date in expense: " + tokens[3]);
            throw e;
        }
    }

    /**
     * Parses an array of strings to create an Income object.
     *
     * @param tokens An array of strings where:
     *               tokens[1] is the amount as a double,
     *               tokens[2] is the description as a string,
     *               tokens[3] is the date as a LocalDate in ISO-8601 format.
     * @return An Income object constructed from the provided tokens.
     * @throws NumberFormatException if tokens[1] cannot be parsed as a double.
     * @throws DateTimeParseException if tokens[3] cannot be parsed as a LocalDate.
     */
    public Income parseIncome(String[] tokens) {
        try{
            double amount = Double.parseDouble(tokens[1]);
            String description = tokens[2];
            LocalDate date = LocalDate.parse(tokens[3]);
            assert amount >= 0 : "Amount should be non-negative";
            assert description != null && !description.isEmpty() : "Description should not be empty";
            return new Income(amount, description, date);
        }catch (NumberFormatException e){
            logger.log(Level.WARNING, "Error parsing amount in income: " + tokens[1]);
            throw e;
        }catch (Exception e){
            logger.log(Level.WARNING, "Error parsing date in income: " + tokens[3]);
            throw e;
        }
    }

    /**
     * Loads financial entries from a file and returns a FinancialList containing the entries.
     * The file is expected to contain lines starting with 'E' for expenses and 'I' for incomes,
     * followed by details separated by " | ".
     * 
     * @return FinancialList containing the loaded financial entries, or null if an error occurs.
     */
    public FinancialList loadFromFile(){
        FinancialList theList = new FinancialList();
        try {
            File file = getStorageFile();
            java.util.Scanner sc = new java.util.Scanner(file);
            Integer loadedExpenseCount = 0;
            Integer loadedIncomeCount = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // parse the line and add the task to the list
                if (line.charAt(0) == 'E') {
                    String[] tokens = line.split(" \\| ");
                    theList.addEntry(parseExpense(tokens));
                    loadedExpenseCount++;
                } else if (line.charAt(0) == 'I') {
                    String[] tokens = line.split(" \\| ");
                    theList.addEntry(parseIncome(tokens));
                    loadedIncomeCount++;
                }
            }
            sc.close();
            logger.log(Level.INFO, "Loaded " + loadedExpenseCount + " expenses and " + 
                    loadedIncomeCount + " incomes from file.");
            return theList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
