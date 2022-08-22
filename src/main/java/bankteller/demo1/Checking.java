package bankteller.demo1;

/**
 * This class represents an open or closed Checking account with an account holder and current balance.
 * @author Afsana Rahman, Mini Sinha
 */
public class Checking extends Account{

    private static final int WAIVE_FEE_BALANCE = 1000;
    private static final int MONTHLY_FEE = 25;
    private static final double ANNUAL_INTEREST = 0.001;
    private static final int MONTHS = 12;

    /**
     * Constructor that creates an instance of a new Checking account.
     * @param profile account holder
     * @param balance initial deposit
     */
    public Checking(Profile profile, double balance) {
        super(profile, balance);
    }

    /**
     * Method that converts current account information into a String.
     * String is returned in format "accountType::FirstName LastName mm/dd/yyy::Balance $#,##0.00".
     * If account is closed, returned String will end in "::CLOSED".
     * @return String containing account information
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Method that returns the percentage of the monthly interest on current account.
     * Annual interest on checking accounts is 0.1%.
     * @return percent monthly interest in decimal form (fraction out of 100)
     */
    @Override
    public double monthlyInterest() {
        return ANNUAL_INTEREST / MONTHS;
    }

    /**
     * Method that returns the monthly fee on current account.
     * Monthly fee is waived if current balance is greater than $1000. Otherwise, an account is charged $25/month.
     * @return monthly fee for current account
     */
    @Override
    public double fee() {
        return ((this.balance >= WAIVE_FEE_BALANCE) ? 0 : MONTHLY_FEE);
    }

    /**
     * Method that returns the account type of the current account.
     * @return "Checking"
     */
    @Override
    public String getType() {
        return "Checking";
    }

}