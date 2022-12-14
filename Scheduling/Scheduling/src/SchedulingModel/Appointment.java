package SchedulingModel;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a customer appointment. Easily populated with data from the database. Includes additional calculated data for ease of use locally.
 */
public class Appointment
{
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private Contact contact;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    /**
     * Represents a customer appointment. Easily populated with data from the database. Includes additional calculated data for ease of use locally.
     * (This single constructor ensures that an Appointment is data complete.)
     *
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createdDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @param contact
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end,
                Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID, Contact contact,
                LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime)
    {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    /**
     * Gets and returns this Appointment's appointmentID.
     *
     * @return this Appointment's appointmentID.
     */
    public int getAppointmentID()
    {
        return appointmentID;
    }

    /**
     * Sets this Appointment's appointmentID.
     *
     * @param appointmentID the appointmentID to set as this Appointment's appointmentID.
     */
    public void setAppointmentID(int appointmentID)
    {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets and returns this Appointment's title.
     *
     * @return this Appointment's title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets this Appointment's title.
     *
     * @param title the title to set as this Appointment's title.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets and returns this Appointment's description.
     *
     * @return this Appointment's description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets this Appointment's description.
     *
     * @param description the description to set as this Appointment's description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets and returns this Appointment's location.
     *
     * @return this Appointment's location.
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets this Appointment's location.
     *
     * @param location the location to set as this Appointment's location.
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * Gets and returns this Appointment's type.
     *
     * @return this Appointment's type.
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets this Appointment's type.
     *
     * @param type the type to set as this Appointment's type.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Gets and returns this Appointment's start time.
     *
     * @return this Appointment's start time.
     */
    public Timestamp getStart()
    {
        return start;
    }

    /**
     * Sets this Appointment's start time.
     *
     * @param start the start time to set as this Appointment's start time.
     */
    public void setStart(Timestamp start)
    {
        this.start = start;
    }

    /**
     * Gets and returns this Appointment's end time.
     *
     * @return this Appointment's end time.
     */
    public Timestamp getEnd()
    {
        return end;
    }

    /**
     * Sets this Appointment's end time.
     *
     * @param end the end time to set as this Appointment's end time.
     */
    public void setEnd(Timestamp end)
    {
        this.end = end;
    }

    /**
     * Gets and returns this Appointment's created date.
     *
     * @return this Appointment's created date.
     */
    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    /**
     * Sets this Appointment's created date.
     *
     * @param createdDate the created date to set as this Appointment's created date.
     */
    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     * Gets and returns who created this Appointment.
     *
     * @return this Appointment's created by tag.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }

    /**
     * Sets who created this Appointment.
     *
     * @param createdBy the created by tag to set as this Appointment's created by tag.
     */
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    /**
     * Gets and returns this Appointment's last update time.
     *
     * @return this Appointment's last update time.
     */
    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    /**
     * Sets this Appointment's last update.
     *
     * @param lastUpdate the last update to set as this Appointment's last update.
     */
    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets and returns who last updated this Appointment.
     *
     * @return this Appointment's last updated by tag.
     */
    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }

    /**
     * Sets who last updated this Appointment.
     *
     * @param lastUpdatedBy the last updated by tag to set as this Appointment's last updated by tag.
     */
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets and returns this Appointment's customerID.
     *
     * @return this Appointment's customerID.
     */
    public int getCustomerID()
    {
        return customerID;
    }

    /**
     * Sets this Appointment's customerID.
     *
     * @param customerID the customerID to set as this Appointment's customerID.
     */
    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    /**
     * Gets and returns this Appointment's userID.
     *
     * @return this Appointment's userID.
     */
    public int getUserID()
    {
        return userID;
    }

    /**
     * Sets this Appointment's userID.
     *
     * @param userID the userID to set as this Appointment's userID.
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    /**
     * Gets and returns this Appointment's contactID.
     *
     * @return this Appointment's contactID.
     */
    public int getContactID()
    {
        return contactID;
    }

    /**
     * Sets this Appointment's contactID.
     *
     * @param contactID the contactID to set as this Appointment's contactID.
     */
    public void setContactID(int contactID)
    {
        this.contactID = contactID;
    }

    /**
     * Gets and returns this Appointment's Contact.
     *
     * @return this Appointment's Contact.
     */
    public Contact getContact()
    {
        return contact;
    }

    /**
     * Sets this Appointment's Contact.
     *
     * @param contact the Contact to set as this Appointment's Contact.
     */
    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    /**
     * Gets and returns this Appointment's Contact Name.
     *
     * @return this Appointment's Contact Name.
     */
    public String getContactName()
    {
        return contact.getContactName();
    }

    /**
     * Gets and returns this Appointment's start date.
     *
     * @return this Appointment's start date.
     */
    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * Sets this Appointment's start date.
     *
     * @param startDate the start date to set as this Appointment's start date.
     */
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * Gets and returns this Appointment's start (local) time.
     *
     * @return this Appointment's start (local) time.
     */
    public LocalTime getStartTime()
    {
        return startTime;
    }

    /**
     * Sets this Appointment's start (local) time.
     *
     * @param startTime the start (local) time to set as this Appointment's start (local) time.
     */
    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    /**
     * Gets and returns this Appointment's end date.
     *
     * @return this Appointment's end date.
     */
    public LocalDate getEndDate()
    {
        return endDate;
    }

    /**
     * Sets this Appointment's end date.
     *
     * @param endDate the end date to set as this Appointment's end date.
     */
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    /**
     * Gets and returns this Appointment's end (local) time.
     *
     * @return this Appointment's end (local) time.
     */
    public LocalTime getEndTime()
    {
        return endTime;
    }

    /**
     * Sets this Appointment's end (local) time.
     *
     * @param endTime the end (local) time to set as this Appointment's end (local) time.
     */
    public void setEndTime(LocalTime endTime)
    {
        this.endTime = endTime;
    }
}
