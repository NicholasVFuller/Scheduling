package Scheduling;

import DB.DBConnection;
import DB.DBQuery;
import SchedulingModel.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller of the Reports Form
 */
public class ReportsformController implements Initializable
{

    @FXML
    private RadioButton customerRadio;

    @FXML
    private RadioButton contactRadio;

    @FXML
    private RadioButton divisionRadio;

    @FXML
    private TextArea textarea;


    /**
     * Initializes this controller when changing views. Shows the customer appointments report by default.
     *
     * @param url NOT USED.
     * @param resourceBundle NOT USED.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        textarea.setEditable(false);
        customerRadio.setSelected(true);
        customerRadioAction(new ActionEvent());
    }


    /**
     * Provides the user with a report of schedules for each contact.
     *
     * @param event NOT USED.
     */
    @FXML
    public void contactRadioAction(ActionEvent event)
    {
        customerRadio.setSelected(false);
        divisionRadio.setSelected(false);
        if(contactRadio.isSelected() == false)
        {
            contactRadio.setSelected(true);
            return;
        }

        textarea.clear();

        textarea.setText("Here are schedules for each contact :\n\n\n");

        for(Contact contact : MainformController.contacts)
        {
            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID  FROM appointments " +
                        "WHERE Contact_ID = ? ORDER BY Start ASC");
                DBQuery.getPreparedStatement().setInt(1, contact.getContactID());
                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                textarea.setText(textarea.getText() + contact.getContactName() + " :\n\n");

                int appointmentID;
                String title;
                String type;
                String description;
                LocalDateTime start;
                LocalDateTime end;
                int customerID;

                while (result.next())
                {
                    appointmentID = result.getInt("Appointment_ID");
                    title = result.getString("Title");
                    type = result.getString("Type");
                    description = result.getString("Description");
                    start = result.getTimestamp("Start").toLocalDateTime();
                    end = result.getTimestamp("End").toLocalDateTime();
                    customerID = result.getInt("Customer_ID");

                    textarea.appendText("\tAppointment ID =" + appointmentID + ", Title = " + title + ", Type = " + type + ", Description = " + description +
                            ", Start Time = " + start.format(DateTimeFormatter.ofPattern("h:mm  M/d/y")) + ", End Time = " + end.format(DateTimeFormatter.ofPattern("h:mm  M/d/y")) + ", Customer ID =" + customerID + "\n");
                }

                textarea.setText(textarea.getText() + "\n\n");

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Provides the user with a report of all customer appointments by type and month.
     *
     * @param event NOT USED.
     */
    @FXML
    public void customerRadioAction(ActionEvent event)
    {
        contactRadio.setSelected(false);
        divisionRadio.setSelected(false);
        if(customerRadio.isSelected() == false)
        {
            customerRadio.setSelected(true);
            return;
        }

        textarea.clear();

        HashMap<String, HashMap<Integer, Integer>> types = new HashMap<String, HashMap<Integer,Integer>>();

        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT Type, Start  FROM appointments");
            ResultSet result = DBQuery.getPreparedStatement().executeQuery();

            String type;
            int month;

            while(result.next())
            {
                type = result.getString("Type");
                month = result.getTimestamp("Start").toLocalDateTime().getMonth().getValue();

                if(!types.containsKey(type))
                {
                    types.put(type, new HashMap<Integer, Integer>());
                    types.get(type).put(month, 1);
                }
                else
                {
                    if(types.get(type).containsKey(month))
                    {
                        types.get(type).put(month, types.get(type).get(month) + 1);
                    }
                    else
                    {
                        types.get(type).put(month, 1);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        int total = 0;

        textarea.setText("The total number of customer appointments by type and month :\n\n\n");

        for(String type : types.keySet())
        {
            textarea.setText(textarea.getText() + type + " :\n\n");

            if(types.get(type).containsKey(1))
            {
                textarea.setText(textarea.getText() + "\tJanuary : " + types.get(type).get(1) + "\n");
                total += types.get(type).get(1);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tJanuary : 0\n");
            }

            if(types.get(type).containsKey(2))
            {
                textarea.setText(textarea.getText() + "\tFebruary : " + types.get(type).get(2) + "\n");
                total += types.get(type).get(2);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tFebruary : 0\n");
            }

            if(types.get(type).containsKey(3))
            {
                textarea.setText(textarea.getText() + "\tMarch : " + types.get(type).get(3) + "\n");
                total += types.get(type).get(3);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tMarch : 0\n");
            }

            if(types.get(type).containsKey(4))
            {
                textarea.setText(textarea.getText() + "\tApril : " + types.get(type).get(4) + "\n");
                total += types.get(type).get(4);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tApril : 0\n");
            }

            if(types.get(type).containsKey(5))
            {
                textarea.setText(textarea.getText() + "\tMay : " + types.get(type).get(5) + "\n");
                total += types.get(type).get(5);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tMay : 0\n");
            }

            if(types.get(type).containsKey(6))
            {
                textarea.setText(textarea.getText() + "\tJune : " + types.get(type).get(6) + "\n");
                total += types.get(type).get(6);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tJune : 0\n");
            }

            if(types.get(type).containsKey(7))
            {
                textarea.setText(textarea.getText() + "\tJuly : " + types.get(type).get(7) + "\n");
                total += types.get(type).get(7);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tJuly : 0\n");
            }

            if(types.get(type).containsKey(8))
            {
                textarea.setText(textarea.getText() + "\tAugust : " + types.get(type).get(8) + "\n");
                total += types.get(type).get(8);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tAugust : 0\n");
            }

            if(types.get(type).containsKey(9))
            {
                textarea.setText(textarea.getText() + "\tSeptember : " + types.get(type).get(9) + "\n");
                total += types.get(type).get(9);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tSeptember : 0\n");
            }

            if(types.get(type).containsKey(10))
            {
                textarea.setText(textarea.getText() + "\tOctober : " + types.get(type).get(10) + "\n");
                total += types.get(type).get(10);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tOctober : 0\n");
            }

            if(types.get(type).containsKey(11))
            {
                textarea.setText(textarea.getText() + "\tNovember : " + types.get(type).get(11) + "\n");
                total += types.get(type).get(11);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tNovember : 0\n");
            }

            if(types.get(type).containsKey(12))
            {
                textarea.setText(textarea.getText() + "\tDecember : " + types.get(type).get(12) + "\n");
                total += types.get(type).get(12);
            }
            else
            {
                textarea.setText(textarea.getText() + "\tDecember : 0\n");
            }

            textarea.setText(textarea.getText() + "\nTotal = " + total + "\n\n");

            total = 0;

        }
    }

    /**
     * Provides the user with a report of customer appointments in each division.
     * If there are no appointments in a division, the division will not be shown in the report.
     *
     * @param event NOT USED.
     */
    @FXML
    public void divisionRadioAction(ActionEvent event)
    {
        customerRadio.setSelected(false);
        contactRadio.setSelected(false);
        if(divisionRadio.isSelected() == false)
        {
            divisionRadio.setSelected(true);
            return;
        }

        textarea.clear();

        textarea.setText("Here are the appointments for each First-Level Division :\n\n");

        int currentDivision = -1;

        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT appointments.Appointment_ID, appointments.Title, appointments.Type, " +
                    "appointments.Description, " +
                    "appointments.Start, appointments.End, appointments.Customer_ID, customers.Division_ID  FROM appointments " +
                    "INNER JOIN customers ON appointments.Customer_ID=customers.Customer_ID " +
                    "ORDER BY customers.Division_ID, appointments.Start");

            ResultSet result = DBQuery.getPreparedStatement().executeQuery();

            int appointmentID;
            String title;
            String type;
            String description;
            LocalDateTime start;
            LocalDateTime end;
            int customerID;
            int divisionID;

            while (result.next())
            {
                appointmentID = result.getInt("Appointment_ID");
                title = result.getString("Title");
                type = result.getString("Type");
                description = result.getString("Description");
                start = result.getTimestamp("Start").toLocalDateTime();
                end = result.getTimestamp("End").toLocalDateTime();
                customerID = result.getInt("Customer_ID");
                divisionID = result.getInt("Division_ID");

                if(currentDivision == divisionID)
                {
                    textarea.appendText("\tAppointment ID =" + appointmentID + ", Title = " + title + ", Type = " + type + ", Description = " + description +
                            ", Start Time = " + start.format(DateTimeFormatter.ofPattern("h:mm  M/d/y")) +
                            ", End Time = " + end.format(DateTimeFormatter.ofPattern("h:mm  M/d/y")) + ", Customer ID =" + customerID + "\n");
                }
                else
                {
                    textarea.appendText("\n" + MainformController.divisions.get(divisionID).getDivision() + ", " +
                            MainformController.countries.get(MainformController.divisions.get(divisionID).getCountryID()).getCountry() + " :\n\n");

                    textarea.appendText("\tAppointment ID =" + appointmentID + ", Title = " + title + ", Type = " + type + ", Description = " + description +
                            ", Start Time = " + start.format(DateTimeFormatter.ofPattern("h:mm  M/d/y")) +
                            ", End Time = " + end.format(DateTimeFormatter.ofPattern("h:mm  M/d/y")) + ", Customer ID =" + customerID + "\n");

                    currentDivision = divisionID;
                }
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Changes scenes to the Main Form. (or returns to the Main Menu.)
     *
     * @param event NOT USED.
     */
    @FXML
    public void returnButtonAction(ActionEvent event)
    {
        try
        {
            Scheduling.changeScene("mainform.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

