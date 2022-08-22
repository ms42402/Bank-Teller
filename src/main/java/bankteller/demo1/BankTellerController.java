package bankteller.demo1;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.InputMismatchException;

/**
 * This class controls the actions on bank accounts in the Bank Teller.
 * Account holders can open, close, deposit to, and withdraw from their account, as well as
 *  view a list of all current accounts in the database and update balances based on fees.
 * @author Afsana Rahman, Mini Sinha
 */
public class BankTellerController {

    @FXML
    private ToggleGroup acctType;

    @FXML
    private TextField amount;

    @FXML
    private RadioButton camden;

    @FXML
    private ToggleGroup campus;

    @FXML
    private RadioButton checking;

    @FXML
    private RadioButton closeAccount;

    @FXML
    private RadioButton collegeChecking;

    @FXML
    private RadioButton deposit;

    @FXML
    private TextArea displayBox;

    @FXML
    private TextField dob;

    @FXML
    private TextField fname;

    @FXML
    private TextField initialDeposit;

    @FXML
    private TextField lname;

    @FXML
    private CheckBox loyalty;

    @FXML
    private RadioButton moneyMarket;

    @FXML
    private RadioButton newBrunswick;

    @FXML
    private RadioButton newark;

    @FXML
    private RadioButton openAccount;

    @FXML
    private ToggleGroup openClose;

    @FXML
    private RadioButton savings;

    @FXML
    private Button submit;

    @FXML
    private ToggleGroup tAcctType;

    @FXML
    private RadioButton tCChecking;

    @FXML
    private RadioButton tChecking;

    @FXML
    private TextField tFname;

    @FXML
    private TextField tLname;

    @FXML
    private RadioButton tMoneyMarket;

    @FXML
    private RadioButton tSavings;

    @FXML
    private TextField tdob;

    @FXML
    private ToggleGroup transact;

    @FXML
    private Button tsubumit;

    @FXML
    private RadioButton withdraw;

    private static final int VALID_INPUT_NUM = 5;
    private static final int NOT_FOUND = -1;

    private static final int MM_MIN_BAL = 2500;

    private static final int NB_CAMPUS = 0;
    private static final int CAM_CAMPUS = 2;

    AccountDatabase database = new AccountDatabase();

