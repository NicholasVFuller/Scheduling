package SchedulingModel;

/**
 * Represents a Country. Easily populated with data from the database.
 */
public class Country
{
    private int countryID;
    private String country;

    /**
     * Represents a Country. Easily populated with data from the database.
     * (This single constructor ensures that a Country is data complete.)
     *
     * @param countryID
     * @param country
     */
    public Country(int countryID, String country)
    {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Gets and returns this Country's Country ID.
     *
     * @return this Country's Country ID.
     */
    public int getCountryID()
    {
        return countryID;
    }

    /**
     * Sets this Country's Country ID.
     *
     * @param countryID the Country ID to set as this Country's Country ID.
     */
    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    /**
     * Gets and returns this Country's Country Name.
     *
     * @return this Country's Country Name.
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Sets this Country's Country Name.
     *
     * @param country the Country Name to set as this Country's Country Name.
     */
    public void setCountry(String country)
    {
        this.country = country;
    }
}
