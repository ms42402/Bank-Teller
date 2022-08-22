package bankteller.demo1;

import java.text.DecimalFormat;

/**
 * This class represents a database of all types of bank accounts.
 * @author Afsana Rahman, Mini Sinha
 */
public class AccountDatabase {

    private static final int MONEYMARKET_BALANCE = 2500;

    private static final int ADD_ARRAY_SPACE = 4;
    private static final int NOT_FOUND = -1;

    private Account[] accounts;
    private int numAcct;

    /**
     * Constructor that initializes a new account database, able to hold up to 4 accounts.
     */
    public AccountDatabase() {
        this.accounts = new Account[ADD_ARRAY_SPACE];
        this.numAcct = 0;
    }

    /**
     * Method that returns the current list of accounts held in the database.
     * @return list of accounts in current database
     */
    public Account[] getAccounts() {
        return accounts;
    }

    /**
     * Method that returns the current number of accounts held in the database.
     * @return number of accounts in current database
     */
    public int getNumAcct() {
        return numAcct;
    }

    /**
     * Method that finds a given (open or closed) account in the current database
     * @param account account to be searched for
     * @return index of the account in the database -- returns NOT_FOUND if not found
     */
    private int find(Account account) {
        if (account == null) {
            return NOT_FOUND;
        }

        for (int i = 0; i < this.numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Method that creates space in the database to hold more accounts by adding 4 more possible account slots.
     */
    private void grow() {
        Account[] increasedAccounts = new Account[accounts.length + ADD_ARRAY_SPACE];

        for (int i = 0; i < accounts.length; i++) {
            increasedAccounts[i] = accounts[i];
        }

        accounts = increasedAccounts;
    }

    /**
     * This method serves as a function to either open a new account or reopen an existing account.
     * New accounts must have valid information to be opened in the database.
     * A new account cannot be created if the same account already exists in the database.
     * Account holders can only have one Checking or one College Checking account in the database.
     * @param account account to be opened/added to the database
     * @return true if the account was successfully opened, false otherwise
     */
    public boolean open(Account account) {
        if (!validAccount(account)) return false;

        if (accounts == null) {
            new AccountDatabase();
        } else if (accounts[accounts.length - 1] != null) {
            grow();
        }

        for (int i = 0; i < this.numAcct; i++) {
            if (accounts[i].equals(account)) {
                if (accounts[i].isClosed()) {
                    accounts[i].close(false);
                    deposit(account);
                    if (account.getType().equals("College Checking")) {
                        CollegeChecking tempAcct = (CollegeChecking) account;
                        CollegeChecking databaseTempAcct = (CollegeChecking) accounts[i];
                        databaseTempAcct.changeCampus(tempAcct.getCampus());
                    }
                    return true;
                } else {
                    return false;
                }
            } else if (accounts[i].getHolder().equals(account.getHolder())) {
                if (accounts[i].getType().contains("Checking") && account.getType().contains("Checking")) {
                    return false;
                }
            }
        }
        addAcc(account);
        return true;
    }

    /**
     * Method that adds account to the next available slot in the account list in the database.
     * @param account account to be added to the database
     */
    private void addAcc(Account account) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                accounts[i] = account;
                numAcct++;
                break;
            }
        }
    }


    /**
     * Method that checks the validity of a new account to be opened.
     * Valid accounts must have a valid date of birth (valid date in the past), an initial deposit greater than $0,
     * and follow the requirements for its specific account type (College Checking accounts must have valid campuses
     * and Money Market accounts must have initial deposits greater than $2500).
     * @param account account to be checked for credentials
     * @return true if the account is valid, false otherwise
     */
    private boolean validAccount(Account account) {
        if (!account.getHolder().getDob().isValid()) {
            return false;
        } else if (account.getHolder().getDob().compareTo(new Date()) >= 0) {
            return false;
        } else if (account.getBalance() <= 0) {
            return false;
        } else {
            if (account.getType().equals("College Checking")) {
                CollegeChecking collegeCheckingAcc = (CollegeChecking) account;
                if (collegeCheckingAcc.toString().contains("null")) {
                    return false;
                }
            } else if (account.getType().equals("Savings")) {
                Savings savingsAcc = (Savings) account;
                if (!(savingsAcc.getLoyal() == 1 || savingsAcc.getLoyal() == 0)) {
                    return false;
                }
            } else if (account.getType().equals("Money Market")) {
                if (account.getBalance() < MONEYMARKET_BALANCE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method serves as a function to close an existing open account.
     * Account must already exist in order to be properly closed.
     * If the account is any type of Savings account, the Loyalty will be changed to 0 (non-loyal).
     * @param account account to be closed
     * @return true if account was successfully closed, false otherwise
     */
    public boolean close(Account account) {
        int indexOfClose = find(account);
        if (indexOfClose == NOT_FOUND) {
            return false;
        }

        accounts[indexOfClose].setBalance();
        if (accounts[indexOfClose].isClosed()) {
            return false;
        }
        accounts[indexOfClose].close(true);

        if (accounts[indexOfClose].getType().equals("Money Market")) {
            MoneyMarket tempAcct = (MoneyMarket) accounts[indexOfClose];
            tempAcct.changeLoyalty(0);
        } else if (accounts[indexOfClose].getType().equals("Savings")) {
            Savings tempAcct = (Savings) accounts[indexOfClose];
            tempAcct.changeLoyalty(0);
        }

        return true;
    }

    /**
     * This method serves as a function to deposit a valid given amount to an existing open account.
     * @param account account holding information of account to be deposited to and balance to be withdrawn
     */
    public void deposit(Account account) {
        if (account == null) {
            return;
        }
        int accountIndex = find(account);
        if (accountIndex != NOT_FOUND) {
            accounts[accountIndex].deposit(account.getBalance());
        }

        if (account.getType().equals("Money Market")) {
            MoneyMarket tempAcct = (MoneyMarket) accounts[accountIndex];
            if (account.getBalance() >= MONEYMARKET_BALANCE) {
                tempAcct.changeLoyalty(1);
            }
        }
    }

    /**
     * This method serves as a function to withdraw a valid given amount from an existing open account.
     * The account must have a sufficient balance to withdraw the amount requested; balances cannot become negative.
     * @param account account holding information of account to be withdrawn from and amount to be withdrawn
     * @return true if withdrawal was successful, false otherwise
     */
    public boolean withdraw(Account account) {

        if (account == null) {
            return false;
        }

        double withdrawAmount = account.getBalance();
        if (withdrawAmount <= 0) {
            return false;
        }

        int accountNum = find(account);
        if (accountNum == NOT_FOUND) {
            return false;
        }

        double currentBalance = accounts[accountNum].getBalance();
        if (currentBalance < withdrawAmount) {
            return false;
        }

        accounts[accountNum].withdraw(withdrawAmount);

        if (account.getType().equals("Money Market")) {
            MoneyMarket tempAcct = (MoneyMarket) accounts[accountNum];
            tempAcct.addWithdrawals();
            if (accounts[accountNum].getBalance() < MONEYMARKET_BALANCE) {
                tempAcct.changeLoyalty(0);
            }
        }

        return true;
    }

    /**
     * This method acts as a function to print all accounts in their current ordering.
     */
    public String print() {

        String print = "";
        for (int i = 0; i < this.numAcct; i++) {
            print = print.concat(accounts[i].toString());
            print = print.concat("\n");
        }

        print = print.concat("*end of list*");
        print = print.concat("\n");

        return print;

    }

    /**
     * This method acts as a function to print all accounts ordered by account type.
     */
    public String printByAccountType() {
        sortByAccountType();

        return print();

    }

    /**
     * This method acts as a function to print all accounts and their monthly fees/interest.
     */
    public String printFeeAndInterest() {

        String fee = "";

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setGroupingSize(3);

        fee = fee.concat("\n");
        fee = fee.concat("*list of accounts with fee and monthly interest");
        fee = fee.concat("\n");

        for (int i = 0; i < numAcct; i++) {
            double monthlyInterest = accounts[i].getBalance() * accounts[i].monthlyInterest();
            String formatMonthlyInterest = decimalFormat.format(monthlyInterest);

            fee = fee.concat(accounts[i].toString() + "::fee $" + decimalFormat.format(accounts[i].fee())
                    + "::monthly interest $" + formatMonthlyInterest);
            fee = fee.concat("\n");

        }

        fee = fee.concat("*end of list*");
        fee = fee.concat("\n");

        return fee;
    }

    /**
     * Method that sorts the current list of accounts by account type.
     * Order of priority is Checking, College Checking, Money Market, then Savings.
     */
    private void sortByAccountType() {

        Account[] sortedAccounts = new Account[numAcct];
        int numSortedAccounts = 0;

        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].getType().equals("Checking")) {
                sortedAccounts[numSortedAccounts] = accounts[i];
                numSortedAccounts++;
            }
        }
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].getType().equals("College Checking")) {
                sortedAccounts[numSortedAccounts] = accounts[i];
                numSortedAccounts++;
            }
        }
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].getType().equals("Money Market")) {
                sortedAccounts[numSortedAccounts] = accounts[i];
                numSortedAccounts++;
            }
        }
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].getType().equals("Savings")) {
                sortedAccounts[numSortedAccounts] = accounts[i];
                numSortedAccounts++;
            }
        }

        accounts = sortedAccounts;

    }

}

