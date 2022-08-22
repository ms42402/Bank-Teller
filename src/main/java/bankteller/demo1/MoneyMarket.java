package bankteller.demo1;

/**
 * This class represents an open or closed Money Market Savings account with an account holder and current balance.
 * @author Afsana Rahman, Mini Sinha
 */
public class MoneyMarket extends Savings{

    private static final int WAIVE_FEE_BALANCE = 2500;
    private static final int MAX_WITHDRAWALS = 3;
    private static final int MONTHLY_FEE = 10;

    private static final double ANNUAL_INTEREST = 0.008;

    private int numWithdrawals;

    /**
     * Constructor that creates an instance of a new Money Market Savings account.
     * Money Market accounts are automatically opened as loyal and have no withdrawals.
     * @param profile account holder
     * @param balance initial deposit
     */
    public MoneyMarket(Profile profile, double balance) {
        super(profile, balance, 1);
        this.numWithdrawals = 0;
    }

    /**
     * Method that adds a withdrawal to withdrawal count when there is a withdrawal.
     */
    public void addWithdrawals() {
        this.numWithdrawals++;
    }

    /**
     * Method that converts current account information into a String.
     * String is in format "accountType::FirstName LastName mm/dd/yyy::Balance $#,##0.00::CLOSED::Loyal::withdrawl: #".
     * String will only contain "::CLOSED" if account is currently closed, and "::Loyal" only if account is LOYAL.
     * The withdrawal count at the end is the number of withdrawals that have been made from the current account.
     * @return String containing account information
     */
    @Override
    public String toString() {
        String toString = super.toString() + "::withdrawl: " + this.numWithdrawals;
        return toString.replace("Money Market", "Money Market Savings");
    }

    /**
     * Method that returns the percentage of the monthly interest on current account.
     * Default annual interest on Money Market Savings accounts is 0.8%. Additional annual interest of 0.15% is added for
     *  Loyal account holders.
     * @return percent monthly interest in decimal form (fraction out of 100)
     */
    @Override
    public double monthlyInterest() {
        if(this.loyal == LOYAL) {
            return (ANNUAL_INTEREST + LOYAL_INTEREST) / MONTHS;
        }
        return ANNUAL_INTEREST / MONTHS;
    }

    /**
     * Method that returns the monthly fee on current account.
     * Monthly fee is waived if current balance is greater than $2500 and there have been less than 3 total withdrawals
     *  on the account. Otherwise, an account is charged $10/month.
     * @return monthly fee for current account
     */
    @Override
    public double fee() {
        return ((this.balance >= WAIVE_FEE_BALANCE && this.numWithdrawals <= MAX_WITHDRAWALS) ? 0 : MONTHLY_FEE);
    }

    /**
     * Method that returns the account type of the current account.
     * @return "Money Market"
     */
    @Override
    public String getType() {
        return "Money Market";
    }

    /**
     * Opens or closes an account based on given input. Number of withdrawals is reset if account is closing.
     * @param close true if account needs to be closed, false if account needs to be opened
     */
    @Override
    public void close(boolean close) {
        this.closed = close;
        if(this.closed) {
            this.numWithdrawals = 0;
        }
    }

}