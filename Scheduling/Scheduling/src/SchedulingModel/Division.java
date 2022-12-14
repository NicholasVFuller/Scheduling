package SchedulingModel;

/**
 * Represents a first-level division. Easily populated with data from the database.
 */
public class Division
{
    private int divisionID;
    private String division;
    private int countryID;

    /**
     * Represents a first-level division. Easily populated with data from the database.
     * (This single constructor ensures that a Division is data complete.)
     *
     * @param divisionID
     * @param division
     * @param countryID
     */
    public Division(int divisionID, String division, int countryID)
    {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Gets and returns this Division's Division ID.
     *
     * @return this Division's Division ID.
     */
    public int getDivisionID()
    {
        return divisionID;
    }

    /**
     * Sets this Division's Division ID.
     *
     * @param divisionID the Division ID to set as this Division's Division ID.
     */
    public void setDivisionID(int divisionID)
    {
        this.divisionID = divisionID;
    }

    /**
     * Gets and returns this Division's Division Name.
     *
     * @return this Division's Division Name.
     */
    public String getDivision()
    {
        return division;
    }

    /**
     * Sets this Division's Division Name.
     *
     * @param division the Division Name to set as this Division's Division Name.
     */
    public void setDivision(String division)
    {
        this.division = division;
    }

    /**
     * Gets and returns this Division's Country ID.
     *
     * @return this Division's Country ID.
     */
    public int getCountryID()
    {
        return countryID;
    }

    /**
     * Sets this Division's Country ID.
     *
     * @param countryID the Country ID to set as this Division's Country ID.
     */
    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }
}