    /**
     * This method initializes the "Manage Accounts" tab in the Bank Teller.
     */
    @FXML
    void initialize() {
        fname.setText(null);
        lname.setText(null);
        dob.setText(null);
        initialDeposit.setDisable(true);
        fname.setDisable(true);
        lname.setDisable(true);
        dob.setDisable(true);
        acctType.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(true);
        });
        campus.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(true);
        });
        loyalty.setDisable(true);
        submit.setDisable(true);

        initializeTransactions();
    }

    /**
     * This method initializes the "Transactions" tab in the BankTeller.
     */
    @FXML
    void initializeTransactions() {
        amount.setText(null);
        tFname.setText(null);
        tLname.setText(null);
        tdob.setText(null);
        amount.setDisable(true);
        tFname.setDisable(true);
        tLname.setDisable(true);
        tdob.setDisable(true);
        tAcctType.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(true);
        });
        tsubumit.setDisable(true);
    }

    /**
     * This method enables the appropriate options if the user chooses to open an account.
     */
    @FXML
    void openAccountToggles() {
        initialize();
        initialDeposit.setDisable(false);
        fname.setDisable(false);
        lname.setDisable(false);
        dob.setDisable(false);
        acctType.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(false);
        });
        submit.setDisable(false);
    }

    /**
     * This method enables the appropriate options if the user chooses to close an account.
     */
    @FXML
    void closeAccountToggles() {
        initialize();
        fname.setDisable(false);
        lname.setDisable(false);
        dob.setDisable(false);
        acctType.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(false);
        });
        submit.setDisable(false);
    }

    /**
     * This method disables toggles if creating a Checking or MoneyMarket account.
     */
    @FXML
    void closeToggles() {
        campus.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(true);
        });
        loyalty.setDisable(true);
    }

    /**
     * This method enables the campus choices for opening a CollegeChecking account.
     */
    @FXML
    void openCCToggles() {
        campus.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(false);
        });
        loyalty.setDisable(true);
    }

    /**
     * This method enables the campus choices for opening a Savings account.
     */
    @FXML
    void openLoyalty() {
        campus.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(true);
        });
        loyalty.setDisable(false);
    }

    /**
     * This method enables the options for depositing or withdrawing to an account.
     */
    @FXML
    void openTransToggles() {
        initializeTransactions();
        amount.setDisable(false);
        tFname.setDisable(false);
        tLname.setDisable(false);
        tdob.setDisable(false);
        tAcctType.getToggles().forEach(toggle -> {
            Node disable = (Node) toggle;
            disable.setDisable(false);
        });
        tsubumit.setDisable(false);
    }

    /**
     * This method processes either adding or removing an account from the account database.
     */
    @FXML
    void accountAction() {
        if(!errorCheck()) return;

        Account account = makeAccount();
        if(openClose.getSelectedToggle() == openAccount && !validBalance()) return;

        if(openClose.getSelectedToggle() == openAccount) {
            open(account);
        } else { // close account
            int accountNum = findAccount(account);
            if (database.close(account)) {
                displayBox.appendText("Account closed.\n");
            } else if(accountNum != NOT_FOUND){
                displayBox.appendText("Account is closed already.\n");
            } else {
                displayBox.appendText(account.getHolder().toString() + " " + account.getType()
                        + " is not in the database.\n");
            }
        }
    }

    /**
     * This method creates a new account based on user input/selections.
     * New accounts are created to either find, open, or close in the account database.
     * Instances of accounts can be created even if invalid.
     * @return new account based on selections made by the user
     */
    private Account makeAccount() {
        Profile profile = new Profile(fname.getText(), lname.getText(), dob.getText());
        String init = initialDeposit.getText();
        double depos = Double.parseDouble(init);
        Toggle toggle = acctType.getSelectedToggle();
        if (collegeChecking.equals(toggle)) {
            int campusCode = -1;
            if (openClose.getSelectedToggle() == openAccount) {
                Toggle selectedToggle = campus.getSelectedToggle();
                if (newBrunswick.equals(selectedToggle)) {
                    campusCode = 0;
                } else if (newark.equals(selectedToggle)) {
                    campusCode = 1;
                }
                campusCode = 2;
            }
            return new CollegeChecking(profile, depos, campusCode);
        } else if (savings.equals(toggle)) {
            int loyaltyVal = -1;
            if (openClose.getSelectedToggle() == openAccount) {
                loyaltyVal = loyalty.isSelected() ? 1 : 0;
            }
            return new Savings(profile, depos, loyaltyVal);
        } else if (moneyMarket.equals(toggle)) {
            return new MoneyMarket(profile, depos);
        }
        return new Checking(profile, depos);
    }

    /**
     * This method creates accounts for making transactions.
     * @return account to be found holding balance to be deposited/withdrawn
     */
    private Account makeTAccount() {
        Profile profile = new Profile(tFname.getText(), tLname.getText(), tdob.getText());
        String init = amount.getText();
        double depos = Double.parseDouble(init);
        Toggle toggle = acctType.getSelectedToggle();
        if (tCChecking.equals(toggle)) {
            return new CollegeChecking(profile, depos, 0);
        } else if (tSavings.equals(toggle)) {
            return new Savings(profile, depos, 1);
        } else if (tMoneyMarket.equals(toggle)) {
            return new MoneyMarket(profile, depos);
        }
        return new Checking(profile, depos);
    }

    /**
     * This method checks for possible selection/input syntax errors when creating an account.
     * @return true if all inputs by the user are valid, false otherwise
     */
    private boolean errorCheck() {
        if(fname.getText() == null || lname.getText() == null) {
            displayBox.appendText("Please enter a valid name.\n");
            return false;
        } else if(dob.getText() == null || !validBirthday(dob.getText())) {
            displayBox.appendText("Please enter a valid birthday.\n");
            return false;
        } else if(acctType.getSelectedToggle() == null) {
            displayBox.appendText("Please select an account type.\n");
            return false;
        } else if(acctType.getSelectedToggle() == collegeChecking && campus.getSelectedToggle() == null) {
            displayBox.appendText("Please select a campus.\n");
            return false;
        }

        try {
            if(openClose.getSelectedToggle() == openAccount) {
                String init = initialDeposit.getText();
                double depos = Double.parseDouble(init);
            }
        } catch(NumberFormatException e) {
            displayBox.appendText("Not a valid initial deposit.\n");
            return false;
        }

        return true;
    }

    /**
     * This method checks if an initial deposit is valid for opening an account.
     * @return true if balance is valid for chosen account, false otherwise
     */
    private boolean validBalance() {
        double balance = Double.parseDouble(initialDeposit.getText());
        if (balance <= 0) {
            displayBox.appendText("Initial deposit cannot be 0 or negative.\n");
            return false;
        } else if (acctType.getSelectedToggle() == moneyMarket && balance < MM_MIN_BAL) {
            displayBox.appendText("Minimum of $2500 to open a MoneyMarket account.\n");
            return false;
        }
        return true;
    }

    /**
     * Checks if account holder has a valid birthday for opening a bank account.
     * Valid birthdays for opening an account must be a valid past date.
     * @param birthday String form of birthday
     * @return true if the birthday is a possible birthday, false otherwise
     */
    private boolean validBirthday(String birthday) {
        if(dob.getText() == null) return false;
        try {
            Date bday = new Date(birthday);
            if (!bday.isValid()) {
                return false;
            }

            Date today = new Date();
            return bday.compareTo(today) < 0;
        } catch(NumberFormatException e) {
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (InputMismatchException e) {
            return false;
        }
    }

    /**
     * Method that finds a given account in the current AccountDatabase.
     * An account is considered "found" if there exists an account of the same type
     *  (Checking, College Checking, Savings, Money Market) with the same holder.
     * An account holder can only have one Checking OR College Checking account in the database, closed or not.
     * @param account account to search for
     * @return index where account is located in the AccountDatabase -- returns NOT_FOUND if it does not exist
     */
    private int findAccount(Account account) {
        if(account == null) {
            return NOT_FOUND;
        }

        Account[] accounts = database.getAccounts();
        for(int i = 0; i < database.getNumAcct(); i++) {
            if(accounts[i].equals(account)) {
                return i;
            } else if(accounts[i].getHolder().equals(account.getHolder())) {
                if(accounts[i].getType().contains("Checking") && account.getType().contains("Checking")) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * This method re/opens an account for a given account holder.
     * An account is not valid for opening if the holder already has an open account of the same type in the database.
     * @param account account to be opened
     */
    private void open(Account account) {
        if(findAccount(account) != NOT_FOUND) { // reopening old account
            int accountNum = findAccount(account);
            Account[] accounts = database.getAccounts();
            if(accounts[accountNum].getHolder().equals(account.getHolder()) && !accounts[accountNum].equals(account)) {
                if(accounts[accountNum].getType().contains("Checking") && account.getType().contains("Checking")) {
                    displayBox.appendText(account.getHolder().toString() + " same account(type) is in the database.\n");
                }
            } else if(accounts[accountNum].isClosed()) {
                database.open(account);
                displayBox.appendText("Account reopened.\n");
            } else {
                displayBox.appendText(account.getHolder().toString() + " same account(type) is in the database.\n");
            }
        } else if (database.open(account)) { // opening new account
            displayBox.appendText("Account opened.\n");
        }
    }

    /**
     * This method processes valid deposits and withdrawals into accounts in the account database.
     */
    @FXML
    void transaction() {
        if(!validTransaction()) return;

        Account account = makeTAccount();
        if(findAccount(account) == NOT_FOUND) {
            displayBox.appendText(account.getHolder().toString() + " " + account.getType()
                    + " is not in the database.\n");
            return;
        } else {
            Account[] accounts = database.getAccounts();
            int comparing = findAccount(account);
            if(accounts[comparing].getHolder().equals(account.getHolder())) {
                if (accounts[comparing].getType().contains("Checking") && account.getType().contains("Checking")
                        && !accounts[comparing].equals(account)) {
                    displayBox.appendText(account.getHolder().toString() + " " + account.getType()
                            + " is not in the database.\n");
                    return;
                }
            }
        }

        if(transact.getSelectedToggle() == deposit) {
            database.deposit(account);
            displayBox.appendText("Deposit - balance updated.\n");
        } else {
            withdrawal(account);
        }
    }

    /**
     * This method checks if user-inputted information is valid for a deposit or withdrawal.
     * @return true if the transaction is possible, false otherwise
     */
    private boolean validTransaction() {
        if(tFname.getText() == null || tLname.getText() == null) {
            displayBox.appendText("Please enter a valid name.\n");
            return false;
        } else if(tdob.getText() == null || !validBirthday(tdob.getText())) {
            displayBox.appendText("Please enter a valid birthday.\n");
            return false;
        } else if(tAcctType.getSelectedToggle() == null) {
            displayBox.appendText("Please select an account type.\n");
            return false;
        }

        try {
            String amt = amount.getText();
            double amnt = Double.parseDouble(amt);
            if(amnt <= 0) {
                String output = transact.getSelectedToggle() == deposit ? "Deposit - " : "Withdraw - ";
                displayBox.appendText(output + "amount cannot be 0 or negative.\n");
                return false;
            }
        } catch(NumberFormatException e) {
            displayBox.appendText("Please enter a valid amount.\n");
            return false;
        }

        return true;
    }

    /**
     * This method processes a valid withdrawal from an existing account in the database.
     * Withdrawals are only valid if the account holder has enough money to withdraw the given amount.
     * @param account account being checked for possible withdrawal
     */
    private void withdrawal(Account account) {
        double amnt = account.getBalance();
        if (database.withdraw(account)) {
            displayBox.appendText("Withdraw - balance updated.\n");
        } else {
            int accountNum = findAccount(account);
            if(database.getAccounts()[accountNum].getBalance() < amnt) {
                displayBox.appendText("Withdraw - insufficient funds.\n");
            }
        }
    }

    /**
     * This method prints all accounts in the database.
     */
    @FXML
    void print() {
        if(database.getNumAcct() == 0) {
            displayBox.appendText("Account database is empty!\n");
        } else {
            displayBox.appendText("*list of accounts in the database\n" + database.print());
        }
    }

    /**
     * This method prints all accounts ordered by their type in the database.
     */
    @FXML
    void printByType() {
        if(database.getNumAcct() == 0) {
            displayBox.appendText("Account database is empty!\n");
        } else {
            displayBox.appendText("*list of accounts by account type. \n" + database.printByAccountType());
        }
    }

    /**
     * This method prints all accounts in the database along with their monthly fees and interest rates,
     */
    @FXML
    void printWithFeesAndInterest() {
        if(database.getNumAcct() == 0) {
            displayBox.appendText("Account database is empty!\n");
        } else {
            displayBox.appendText(database.printFeeAndInterest());
        }

    }

    /**
     * This method prints all accounts in the database after updating their balanaces based on monthly fees and interest.
     */
    @FXML
    void updateBalances() {
        if(database.getNumAcct() == 0) {
            displayBox.appendText("Account Database is empty!\n");
        } else {
            Account[] accounts = database.getAccounts();
            for(int i = 0; i < database.getNumAcct(); i++) {
                double newBalance = accounts[i].getBalance() * accounts[i].monthlyInterest();
                accounts[i].deposit(newBalance);
                accounts[i].withdraw(accounts[i].fee());
            }
            displayBox.appendText("*list of accounts with updated balance\n" + database.print());
        }
    }

}