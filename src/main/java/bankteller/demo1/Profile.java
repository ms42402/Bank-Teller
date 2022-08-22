package bankteller.demo1;

/**
 * This class holds the information of an account holder.
 * @author Afsana Rahman, Mini Sinha
 */
public class Profile {

    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor that creates new profile of an account holder.
     * @param fname first name of account holder
     * @param lname last name of account holder
     * @param dob date of birth of account holder
     */
    public Profile(String fname, String lname, String dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
    }

    /**
     * Method that returns date of account holder's birthday
     * @return date of birth of account holder
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * Checks if current instance of profile is equal to given profile.
     * This method compares first and last name and birthday. All must be equal for profiles to be equal.
     * First and last name checks are NOT case-sensitive.
     * @param obj object being compared
     * @return true if the profiles have all the same information, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(!(obj instanceof Profile)) {
            return false;
        }

        Profile profile = (Profile) obj;
        return (this.fname.equalsIgnoreCase(profile.fname) && this.lname.equalsIgnoreCase(profile.lname)
            && (this.dob.compareTo(profile.dob) == 0));
    }

    /**
     * Method that converts profile information into a String.
     * String is formatted as "FirstName LastName mm/dd/yyyy".
     * @return profile information in String form
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

}