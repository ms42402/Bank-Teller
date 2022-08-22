package bankteller.demo1;

/**
 * This class represents an open or closed College Checking account with an account holder and current balance.
 * College Checking accounts also must correspond to a given campus.
 * @author Afsana Rahman, Mini Sinha
 */
public class CollegeChecking extends Checking {

    private static final double ANNUAL_INTEREST = 0.0025;
    private static final int MONTHS = 12;

    private Campus campus;

    /**
     * Constructor that creates an instance of a new College Checking account.
     * @param profile account holder
     * @param balance initial deposit
     * @param campus campus code for corresponding campus
     */
    public CollegeChecking(Profile profile, double balance, int campus) {
        super(profile, balance);
        switch(campus) {
            case(0):
                this.campus = Campus.NEW_BRUNSWICK;
                break;
            case(1):
                this.campus = Campus.NEWARK;
                break;
            case(2):
                this.campus = Campus.CAMDEN;
                break;
            default:
                break;
        }
    }

    /**
     * Method that returns the campus corresponding to the current account.
     * @return campus of account
     */
    public Campus getCampus() {
        return this.campus;
    }

    /**
     * Method that sets current campus to new given campus.
     * @param campus new campus that the account will hold
     */
    public void changeCampus(Campus campus) {
        this.campus = campus;
    }

    /**
     * Method that converts current account information into a String.
     * String is returned in format "accountType::FirstName LastName mm/dd/yyy::Balance $#,##0.00::CLOSED::campusName".
     * String will only contain "CLOSED" if account is currently closed.
     * @return String containing account information
     */
    @Override
    public String toString() {
        return super.toString() + "::" + this.campus;
    }

    /**
     * Method that returns the percentage of the monthly interest on current account.
     * Annual interest on College Checking accounts is 0.25%.
     * @return percent monthly interest in decimal form (fraction out of 100)
     */
    @Override
    public double monthlyInterest() {
        return ANNUAL_INTEREST / MONTHS;
    }

    /**
     * Method that returns the monthly fee on current account.
     * College Checking accounts do not have a monthly fee.
     * @return $0
     */
    @Override
    public double fee() {
        return 0;
    }

    /**
     * Method that returns the account type of the current account.
     * @return "College Checking"
     */
    @Override
    public String getType() {
        return "College Checking";
    }
}