# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

---

### FinancialList Component

#### Overview

The `FinancialList` class is responsible for storing and managing all financial entries in the application,
including both `Expense` and `Income`. The `FinancialEntry` class acts as the base class, with `Expense` and `Income` 
classes extending it to represent specific types of transactions.

#### Class Structure

The `FinancialList` class contains the following attributes:
- entries: A list of FinancialEntry objects representing all stored transactions.

The `FinancialEntry` class contains the following attributes:
- **description**: A string describing the transaction.
- **amount**: A double representing the amount of the transaction.
- **date**: A LocalDate object representing the date of the transaction.

The Expense and Income classes extend FinancialEntry, each maintaining the base attributes while
adding context to the type of financial entry.

### Implementation Details

#### FinancialList Class Diagram
- The FinancialList stores and manages FinancialEntry objects, including Expense and Income.
---

### Commands

#### Overview

The abstract class `Command` has been implemented to introduce an additional layer
of abstraction between the `AppUi` class and command execution,
allowing for separation of handling command keywords and executing commands.

The diagram below shows the inheritance of the `Command` class.

#### Listing Entries
__Overview__

The list entries feature is facilitated by the `SeeAllEntriesCommand` class.
Similarly, classes `SeeAllExpensesCommand` and `SeeAllIncomesCommand` facilitate 
listing out expenses and incomes respectively.

__Implementation__

The user invokes the command to list entries by entering the following command:
```list [income|expense] [/from START_DATE] [/to END_DATE]```.

This is parsed by the InputParser, returning a HashMap `commandArgumets`, containing the following optional arguments:
- `argument`: Represents the type of Financial Entries to be printed. Can take 3 possible values:
  - `expense`: List only Expenses
  - `income`: List only Incomes
  - `null`: List both Expenses and Incomes
- `/from`: Represents the starting date from which Financial Entries should be listed. If value is `null`,
there is no defined starting date.
- `/to`: Represents the ending date by which Financial Entries should be listed. If value is `null`,
  there is no defined ending date.

`CommandHandler` invokes the `listHelper` method to create and execute the command to list the financial entries
according to the following logic.

{add in diagram}

The interaction between the command classes and the `FinancialList` is as follows,
using `SeeAllEntriesCommand` as an example:

{add diagram}

The `shouldBeIncluded()` method marks Financial Entries as "should be included" if their
dates fall between the start and end dates passed into the command object.

`SeeAllExpensesCommand` and `SeeAllIncomesCommand` interact with the `FinancialList` in a 
similar manner, with the only difference being that the `shouldBeIncluded()` methods of
`SeeAllExpensesCommand` and `SeeAllIncomesCommand` only mark `Expenses` and `Incomes` as "should be included".

__Design Considerations__

Given that the logic for `SeeAllEntriesCommand`, `SeeAllExpensesCommand` and `SeeAllIncomesCommand` are very similar 
with the only difference being the criteria for printing the entries, we made `SeeAllExpensesCommand` and 
`SeeAllIncomesCommand` inherit `SeeAllEntriesCommand` to reduce the amount of duplicated code written.

### Exceptions and Logging

An exception class `FinanceBuddyException` is thrown when users use the product wrongly.
Exceptions are caught at the nearest instance that they occur.

*Insert code snippet here.*

### Storage

__Overview__

The `Storage` class has been implemented to store the `FinancialList` into a file. 
Also it's responsible for restoring users' progress even if they have terminate the program using the saved file.

__Implementation Details__

The function `updateStorage` should be called whenever the `FinancialList` in an `AppUi` object. 
It will overide the `data/FinancialList.txt` file with the up-to-date `FinancialList` that has been converted to string.
Note: if the `data/FinancialList.txt` file doesn't exist, the program should generate one.

The storage format of an `Expense` or `Income`, handled by `toStorageString()` method is different from the format generate by `toString()` method for CLS display.

EX:
For an Income with description "Lunch", amount "10.90" and date "2024/10/25":
The `toString()` method will return as `[Income] - Lunch $ 10.90 (on 25/10/24)`
And the `toStorageString()` method will return as `I | 10.90 | Lunch | 25/10/24`

## Product scope
### Target user profile:
- University student who wants to manage their limited finances
- unsure of how to manage his finances, wants to learn
- busy with academics and CCAs, wants to manage finances quickly
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

### Value proposition
Finance Buddy allows our target user profile to keep track of their income/expenditures
faster than a typical mouse/GUI driven app

## User Stories

| Version | As a ...                       | I want to ...                                                                       | So that I can ...                                              |
|---------|--------------------------------|-------------------------------------------------------------------------------------|----------------------------------------------------------------|
| v1.0    | new user                       | see usage instructions                                                              | remember how to use the app in case I forget the commands      |
| v1.0    | user                           | record my daily expenses                                                            | keep track on how much I spend and what I spend on             |
| v1.0    | user                           | delete my logging records                                                           | remove a wrong record                                          |
| v1.0    | user                           | edit my logs                                                                        | edit a wrong record                                            |
| v1.0    | user                           | see my cash flows                                                                   | have an overview of my cash flow                               |
| v2.0    | user                           | view my expenditure over the last X days                                            | see how much money I spent recently                            |
| v2.0    | user                           | keep a log of my data                                                               | retain memory of past transactions in previous runs of the app |
| v2.0    | user                           | set a monthly budget for myself                                                     | ensure that I am saving enough money                           |
| v2.0    | user                           | be alerted when I exceed my allocated budget                                        | know when I spend too much money                               |
| v2.0    | user                           | categorise my spendings                                                             | know my spending across different areas                        |
| v2.0    | user                           | view my expenditure over different categories                                       | see where I spend the most                                     |
| v2.0    | user new to financial planning | get suggested budget allocations according to income, expenses, and financial goals | have realistic financial budgets                               |
| v2.0    | user                           | have reports or summaries of my spending trends                                     | make better financial decisions in the future                  |
| v2.0    | busy user                      | log my finances in the shortest possible time                                       | have more time for other activities                            |
| v2.0    | busy user                      | visualize my overall cash flow (inflows and outflows) across all accounts           | see my total financial health at a glance                      |
| v2.1    | busy user                      | use shortcuts to log frequent and similar expenses                                  | save time logging expenses                                     |

## Non-Functional Requirements

 - Technical Requirements: Any mainstream OS, i.e. Windows, macOS or Linux, with Java 11 installed. Instructions for downloading Java 11 can be found [here](https://www.oracle.com/sg/java/technologies/javase/jdk11-archive-downloads.html).
 - Project Scope Constraints: The application should only be used for tracking. It is not meant to be involved in any form of monetary transaction.
 - Project Scope Constraints: Data storage is only to be performed locally.
 - Quality Requirements: The application should be able to be used effectively by a novice with little experience with CLIs.


## Glossary

* *FinancialEntry* - Refers to the base class for financial entries, containing common attributes such as description, amount, and date that define a financial transaction.
* *description* - A brief text that provides context or details about the financial transaction, such as what the expense or income relates to.
* *amount* - A numeric value representing the monetary value of the transaction. The amount must be a number greater than or equal to zero, with precision up to two decimal places.
* *date* - Represents the date of the financial entry, recorded in the format dd/MM/YY, indicating the day, month, and year when the transaction occurred.
* *Expense* - A subclass of FinancialEntry that represents a financial outflow or expenditure made by the user.
* *Income* - A subclass of FinancialEntry that represents a financial inflow or income received by the user.
* *FinancialList* - A class responsible for storing and managing all financial entries, including both expenses and incomes, allowing for the organization and manipulation of financial data.

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
