package SchedulingModel;

/**
 * Represents a Contact for the organization. Easily populated with data from the database.
 */
public class Contact
{
    private int contactID;
    private String contactName;

    /**
     * Represents a Contact for the organization. Easily populated with data from the database.
     * (This single constructor ensures that a Contact is data complete.)
     *
     * @param contactID
     * @param contactName
     */
    public Contact(int contactID, String contactName)
    {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * Gets and returns this Contact's Contact ID.
     *
     * @return this Contact's Contact ID.
     */
    public int getContactID()
    {
        return contactID;
    }

    /**
     * Sets this Contact's Contact ID.
     *
     * @param contactID the Contact ID to set as this Contact's Contact ID.
     */
    public void setContactID(int contactID)
    {
        this.contactID = contactID;
    }

    /**
     * Gets and returns this Contact's Contact Name.
     *
     * @return this Contact's Contact Name.
     */
    public String getContactName()
    {
        return contactName;
    }

    /**
     * Sets this Contact's Contact Name.
     *
     * @param contactName the Contact Name to set as this Contact's Contact Name.
     */
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
}
