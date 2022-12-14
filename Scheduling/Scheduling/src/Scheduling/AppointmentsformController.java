package Scheduling;

import DB.DBConnection;
import DB.DBQuery;
import SchedulingModel.Appointment;
import SchedulingModel.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller of the Appointment Form.
 */
public class AppointmentsformController implements Initializable
{

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> startTimeColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> endDateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> endTimeColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML
    private TableColumn<Appointment, Integer> userIDColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label appointmentIDLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField appointmentIDTextfield;
    @FXML
    private TextField titleTextfield;
    @FXML
    private Label addUpdateLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label deleteConfirmLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextArea descriptionTextarea;
    @FXML
    private Label locationLabel;
    @FXML
    private TextField locationTextfield;
    @FXML
    private ComboBox<String> contactCombo;
    @FXML
    private Label typeLabel;
    @FXML
    private TextField typeTextfield;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private ComboBox<Integer> startHourCombo;
    @FXML
    private ComboBox<Integer> startMinuteCombo;
    @FXML
    private ComboBox<String> startAmPmCombo;
    @FXML
    private Label endDateLabel;
    @FXML
    private TextField customerIDTextfield;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label endTimeLabel;
    @FXML
    private ComboBox<Integer> endHourCombo;
    @FXML
    private ComboBox<Integer> endMinuteCombo;
    @FXML
    private ComboBox<String> endAmPmCombo;
    @FXML
    private Label customerIDLabel;
    @FXML
    private Label userIDLabel;
    @FXML
    private TextField userIDTextfield;

    /**
     * Initializes this controller when changing views. Populates the appointments table with data from the database.
     *
     * @param url NOT USED.
     * @param resourceBundle NOT USED.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        selectionMode();

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("startDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalTime>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("endDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalTime>("endTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));


        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM appointments");
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


                appointmentTable.getItems().add(new Appointment(appointmentID, title, description, location, type, start, end, createdDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact, startDate, startTime, endDate, endTime));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Changes the scene to accept data from the user for adding a new appointment.
     *
     * @param event NOT USED.
     */
    @FXML
    public void addButtonAction(ActionEvent event)
    {
        addUpdateMode("add");

        appointmentIDTextfield.setText(getNewAppID().toString());

        ObservableList<String> contacts = FXCollections.observableArrayList();

        for (Contact contact : MainformController.contacts)
        {
            contacts.add(contact.getContactName());
        }

        contactCombo.setItems(contacts);

        for (int i = 1; i <= 12; i++)
        {
            startHourCombo.getItems().add(i);
            endHourCombo.getItems().add(i);
        }

        for (int i = 0; i <= 59; i++)
        {
            startMinuteCombo.getItems().add(i);
            endMinuteCombo.getItems().add(i);
        }

        startAmPmCombo.getItems().add("AM");
        startAmPmCombo.getItems().add("PM");
        endAmPmCombo.getItems().add("AM");
        endAmPmCombo.getItems().add("PM");


    }

