package SchedulingModel;

import java.sql.Timestamp;

/**
 * Represents a customer. Easily populated with data from the database. Includes additional calculated data for ease of use locally.
 */
public class Customer
{
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String country;
    private String division;

    /**
     * Represents a customer. Easily populated with data from the database. Includes additional calculated data for ease of use locally.
     * (This single constructor ensures that a Customer is data complete.)
     *
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param createdDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @param country
     * @param division
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, Timestamp createdDate, String createdBy,
             Timestamp lastUpdate, String lastUpdatedBy, int divisionID, String country, String division)
    {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.country = country;
        this.division = division;
    }

    /**
     * Gets and returns this Customer's Customer ID.
     *
     * @return this Customer's Customer ID.
     */
    public int getCustomerID()
    {
        return customerID;
    }

    /**
     * Sets this Customer's Customer ID.
     *
     * @param customerID the Customer ID to set as this Customer's Customer ID.
     */
    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    /**
     * Gets and returns this Customer's Customer Name.
     *
     * @return this Customer's Customer Name.
     */
    public String getCustomerName()
    {
        return customerName;
    }

    /**
     * Sets this Customer's Customer Name.
     *
     * @param customerName the Customer Name to set as this Customer's Customer Name.
     */
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    /**
     * Gets and returns this Customer's address.
     *
     * @return this Customer's address.
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets this Customer's address.
     *
     * @param address the address to set as this Customer's address.
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Gets and returns this Customer's postal code.
     *
     * @return this Customer's postal code.
     */
    public String getPostalCode()
    {
        return postalCode;
    }

    /**
     * Sets this Customer's postal code.
     *
     * @param postalCode the postal code to set as this Customer's postal code.
     */
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    /**
     * Gets and returns this Customer's phone number.
     *
     * @return this Customer's phone number.
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets this Customer's phone number.
     *
     * @param phone the phone number to set as this Customer's phone number.
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Gets and returns this Customer's created date.
     *
     * @return this Customer's created date.
     */
    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    /**
     * Sets this Customer's created date.
     *
     * @param createdDate the created date to set as this Customer's created date.
     */
    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     * Gets and returns who created this Customer.
     *
     * @return this Customer's creator.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }

    /**
     * Sets who created this Customer.
     *
     * @param createdBy the creator to set as this Customer's creator.
     */
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    /**
     * Gets and returns this Customer's last update.
     *
     * @return this Customer's last update.
     */
    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    /**
     * Sets this Customer's last update.
     *
     * @param lastUpdate the last update to set as this Customer's last update.
     */
    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets and returns who last updated this Customer.
     *
     * @return this Customer's last updater.
     */
    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }

    /**
     * Sets who last updated this Customer.
     *
     * @param lastUpdatedBy the last updater to set as this Customer's last updater.
     */
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets and returns this Customer's Division ID.
     *
     * @return this Customer's Division ID.
     */
    public int getDivisionID()
    {
        return divisionID;
    }

    /**
     * Sets this Customer's Division ID.
     *
     * @param divisionID the Division ID to set as this Customer's Division ID.
     */
    public void setDivisionID(int divisionID)
    {
        this.divisionID = divisionID;
    }

    /**
     * Gets and returns this Customer's Country Name.
     *
     * @return this Customer's Country Name.
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Sets this Customer's Country Name.
     *
     * @param country the Country Name to set as this Customer's Country Name.
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * Gets and returns this Customer's Division.
     *
     * @return this Customer's Division.
     */
    public String getDivision()
    {
        return division;
    }

    /**
     * Sets this Customer's Division.
     *
     * @param division the Division to set as this Customer's Division.
     */
    public void setDivision(String division)
    {
        this.division = division;
    }
}
