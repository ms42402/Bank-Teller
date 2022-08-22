package bankteller.demo1;

/**
 * This class contains the definitions of Campus objects.
 * Each campus corresponds to a valid campus code.
 * @author Afsana Rahman, Mini Sinha
 */
public enum Campus {

    NEW_BRUNSWICK(0, "New Brunswick"),
    NEWARK(1, "Newark"),
    CAMDEN(2, "Camden");

    private final int campusCode;
    private final String campus;

    /**
     * Constructor that creates Campus object based on corresponding campus code.
     * Campus name matches the Campus object name.
     * @param campusCode valid campus code
     * @param campus name of campus
     */
    Campus(int campusCode, String campus) {
        this.campusCode = campusCode;
        this.campus = campus;
    }
}