    /**
     * Changes scene back to default "selection mode". Saves/commits no data.
     *
     * @param event NOT USED.
     */
    @FXML
    public void cancelButtonAction(ActionEvent event)
    {
        selectionMode();
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
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the currently selected appointment.
     * Alerts the user if no appointment is selected or appointment was not deleted.
     *
     * @param event NOT USED.
     */
    @FXML
    public void deleteButtonAction(ActionEvent event)
    {
        if (appointmentTable.getSelectionModel().isEmpty())
        {
            deleteConfirmLabel.setText("Select Appointment to delete.");
            return;
        }

        String apptIDToDelete = String.valueOf(appointmentTable.getSelectionModel().getSelectedItem().getAppointmentID());

        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "DELETE FROM appointments WHERE Appointment_ID=" + apptIDToDelete);
            DBQuery.getPreparedStatement().execute();

            if (DBQuery.getPreparedStatement().getUpdateCount() > 0)
            {
                Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

                deleteConfirmLabel.setText("Appointment " + String.valueOf(appointment.getAppointmentID()) + " (" + appointment.getType() + ")\nhas been cancelled.");
                deleteConfirmLabel.setVisible(true);

                appointmentTable.getItems().remove(appointmentTable.getSelectionModel().getSelectedItem());
            } else
            {
                deleteConfirmLabel.setText("Appointment was not deleted...");
                deleteConfirmLabel.setVisible(true);
            }
        } catch (SQLException e)
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
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Commits the currently entered data to the database and updates local data to reflect changes.
     * Otherwise, asks user to enter or correct data for proper committal.
     *
     * @param event NOT USED.
     */
    @FXML
    public void saveButtonAction(ActionEvent event)
    {

        if (titleTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment title.");
            errorLabel.setVisible(true);
            return;
        }

        if (descriptionTextarea.getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment description.");
            errorLabel.setVisible(true);
            return;
        }

        if (locationTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment location.");
            errorLabel.setVisible(true);
            return;
        }

        if (contactCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select an Appointment contact.");
            errorLabel.setVisible(true);
            return;
        }

        if (typeTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment type.");
            errorLabel.setVisible(true);
            return;
        }

        if(startDatePicker.getValue() == null)
        {
            errorLabel.setText("Enter a valid Appointment start date.");
            errorLabel.setVisible(true);
            return;
        }

        if (startDatePicker.getEditor().getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment start date.");
            errorLabel.setVisible(true);
            return;
        }

        if (startHourCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select an Appointment start hour.");
            errorLabel.setVisible(true);
            return;
        }

        if(startMinuteCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select an Appointment start minute.");
            errorLabel.setVisible(true);
            return;
        }

        if(startAmPmCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select Appointment start AM or PM.");
            errorLabel.setVisible(true);
            return;
        }

        if(endDatePicker.getValue() == null)
        {
            errorLabel.setText("Enter a valid Appointment end date.");
            errorLabel.setVisible(true);
            return;
        }

        if(endDatePicker.getEditor().getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment end date.");
            errorLabel.setVisible(true);
            return;
        }

        if (endHourCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select an Appointment end hour.");
            errorLabel.setVisible(true);
            return;
        }

        if(endMinuteCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select an Appointment end minute.");
            errorLabel.setVisible(true);
            return;
        }

        if(endAmPmCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select Appointment end AM or PM.");
            errorLabel.setVisible(true);
            return;
        }

        if(customerIDTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment Customer ID.");
            errorLabel.setVisible(true);
            return;
        }

        if(userIDTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter an Appointment User ID.");
            errorLabel.setVisible(true);
            return;
        }


        int appointmentID = Integer.valueOf(appointmentIDTextfield.getText());

        if (addUpdateLabel.getText().equals("Add :"))
        {
            int customerID = Integer.valueOf(customerIDTextfield.getText());
            int userID = Integer.valueOf(userIDTextfield.getText());

            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM customers WHERE Customer_ID=" + String.valueOf(customerID));

                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                if(result.next())
                {
                    //The customer exists, so do nothing.
                }
                else
                {
                    errorLabel.setText("Enter a Customer ID of an existing Customer only.");
                    errorLabel.setVisible(true);
                    return;
                }


                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM users WHERE User_ID=" + String.valueOf(userID));

                result = DBQuery.getPreparedStatement().executeQuery();

                if(result.next())
                {
                    //The user exists, so do nothing.
                }
                else
                {
                    errorLabel.setText("Enter a User ID of an existing User only.");
                    errorLabel.setVisible(true);
                    return;
                }





                DBQuery.setPreparedStatement(DBConnection.getConnection(), "INSERT INTO appointments(Appointment_ID, Title, " +
                        "Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
                        "Customer_ID, User_ID, Contact_ID) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                PreparedStatement stmt = DBQuery.getPreparedStatement();

                String title = titleTextfield.getText();
                String description = descriptionTextarea.getText();
                String location = locationTextfield.getText();
                String type = typeTextfield.getText();
                Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String createdBy = "test";
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String lastUpdatedBy = "test";
                int contactID = Integer.MIN_VALUE;
                Contact contact = null;

                for(Contact cont : MainformController.contacts)
                {
                    if(contactCombo.getSelectionModel().getSelectedItem().equals(cont.getContactName()))
                    {
                        contact = cont;
                        contactID = cont.getContactID();
                        break;
                    }
                }


                LocalDate startDate = startDatePicker.getValue();
                Integer hour = startHourCombo.getValue();
                if(startAmPmCombo.getSelectionModel().getSelectedItem().equals("AM"))
                {
                    if(hour == 12)
                    {
                        hour = 0;
                    }
                }
                else
                {
                    if(hour == 12)
                    {
                        hour = 12;
                    }
                    else
                    {
                        hour += 12;
                    }
                }
                LocalTime startTime = LocalTime.of(hour, startMinuteCombo.getValue());
                LocalDate endDate = endDatePicker.getValue();

                hour = endHourCombo.getValue();
                if(endAmPmCombo.getSelectionModel().getSelectedItem().equals("AM"))
                {
                    if(hour == 12)
                    {
                        hour = 0;
                    }
                }
                else
                {
                    if(hour == 12)
                    {
                        hour = 12;
                    }
                    else
                    {
                        hour += 12;
                    }
                }
                LocalTime endTime = LocalTime.of(hour, endMinuteCombo.getValue());


                ZonedDateTime checkStart = LocalDateTime.of(startDate, startTime).atZone(ZoneId.of("America/Denver"));
                ZonedDateTime checkEnd = LocalDateTime.of(endDate, endTime).atZone(ZoneId.of("America/Denver"));

                if(checkStart.getDayOfWeek() == DayOfWeek.SATURDAY || checkStart.getDayOfWeek() == DayOfWeek.SUNDAY ||
                checkEnd.getDayOfWeek() == DayOfWeek.SATURDAY || checkEnd.getDayOfWeek() == DayOfWeek.SUNDAY)
                {
                    errorLabel.setText("Business days are Monday - Friday only.");
                    errorLabel.setVisible(true);
                    return;
                }



                ZonedDateTime estStartDay = ZonedDateTime.of(checkStart.getYear(), checkStart.getMonthValue(), checkStart.getDayOfMonth(), 8, 0, 0,
                        0, ZoneId.of("America/New_York"));
                ZonedDateTime estEndDay = ZonedDateTime.of(checkStart.getYear(), checkStart.getMonthValue(), checkStart.getDayOfMonth(), 22, 0, 0,
                    0, ZoneId.of("America/New_York"));

                if(checkEnd.isBefore(checkStart))
                {
                    errorLabel.setText("Appointment cannot end before it starts.");
                    errorLabel.setVisible(true);
                    return;
                }

                if(checkStart.isBefore(estStartDay) || checkStart.isAfter(estEndDay))
                {
                    errorLabel.setText("Appointments must take place between 8AM - 10PM EST.");
                    errorLabel.setVisible(true);
                    return;
                }

                if(checkEnd.isBefore(estStartDay) || checkEnd.isAfter(estEndDay))
                {
                    errorLabel.setText("Appointments must take place between 8AM - 10PM EST.");
                    errorLabel.setVisible(true);
                    return;
                }



                LocalDateTime overlapStart = checkStart.toLocalDateTime();
                LocalDateTime overlapEnd = checkEnd.toLocalDateTime();

                LocalDateTime currentStart;
                LocalDateTime currentEnd;

                for(Appointment appointment : appointmentTable.getItems())
                {
                    if(appointment.getCustomerID() == customerID)
                    {
                        currentStart = LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime());
                        currentEnd = LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime());

                        if(!(overlapStart.isBefore(currentStart) || overlapStart.isAfter(currentEnd)))
                        {
                            errorLabel.setText("Appointments for a customer cannot be overlapping.");
                            errorLabel.setVisible(true);
                            return;
                        }

                        if(!(overlapEnd.isBefore(currentStart) || overlapEnd.isAfter(currentEnd)))
                        {
                            errorLabel.setText("Appointments for a customer cannot be overlapping.");
                            errorLabel.setVisible(true);
                            return;
                        }
                    }
                }




                Timestamp start = Timestamp.valueOf(overlapStart);
                Timestamp end = Timestamp.valueOf(overlapEnd);



                stmt.setInt(1, appointmentID);
                stmt.setString(2, title);
                stmt.setString(3, description);
                stmt.setString(4, location);
                stmt.setString(5, type);
                stmt.setTimestamp(6, start);
                stmt.setTimestamp(7, end);
                stmt.setTimestamp(8, createdDate);
                stmt.setString(9, createdBy);
                stmt.setTimestamp(10, lastUpdate);
                stmt.setString(11, lastUpdatedBy);
                stmt.setInt(12, customerID);
                stmt.setInt(13, userID);
                stmt.setInt(14, contactID);

                DBQuery.getPreparedStatement().execute();

                if(stmt.getUpdateCount() > 0)
                {
                    Appointment appt = new Appointment(appointmentID,title, description, location, type, start, end, createdDate, createdBy, lastUpdate, lastUpdatedBy,
                            customerID, userID, contactID, contact, startDate, startTime, endDate, endTime);

                    appointmentTable.getItems().add(appt);

                    selectionMode();
                    deleteConfirmLabel.setText("Appointment added.");
                    deleteConfirmLabel.setVisible(true);
                }
                else
                {
                    selectionMode();
                    deleteConfirmLabel.setText("Appointment not added...");
                    deleteConfirmLabel.setVisible(true);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            int customerID = Integer.valueOf(customerIDTextfield.getText());
            int userID = Integer.valueOf(userIDTextfield.getText());

            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM customers WHERE Customer_ID=" + String.valueOf(customerID));

                ResultSet result = DBQuery.getPreparedStatement().executeQuery();

                if(result.next())
                {
                    //The customer exists, so do nothing.
                }
                else
                {
                    errorLabel.setText("Enter a Customer ID of an existing Customer only.");
                    errorLabel.setVisible(true);
                    return;
                }


                DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM users WHERE User_ID=" + String.valueOf(userID));

                result = DBQuery.getPreparedStatement().executeQuery();

                if(result.next())
                {
                    //The user exists, so do nothing.
                }
                else
                {
                    errorLabel.setText("Enter a User ID of an existing User only.");
                    errorLabel.setVisible(true);
                    return;
                }





                DBQuery.setPreparedStatement(DBConnection.getConnection(), "UPDATE appointments SET Title=?," +
                        "Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?, " +
                        "Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID =" + String.valueOf(appointmentID));

                PreparedStatement stmt = DBQuery.getPreparedStatement();

                Appointment current = null;

                for(Appointment appointment : appointmentTable.getItems())
                {
                    if(appointment.getAppointmentID() == appointmentID)
                    {
                        current = appointment;
                        break;
                    }
                }

                String title = titleTextfield.getText();
                String description = descriptionTextarea.getText();
                String location = locationTextfield.getText();
                String type = typeTextfield.getText();
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String lastUpdatedBy = "test";
                int contactID = Integer.MIN_VALUE;
                Contact contact = null;

                for(Contact cont : MainformController.contacts)
                {
                    if(contactCombo.getSelectionModel().getSelectedItem().equals(cont.getContactName()))
                    {
                        contact = cont;
                        contactID = cont.getContactID();
                        break;
                    }
                }


                LocalDate startDate = startDatePicker.getValue();
                Integer hour = startHourCombo.getValue();
                if(startAmPmCombo.getSelectionModel().getSelectedItem().equals("AM"))
                {
                    if(hour == 12)
                    {
                        hour = 0;
                    }
                }
                else
                {
                    if(hour == 12)
                    {
                        hour = 12;
                    }
                    else
                    {
                        hour += 12;
                    }
                }
                LocalTime startTime = LocalTime.of(hour, startMinuteCombo.getValue());
                LocalDate endDate = endDatePicker.getValue();

                hour = endHourCombo.getValue();
                if(endAmPmCombo.getSelectionModel().getSelectedItem().equals("AM"))
                {
                    if(hour == 12)
                    {
                        hour = 0;
                    }
                }
                else
                {
                    if(hour == 12)
                    {
                        hour = 12;
                    }
                    else
                    {
                        hour += 12;
                    }
                }
                LocalTime endTime = LocalTime.of(hour, endMinuteCombo.getValue());


                ZonedDateTime checkStart = LocalDateTime.of(startDate, startTime).atZone(ZoneId.of("America/Denver"));
                ZonedDateTime checkEnd = LocalDateTime.of(endDate, endTime).atZone(ZoneId.of("America/Denver"));

                if(checkStart.getDayOfWeek() == DayOfWeek.SATURDAY || checkStart.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        checkEnd.getDayOfWeek() == DayOfWeek.SATURDAY || checkEnd.getDayOfWeek() == DayOfWeek.SUNDAY)
                {
                    errorLabel.setText("Business days are Monday - Friday only.");
                    errorLabel.setVisible(true);
                    return;
                }



                ZonedDateTime estStartDay = ZonedDateTime.of(checkStart.getYear(), checkStart.getMonthValue(), checkStart.getDayOfMonth(), 8, 0, 0,
                        0, ZoneId.of("America/New_York"));
                ZonedDateTime estEndDay = ZonedDateTime.of(checkStart.getYear(), checkStart.getMonthValue(), checkStart.getDayOfMonth(), 22, 0, 0,
                        0, ZoneId.of("America/New_York"));

                if(checkEnd.isBefore(checkStart))
                {
                    errorLabel.setText("Appointment cannot end before it starts.");
                    errorLabel.setVisible(true);
                    return;
                }

                if(checkStart.isBefore(estStartDay) || checkStart.isAfter(estEndDay))
                {
                    errorLabel.setText("Appointments must take place between 8AM - 10PM EST.");
                    errorLabel.setVisible(true);
                    return;
                }

                if(checkEnd.isBefore(estStartDay) || checkEnd.isAfter(estEndDay))
                {
                    errorLabel.setText("Appointments must take place between 8AM - 10PM EST.");
                    errorLabel.setVisible(true);
                    return;
                }


                LocalDateTime overlapStart = checkStart.toLocalDateTime();
                LocalDateTime overlapEnd = checkEnd.toLocalDateTime();

                LocalDateTime currentStart;
                LocalDateTime currentEnd;

                for(Appointment appointment : appointmentTable.getItems())
                {
                    if(appointment.getCustomerID() == customerID)
                    {
                        currentStart = LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime());
                        currentEnd = LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime());

                        if(!(overlapStart.isBefore(currentStart) || overlapStart.isAfter(currentEnd)))
                        {
                            errorLabel.setText("Appointments for a customer cannot be overlapping.");
                            errorLabel.setVisible(true);
                            return;
                        }

                        if(!(overlapEnd.isBefore(currentStart) || overlapEnd.isAfter(currentEnd)))
                        {
                            errorLabel.setText("Appointments for a customer cannot be overlapping.");
                            errorLabel.setVisible(true);
                            return;
                        }
                    }
                }


