package Scheduling;

import DB.DBConnection;
import DB.DBQuery;
import SchedulingModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller of the Main Form.
 */
public class MainformController implements Initializable
{
    @FXML
    private Label errorLabel;

    private static boolean beenHere;

    /**
     * An ObservableList that stores all of the contacts from the database.
     * This list is never updated and only created when starting the program.
     */
    public static ObservableList<Contact> contacts;

    /**
     * An ObservableMap that stores all of the First-Level Division data from the database.
     * This map is never updated and only created when starting the program.
     */
    public static ObservableMap<Integer, Division> divisions;

    /**
     * An ObservableMap that stores all of the countries from the database.
     * This map is never updated and only created when starting the program.
     */
    public static ObservableMap<Integer, Country> countries;

    private ArrayList<Appointment> appointments;

    private Error changeSceneError;

    /**
     * (LAMBDA EXPRESSION #1 is LOCATED HERE. This Functional Interface in conjunction with these lambda expressions allow for varying use of the
     * error label, which is superior to a method in this case. LAMBDA EXPRESSION #2 is located in the getTwoYears method of the SchedulesFormController.)
     *
     * Initializes this controller when changing views. Populates countries map, divisions map and contacts list, as well as checks for
     * an appointment starting in the next 15 minutes, only on the first time arriving at this view.
     *
     * @param url NOT USED.
     * @param rb NOT USED.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        errorLabel.setVisible(false);

        //Lambda
        changeSceneError = ()-> {errorLabel.setText("Something went terribly wrong! Exit program now, please!");
            errorLabel.setTextFill(Paint.valueOf(Color.RED.toString()));
            errorLabel.setVisible(true);};

        //Lambda
        Error sqlError = ()-> {errorLabel.setText("There has been an unexpected SQL error! Exit program now, please!");
            errorLabel.setTextFill(Paint.valueOf(Color.RED.toString()));
            errorLabel.setVisible(true);};

        if(!beenHere)
        {
            contacts = FXCollections.observableArrayList();
            divisions = FXCollections.observableHashMap();
            countries = FXCollections.observableHashMap();
            appointments = new ArrayList<Appointment>();

            //try to get all contacts from database
            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT Contact_ID, Contact_Name FROM contacts");
                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                int contactID;
                String contactName;

                while (result.next())
                {
                    contactID = result.getInt("Contact_ID");
                    contactName = result.getString("Contact_Name");

                    contacts.add(new Contact(contactID, contactName));
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
                sqlError.showError();
            }


            //try to get all first divisions from database
            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT Division_ID, Division, Country_ID FROM first_level_divisions");
                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                int divisionID;
                String division;
                int countryID;

                while (result.next())
                {
                    divisionID = result.getInt("Division_ID");
                    division = result.getString("Division");
                    countryID = result.getInt("Country_ID");

                    divisions.put(divisionID ,new Division(divisionID, division, countryID));
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
                sqlError.showError();
            }


            //try to get all countries from database
            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT Country_ID, Country FROM countries");
                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                int countryID;
                String country;

                while (result.next())
                {
                    countryID = result.getInt("Country_ID");
                    country = result.getString("Country");

                    countries.put(countryID ,new Country(countryID, country));
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
                sqlError.showError();
            }


            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM appointments WHERE DATE(Start) = ?");

                DBQuery.getPreparedStatement().setDate(1, Date.valueOf(LocalDate.now()));

                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                int appointmentID;
                String title;
                String description;
                String location;
                String type;
                Timestamp start;
                Timestamp end;
                Timestamp createdDate;
                String createdBy;
                Timestamp lastUpdate;
                String lastUpdatedBy;
                int customerID;
                int userID;
                int contactID;
                Contact contact = null;
                LocalDate startDate;
                LocalTime startTime;
                LocalDate endDate;
                LocalTime endTime;


                while (result.next())
                {
                    appointmentID = result.getInt("Appointment_ID");
                    title = result.getString("Title");
                    description = result.getString("Description");
                    location = result.getString("Location");
                    type = result.getString("Type");
                    start = result.getTimestamp("Start");
                    end = result.getTimestamp("End");
                    createdDate = result.getTimestamp("Create_Date");
                    createdBy = result.getString("Created_By");
                    lastUpdate = result.getTimestamp("Last_Update");
                    lastUpdatedBy = result.getString("Last_Updated_By");
                    customerID = result.getInt("Customer_ID");
                    userID = result.getInt("User_ID");
                    contactID = result.getInt("Contact_ID");

                    for (Contact cont : MainformController.contacts)
                    {
                        if (contactID == cont.getContactID())
                        {
                            contact = cont;
                            break;
                        }
                    }

                    startDate = start.toLocalDateTime().toLocalDate();
                    startTime = start.toLocalDateTime().toLocalTime();
                    endDate = end.toLocalDateTime().toLocalDate();
                    endTime = end.toLocalDateTime().toLocalTime();


                    appointments.add(new Appointment(appointmentID, title, description, location, type, start, end, createdDate,
                            createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact, startDate, startTime, endDate, endTime));
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                sqlError.showError();
            }

            Appointment comingSoonest = null;

            for(Appointment appointment : appointments)
            {
                if(appointment.getStartTime().isBefore(LocalTime.now()))
                {
                    continue;
                }
                if(comingSoonest == null)
                {
                    comingSoonest = appointment;
                    continue;
                }

                if(appointment.getStartTime().isBefore(comingSoonest.getStartTime()))
                {
                    comingSoonest = appointment;
                }
            }

            errorLabel.setTextFill(Paint.valueOf(Color.BLACK.toString()));

            //Lambda
            Error noAppointment = () -> {errorLabel.setText("There is no appointment in the next 15 minutes.");errorLabel.setVisible(true);};

            if(comingSoonest == null)
            {
                noAppointment.showError();
            }
            else
            {

                if (comingSoonest.getStartTime().isAfter(LocalTime.now()) && comingSoonest.getStartTime().isBefore(LocalTime.now().plusMinutes(15)))
                {
                    errorLabel.setText("Appointment " + comingSoonest.getAppointmentID() + " starts at " + comingSoonest.getStartTime() + " " + comingSoonest.getStartDate());
                    errorLabel.setVisible(true);
                } else
                {
                    noAppointment.showError();
                }

            }

            beenHere = true;
        }
    }

    /**
     * Changes scene to the Appointments Form.
     *
     * @param event NOT USED.
     */
    @FXML
    public void appointmentsButtonAction(ActionEvent event)
    {
        try
        {
            Scheduling.changeScene("appointmentsform.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            changeSceneError.showError();
        }
    }

    /**
     * Changes scene to the Customers Form.
     *
     * @param event NOT USED.
     */
    @FXML
    public void customersButtonAction(ActionEvent event)
    {
        try
        {
            Scheduling.changeScene("customersform.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            changeSceneError.showError();
        }
    }

    /**
     * Exits the application.
     *
     * @param event NOT USED.
     */
    @FXML
    public void exitButtonAction(ActionEvent event)
    {
        System.exit(0);
    }

    /**
     * Changes scene to the Reports Form.
     *
     * @param event NOT USED.
     */
    @FXML
    public void reportsButtonAction(ActionEvent event)
    {
        try
        {
            Scheduling.changeScene("reportsform.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            changeSceneError.showError();
        }
    }

    /**
     * Changes scene to the Schedules Form.
     *
     * @param event NOT USED.
     */
    @FXML
    public void schedulesButtonAction(ActionEvent event)
    {
        try
        {
            Scheduling.changeScene("schedulesform.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            changeSceneError.showError();
        }
    }


    /**
     * Functional Interface for controlling the error label.
     */
    @FunctionalInterface
    public interface Error
    {
        void showError();
    }

}

