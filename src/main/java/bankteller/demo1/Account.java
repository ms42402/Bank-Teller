package bankteller.demo1;

import java.text.DecimalFormat;

/**
 * This abstract class represents an open or closed account held by an account holder with current balance.
 * @author Afsana Rahman, Mini Sinha
 */
public abstract class Account {

    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * Constructor that creates new account.
     * @param holder person/profile who owns the account
     * @param balance initial deposit
     */
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
        this.closed = false;
    }

    /**
     * Method that returns the profile of the account holder.
     * @return current holder
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * Method that returns the current balance of the account.
     * @return current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Method that resets the balance of an account to $0.
     * This should only be called when an account is being closed.
     */
    public void setBalance() {
        this.balance = 0;
    }

    /**
     * Method that returns the current state of an account.
     * @return true if account is closed, false if account is open
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Method that returns whether an object is an account with the same holder and type.
     * @param obj object being compared to current instance of account
     * @return true if accounts are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(!(obj instanceof Account)) {
            return false;
        }

        Account account = (Account) obj;
        Profile holder = account.getHolder();
        return this.holder.equals(holder) && this.getType().equals(account.getType());
    }

    /**
     * Method that converts current account information into a String.
     * String is returned in format "accountType::FirstName LastName mm/dd/yyy::Balance $#,##0.00".
     * If account is closed, returned String will end in "::CLOSED".
     * @return String containing account information
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setGroupingSize(3);
        String toString = getType() + "::" + this.holder.toString() + "::Balance $" + decimalFormat.format(this.balance);
        if(this.closed) {
            toString += "::CLOSED";
        }
        return toString;
    }

    /**
     * Withdraws given amount from current account balance.
     * @param amount money to be subtracted
     */
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    /**
     * Deposits given amount to current account balance.
     * @param amount money to be added
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Opens or closes an account based on given input.
     * @param close true if account needs to be closed, false if account needs to be opened
     */
    public void close(boolean close) {
        this.closed = close;
    }

    /**
     * Abstract method that returns the percentage of the monthly interest on current account.
     * @return percent monthly interest in decimal form (fraction out of 100)
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that returns the monthly fee on current account.
     * @return monthly fee for current account
     */
    public abstract double fee();

    /**
     * Abstract method that returns the account type of the current account.
     * Account types are Checking, College Checking, Savings, and Money Market.
     * @return account type
     */
    public abstract String getType();

}