                Timestamp start = Timestamp.valueOf(LocalDateTime.of(startDate, startTime));
                Timestamp end = Timestamp.valueOf(LocalDateTime.of(endDate, endTime));

                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, location);
                stmt.setString(4, type);
                stmt.setTimestamp(5, start);
                stmt.setTimestamp(6, end);
                stmt.setTimestamp(7, lastUpdate);
                stmt.setString(8, lastUpdatedBy);
                stmt.setInt(9, customerID);
                stmt.setInt(10, userID);
                stmt.setInt(11, contactID);

                DBQuery.getPreparedStatement().execute();

                if(stmt.getUpdateCount() > 0)
                {
                    current.setTitle(title);
                    current.setDescription(description);
                    current.setLocation(location);
                    current.setType(type);
                    current.setStart(start);
                    current.setEnd(end);
                    current.setLastUpdate(lastUpdate);
                    current.setLastUpdatedBy(lastUpdatedBy);
                    current.setCustomerID(customerID);
                    current.setUserID(userID);
                    current.setContactID(contactID);
                    current.setContact(contact);
                    current.setStartDate(startDate);
                    current.setStartTime(startTime);
                    current.setEndDate(endDate);
                    current.setEndTime(endTime);

                    appointmentTable.refresh();

                    selectionMode();
                    deleteConfirmLabel.setText("Appointment updated.");
                    deleteConfirmLabel.setVisible(true);
                }
                else
                {
                    selectionMode();
                    deleteConfirmLabel.setText("Appointment not updated...");
                    deleteConfirmLabel.setVisible(true);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }


    /**
     * Changes the scene by showing the user data from the currently selected appointment that can be changed by the user.
     *
     * @param event NOT USED.
     */
    @FXML
    public void updateButtonAction(ActionEvent event)
    {
        if (appointmentTable.getSelectionModel().isEmpty())
        {
            deleteConfirmLabel.setText("Select Appointment to update.");
            deleteConfirmLabel.setVisible(true);
            return;
        }

        addUpdateMode("update");

        Appointment apptToUpdate = appointmentTable.getSelectionModel().getSelectedItem();

        ObservableList<String> contacts = FXCollections.observableArrayList();

        for (Contact contact : MainformController.contacts)
        {
            contacts.add(contact.getContactName());
        }

        contactCombo.setItems(contacts);

        for (int i = 1; i <= 12; i++)
        {
            startHourCombo.getItems().add(i);
            endHourCombo.getItems().add(i);
        }

        for (int i = 0; i <= 59; i++)
        {
            startMinuteCombo.getItems().add(i);
            endMinuteCombo.getItems().add(i);
        }

        startAmPmCombo.getItems().add("AM");
        startAmPmCombo.getItems().add("PM");
        endAmPmCombo.getItems().add("AM");
        endAmPmCombo.getItems().add("PM");


        appointmentIDTextfield.setText(String.valueOf(apptToUpdate.getAppointmentID()));
        titleTextfield.setText(apptToUpdate.getTitle());
        descriptionTextarea.setText(apptToUpdate.getDescription());
        locationTextfield.setText(apptToUpdate.getLocation());
        contactCombo.setValue(apptToUpdate.getContactName());
        typeTextfield.setText(apptToUpdate.getType());
        startDatePicker.getEditor().setText(apptToUpdate.getStartDate().format(DateTimeFormatter.ofPattern("M/d/y")));

        startDatePicker.setValue(apptToUpdate.getStartDate());

        startMinuteCombo.setValue(apptToUpdate.getStartTime().getMinute());

        String amPm;
        Integer hour = apptToUpdate.getStartTime().getHour();

        if (hour / 12 == 1)
        {
            if(!(hour == 12))
            {
                hour -= 12;
            }
            amPm = "PM";
        } else
        {
            if(hour == 0)
            {
                hour = 12;
            }
            amPm = "AM";
        }

        startHourCombo.setValue(hour);
        startAmPmCombo.setValue(amPm);

        endDatePicker.getEditor().setText(apptToUpdate.getEndDate().format(DateTimeFormatter.ofPattern("M/d/y")));

        endDatePicker.setValue(apptToUpdate.getEndDate());

        endMinuteCombo.setValue(apptToUpdate.getEndTime().getMinute());

        hour = apptToUpdate.getEndTime().getHour();

        if (hour / 12 == 1)
        {
            if(!(hour == 12))
            {
                hour -= 12;
            }
            amPm = "PM";
        } else
        {
            if(hour == 0)
            {
                hour = 12;
            }
            amPm = "AM";
        }

        endHourCombo.setValue(hour);
        endAmPmCombo.setValue(amPm);

        customerIDTextfield.setText(String.valueOf(apptToUpdate.getCustomerID()));
        userIDTextfield.setText(String.valueOf(apptToUpdate.getUserID()));
    }

    /**
     * Puts the scene in default selection mode where the user can add an appointment
     * or select an appointment from the table to update or delete.
     */
    public void selectionMode()
    {
        deleteConfirmLabel.setText("");
        deleteConfirmLabel.setVisible(false);
        addButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        addUpdateLabel.setText("");
        addUpdateLabel.setVisible(false);
        appointmentIDLabel.setVisible(false);
        appointmentIDTextfield.setText("");
        appointmentIDTextfield.setVisible(false);
        titleLabel.setVisible(false);
        titleTextfield.setText("");
        titleTextfield.setVisible(false);
        descriptionLabel.setVisible(false);
        descriptionTextarea.setText("");
        descriptionTextarea.setVisible(false);
        locationLabel.setVisible(false);
        locationTextfield.setText("");
        locationTextfield.setVisible(false);
        contactCombo.valueProperty().set(null);
        contactCombo.setVisible(false);
        typeLabel.setVisible(false);
        typeTextfield.setText("");
        typeTextfield.setVisible(false);
        startDateLabel.setVisible(false);
        startDatePicker.setVisible(false);
        startTimeLabel.setVisible(false);
        startHourCombo.valueProperty().set(null);
        startHourCombo.setVisible(false);
        startMinuteCombo.valueProperty().set(null);
        startMinuteCombo.setVisible(false);
        startAmPmCombo.valueProperty().set(null);
        startAmPmCombo.setVisible(false);
        endDateLabel.setVisible(false);
        endDatePicker.setVisible(false);
        endTimeLabel.setVisible(false);
        endHourCombo.valueProperty().set(null);
        endHourCombo.setVisible(false);
        endMinuteCombo.valueProperty().set(null);
        endMinuteCombo.setVisible(false);
        endAmPmCombo.valueProperty().set(null);
        endAmPmCombo.setVisible(false);
        customerIDLabel.setVisible(false);
        customerIDTextfield.setText("");
        customerIDTextfield.setVisible(false);
        userIDLabel.setVisible(false);
        userIDTextfield.setText("");
        userIDTextfield.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    /**
     * Generates a new Appointment ID
     *
     * @return the latest Appointment ID
     */
    public Integer getNewAppID()
    {
        int biggest = -1;
        for (Appointment appointment : appointmentTable.getItems())
        {
            if (appointment.getAppointmentID() > biggest)
            {
                biggest = appointment.getAppointmentID();
            }
        }
        return biggest + 1;
    }

    /**
     * Sets the scene to add or update mode where the user can add or modify an appointment.
     *
     * @param addOrUpdate "add" will select add mode. "update" will select update mode. Any other strings will produce no results from this method.
     */
    public void addUpdateMode(String addOrUpdate)
    {
        if (addOrUpdate.equals("add"))
        {
            addUpdateLabel.setText("Add :");
        } else if (addOrUpdate.equals("update"))
        {
            addUpdateLabel.setText("Update :");
        } else
        {
            return;
        }
        addUpdateLabel.setVisible(true);
        deleteConfirmLabel.setText("");
        deleteConfirmLabel.setVisible(false);
        addButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        appointmentIDLabel.setVisible(true);
        appointmentIDTextfield.setVisible(true);
        titleLabel.setVisible(true);
        titleTextfield.setVisible(true);
        descriptionLabel.setVisible(true);
        descriptionTextarea.setVisible(true);
        locationLabel.setVisible(true);
        locationTextfield.setVisible(true);
        contactCombo.setVisible(true);
        typeLabel.setVisible(true);
        typeTextfield.setVisible(true);
        startDateLabel.setVisible(true);
        startDatePicker.getEditor().setText("");
        startDatePicker.setValue(null);
        startDatePicker.setVisible(true);
        startTimeLabel.setVisible(true);
        startHourCombo.getItems().clear();
        startHourCombo.setVisible(true);
        startMinuteCombo.getItems().clear();
        startMinuteCombo.setVisible(true);
        startAmPmCombo.getItems().clear();
        startAmPmCombo.setVisible(true);
        endDateLabel.setVisible(true);
        endDatePicker.getEditor().setText("");
        endDatePicker.setValue(null);
        endDatePicker.setVisible(true);
        endTimeLabel.setVisible(true);
        endHourCombo.getItems().clear();
        endHourCombo.setVisible(true);
        endMinuteCombo.getItems().clear();
        endMinuteCombo.setVisible(true);
        endAmPmCombo.getItems().clear();
        endAmPmCombo.setVisible(true);
        customerIDLabel.setVisible(true);
        customerIDTextfield.setVisible(true);
        userIDLabel.setVisible(true);
        userIDTextfield.setVisible(true);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
    }

}

