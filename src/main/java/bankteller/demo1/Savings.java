package bankteller.demo1;

/**
 * This class represents an open or closed Savings account with an account holder and current balance.
 * A "Loyal" Savings account comes with several special perks including increased monthly interest.
 * @author Afsana Rahman, Mini Sinha
 */
public class Savings extends Account{

    private static final int WAIVE_FEE_BALANCE = 300;
    private static final int MONTHLY_FEE = 6;

    private static final double ANNUAL_INTEREST = 0.003;
    protected static final double LOYAL_INTEREST = 0.0015;
    protected static final int MONTHS = 12;

    protected static final int LOYAL = 1;

    protected int loyal;

    /**
     * Constructor that creates an instance of a new Savings account.
     * @param profile account holder
     * @param balance initial deposit
     * @param loyal loyalty of new account (0 if non-loyal, 1 if loyal)
     */
    public Savings(Profile profile, double balance, int loyal) {
        super(profile, balance);
        this.loyal = loyal;
    }

    /**
     * Method that returns the loyalty status of a savings account.
     * @return current loyal value (may be invalid)
     */
    public int getLoyal() {
        return this.loyal;
    }

    /**
     * Method that converts current account information into a String.
     * String is in format "accountType::FirstName LastName mm/dd/yyy::Balance $#,##0.00::CLOSED::Loyal".
     * String will only contain "::CLOSED" if account is currently closed, and "::Loyal" only if account is LOYAL.
     * @return String containing account information
     */
    @Override
    public String toString() {
        String toString = super.toString();
        if(this.loyal == LOYAL) {
            toString += "::Loyal";
        }
        return toString;
    }

    /**
     * Method that returns the percentage of the monthly interest on current account.
     * Default annual interest on Savings accounts is 0.3%. Additional annual interest of 0.15% is added for
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
     * Monthly fee is waived if current balance is greater than $300. Otherwise, an account is charged $6/month.
     * @return monthly fee for current account
     */
    @Override
    public double fee() {
        return ((this.balance >= WAIVE_FEE_BALANCE) ? 0 : MONTHLY_FEE);
    }

    /**
     * Method that returns the account type of the current account.
     * @return "Savings"
     */
    @Override
    public String getType() {
        return "Savings";
    }

    /**
     * Method that changes the loyalty status of current account based on input.
     * @param loyalty what the loyalty will change to
     */
    public void changeLoyalty(int loyalty) {
        this.loyal = loyalty;
    }

}