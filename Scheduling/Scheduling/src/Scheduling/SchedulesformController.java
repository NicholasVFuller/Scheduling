package Scheduling;

import DB.DBConnection;
import DB.DBQuery;
import SchedulingModel.Appointment;
import SchedulingModel.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller of the Schedules Form.
 */
public class SchedulesformController implements Initializable
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
    private RadioButton monthRadio;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private ComboBox<String> combo;

    private ArrayList<Appointment> appointments;

    private HashMap<String, YearMonth> months;

    private HashMap<String, LocalDate> weeks;

    private LocalDate firstMonday;

    /**
     * Initializes this controller when changing views. Shows the by month selection by default.
     *
     * @param url NOT USED.
     * @param resourceBundle NOT USED.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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

        appointments = new ArrayList<Appointment>();

        getTwoYears();

        months = new HashMap<String, YearMonth>();
        weeks = new HashMap<String, LocalDate>();

        YearMonth now = YearMonth.now();
        YearMonth then = YearMonth.of(now.getYear() +2, now.getMonthValue() + 1);
        String monthYear;

        while(now.isBefore(then))
        {
            monthYear = now.getMonth().toString() + " " + now.getYear();
            months.put(monthYear, now);

            if(now.getMonthValue() == 12)
            {
                now = YearMonth.of(now.getYear() + 1, 1);
            }
            else
            {
                now = YearMonth.of(now.getYear(), now.getMonthValue() + 1);
            }
        }


        //Weeks
        LocalDate startDate = LocalDate.now();
        startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
        LocalDate lastDate = startDate.plusYears(2);
        lastDate = lastDate.plusMonths(1);

        switch (startDate.getDayOfWeek())
        {
            case SUNDAY:
                startDate = startDate.plusDays(1);
                break;
            case MONDAY:
                break;
            case TUESDAY:
                startDate = startDate.minusDays(1);
                break;
            case WEDNESDAY:
                startDate = startDate.minusDays(2);
                break;
            case THURSDAY:
                startDate = startDate.minusDays(3);
                break;
            case FRIDAY:
                startDate = startDate.minusDays(4);
                break;
            case SATURDAY:
                startDate = startDate.plusDays(2);
                break;
        }

        firstMonday = startDate;

        LocalDate endDate;
        String toPut;

        while(startDate.isBefore(lastDate))
        {
            endDate = startDate.plusDays(4);

            toPut = startDate.format(DateTimeFormatter.ofPattern("M/d/y")) + " - " + endDate.format(DateTimeFormatter.ofPattern("M/d/y"));

            weeks.put(toPut, startDate);

            startDate = startDate.plusDays(7);
        }




        monthRadioAction(new ActionEvent());
    }

    /**
     * Shows the selected Month or Week schedule based on which radio button is selected.
     *
     * @param event NOT USED.
     */
    @FXML
    public void comboAction(ActionEvent event)
    {
        appointmentTable.getItems().clear();
        if(combo.getSelectionModel().isEmpty())
        {
            return;
        }
        if(monthRadio.isSelected())
        {
            YearMonth month = months.get(combo.getSelectionModel().getSelectedItem());

            for(Appointment appointment : appointments)
            {
                if(appointment.getStartDate().getYear() == month.getYear() && appointment.getStartDate().getMonthValue() == month.getMonthValue())
                {
                    appointmentTable.getItems().add(appointment);
                }
            }
        }
        else
        {
            LocalDate date = weeks.get(combo.getSelectionModel().getSelectedItem());
            LocalDate endDate = date.plusDays(4);

            for(Appointment appointment : appointments)
            {
                if((appointment.getStartDate().isAfter(date) && appointment.getStartDate().isBefore(endDate)) ||
                appointment.getStartDate().equals(date) || appointment.getStartDate().equals((endDate)))
                {
                    appointmentTable.getItems().add(appointment);
                }
            }
        }
    }

    /**
     * Populates the combo box with months, allowing the user to select and view schedules by month.
     *
     * @param event NOT USED.
     */
    @FXML
    public void monthRadioAction(ActionEvent event)
    {
        weekRadio.setSelected(false);
        if(monthRadio.isSelected() == false)
        {
            monthRadio.setSelected(true);
            return;
        }

        combo.getItems().clear();

        YearMonth now = YearMonth.now();
        YearMonth then = YearMonth.of(now.getYear() +2, now.getMonthValue() + 1);
        String monthYear;

        while(now.isBefore(then))
        {
            monthYear = now.getMonth().toString() + " " + now.getYear();
            combo.getItems().add(monthYear);

            if(now.getMonthValue() == 12)
            {
                now = YearMonth.of(now.getYear() + 1, 1);
            }
            else
            {
                now = YearMonth.of(now.getYear(), now.getMonthValue() + 1);
            }
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

    /**
     * Populates the combo box with weeks, allowing the user to select and view schedules by week.
     *
     * @param event
     */
    @FXML
    public void weekRadioAction(ActionEvent event)
    {
        monthRadio.setSelected(false);
        if(weekRadio.isSelected() == false)
        {
            weekRadio.setSelected(true);
            return;
        }

        combo.getItems().clear();

        LocalDate startDate = LocalDate.now();
        startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
        LocalDate lastDate = startDate.plusYears(2);
        lastDate = lastDate.plusMonths(1);

        startDate = firstMonday;

        LocalDate endDate;
        String toPut;

        while(startDate.isBefore(lastDate))
        {
            endDate = startDate.plusDays(4);

            toPut = startDate.format(DateTimeFormatter.ofPattern("M/d/y")) + " - " + endDate.format(DateTimeFormatter.ofPattern("M/d/y"));

            combo.getItems().add(toPut);

            startDate = startDate.plusDays(7);
        }



    }

    /**
     * (LAMBDA EXPRESSION #2 is LOCATED HERE. This Functional Interface in conjunction with these lambda expressions allow for any time conversion to be easily constructed
     * and adapted in the code, which is superior to a method in this case. LAMBDA EXPRESSION #1 is located in the initialize method of the MainFormController.)
     *
     * Gets the next two years of appointment data from the database and stores it locally to be gone through by month or week by the user.
     */
    public void getTwoYears()
    {
        YearMonth today = YearMonth.now();
        YearMonth twoYearsLater = YearMonth.of(today.getYear() + 2, today.getMonthValue());

        int lastDay = -1;

        if(twoYearsLater.getMonthValue() == 2 && !twoYearsLater.isLeapYear())
        {
            lastDay = 28;
        }
        else
        {
            lastDay = twoYearsLater.getMonth().maxLength();
        }

        LocalDateTime localToday = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0);
        LocalDateTime localTwo = LocalDateTime.of(twoYearsLater.getYear(), twoYearsLater.getMonthValue(), lastDay, 23, 59);

        Timestamp tTimestamp = Timestamp.valueOf(localToday);
        Timestamp twoTimestamp = Timestamp.valueOf(localTwo);

        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?");
            DBQuery.getPreparedStatement().setTimestamp(1, tTimestamp);
            DBQuery.getPreparedStatement().setTimestamp(2, twoTimestamp);

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

                //LAMBDAS
                TimeConverter<LocalDate, Timestamp> convertToDate = time -> time.toLocalDateTime().toLocalDate();
                TimeConverter<LocalTime, Timestamp> convertToTime = time -> time.toLocalDateTime().toLocalTime();

                startDate = convertToDate.convert(start);
                startTime = convertToTime.convert(start);
                endDate = convertToDate.convert(end);
                endTime = convertToTime.convert(end);


                appointments.add(new Appointment(appointmentID, title, description, location, type, start, end, createdDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID, contact, startDate, startTime, endDate, endTime));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public interface TimeConverter<T, T2>
    {
        public T convert(T2 time);
    }

}

