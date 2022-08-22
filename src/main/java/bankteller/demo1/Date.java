package bankteller.demo1;

import java.util.Calendar;

/**
 * This class represents a date containing the month, day, and year.
 * Dates are formatted as "mm/ddd/yyyy".
 * @author Afsana Rahman, Mini Sinha
 */
public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;

    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUARTERCENTENNIAL = 400;

    private static final int MINDAYS = 1;
    private static final int SHORTMONTH_MAXDAYS = 30;
    private static final int LONGMONTH_MAXDAYS = 31;
    private static final int FEB_LEAPYEARDAYS = 29;
    private static final int FEB_NONLEAPYEARDAYS = 28;

    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int APR = 4;
    private static final int MAY = 5;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int SEP = 9;
    private static final int OCT = 10;
    private static final int NOV = 11;
    private static final int DEC = 12;

    private static final int EARLIER = -1;

    /**
     * Constructor that returns instance of given specific date.
     * @param date the date in format mm/dd/yyyy
     */
    public Date(String date) {
        String[] tokens = date.split("/");
        this.month = Integer.parseInt(tokens[0]);
        this.day = Integer.parseInt(tokens[1]);
        this.year = Integer.parseInt(tokens[2]);
    }

    /**
     * Constructor that returns instance of current (today's) date.
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH);
        this.day = today.get(Calendar.DATE);
    }

    /**
     * Method that returns value of date's year.
     * @return instance year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Checks if date is a possible calendar date.
     * @return true if valid calendar date, false otherwise
     */
    public boolean isValid() {

        if(this.month < JAN || this.month > DEC) {
            return false;
        } else {
            return validDayForMonth();
        }
    }

    /**
     * Creates a new string containing the date.
     * @return String form of date in "mm/dd/yyyy" format
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     * Compares this date with specified date.
     * @param date another date that is being compared to this instance's date
     * @return negative if instance date is earlier, positive if later, zero if same date
     */
    @Override
    public int compareTo(Date date) {
        if(this.year < date.year) {
            return EARLIER;
        } else if(this.year > date.year) {
            return 1;
        } else {
            if(this.month < date.month) {
                return EARLIER;
            } else if(this.month > date.month) {
                return 1;
            } else {
                if(this.day < date.day) {
                    return EARLIER;
                } else if(this.day > date.day) {
                    return 1;
                } else { // if entire dates are equal
                    return 0;
                }
            }
        }
    }

    /**
     * Helper method that checks if this date is on a valid day in given month.
     * @return true if date is possible, false if not possible
     */
    private boolean validDayForMonth() {

        if(this.day < MINDAYS || this.day > LONGMONTH_MAXDAYS) {
            return false;
        }

        switch(this.month) {
            case(JAN):
            case(MAR):
            case(MAY):
            case(JUL):
            case(AUG):
            case(OCT):
            case(DEC):
                return true;
            case(APR):
            case(JUN):
            case(SEP):
            case(NOV):
                return (this.day <= SHORTMONTH_MAXDAYS);
            case(FEB):
                if(leapYear()) {
                    return (this.day <= FEB_LEAPYEARDAYS);
                } else {
                    return (this.day <= FEB_NONLEAPYEARDAYS);
                }
            default:
                return false;
        }
    }

    /**
     * Helper method that checks if given year is a leap year.
     * A year is a leap year if it is divisible by 400, or divisible by 4 and not 100.
     * @return true if leap year, false otherwise
     */
    private boolean leapYear() {

        if(this.year % QUARTERCENTENNIAL == 0) {
            return true;
        }
        if(this.year % CENTENNIAL == 0) {
            return false;
        }
        return this.year % QUADRENNIAL == 0;
    }

}