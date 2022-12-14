package Scheduling;

import DB.DBConnection;
import DB.DBQuery;
import SchedulingModel.Country;
import SchedulingModel.Customer;
import SchedulingModel.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller of the Customer Form.
 */
public class CustomersformController implements Initializable
{

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TableColumn<Customer, String> stateColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

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
    private Label customerIDLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private ComboBox<String> countryCombo;

    @FXML
    private ComboBox<String> regionCombo;

    @FXML
    private Label addressLabel;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField customerIDTextfield;

    @FXML
    private TextField customerNameTextfield;

    @FXML
    private TextField addressTextfield;

    @FXML
    private TextField postalCodeTextfield;

    @FXML
    private TextField phoneTextfield;

    @FXML
    private Label addUpdateLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label deleteConfirmLabel;


    /**
     * Initializes this controller when changing views. Populates the customers table with data from the database.
     *
     * @param url NOT USED.
     * @param resourceBundle NOT USED.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        selectionMode();

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));

        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT * FROM customers");
            ResultSet result = DBQuery.getPreparedStatement().executeQuery();

            int custID;
            String custName;
            String address;
            String postCode;
            String phone;
            Timestamp createDate;
            String createdBy;
            Timestamp lastUpdate;
            String lastUpdatedBy;
            int divID;

            String country = "";
            String division = "";
            int countID = -1;

            while (result.next())
            {
                custID = result.getInt("Customer_ID");
                custName = result.getString("Customer_Name");
                address = result.getString("Address");
                postCode = result.getString("Postal_Code");
                phone = result.getString("Phone");
                createDate = result.getTimestamp("Create_Date");
                createdBy = result.getString("Created_By");
                lastUpdate = result.getTimestamp("Last_Update");
                lastUpdatedBy = result.getString("Last_Updated_By");
                divID = result.getInt("Division_ID");

                division = MainformController.divisions.get(divID).getDivision();
                countID = MainformController.divisions.get(divID).getCountryID();
                country = MainformController.countries.get(countID).getCountry();


                customerTable.getItems().add(new Customer(custID, custName, address, postCode, phone, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, divID, country, division));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Changes the scene to accept data from the user for adding a new customer.
     *
     * @param event NOT USED.
     */
    @FXML
    public void addButtonAction(ActionEvent event)
    {
        addButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        deleteConfirmLabel.setVisible(false);
        addUpdateLabel.setText("Add :");
        addUpdateLabel.setVisible(true);
        customerIDLabel.setVisible(true);
        customerIDTextfield.setText("");
        customerIDTextfield.setVisible(true);
        customerNameLabel.setVisible(true);
        customerNameTextfield.setText("");
        customerNameTextfield.setVisible(true);
        countryCombo.setVisible(true);
        addressLabel.setVisible(true);
        addressTextfield.setText("");
        addressTextfield.setVisible(true);
        postalCodeLabel.setVisible(true);
        postalCodeTextfield.setText("");
        postalCodeTextfield.setVisible(true);
        phoneLabel.setVisible(true);
        phoneTextfield.setText("");
        phoneTextfield.setVisible(true);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        errorLabel.setVisible(false);

        customerIDTextfield.setText(getNewCustID().toString());

        ObservableList<String> countries = FXCollections.observableArrayList();

        for(Country count : MainformController.countries.values())
        {
            countries.add(count.getCountry());
        }

        countryCombo.setItems(countries);
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
            errorLabel.setText("Something went terribly wrong! Exit program now, please!");
            errorLabel.setVisible(true);
        }
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
     * Sets the First-Level Division combo box data based on which country is selected.
     *
     * @param event NOT USED.
     */
    @FXML
    public void countryComboAction(ActionEvent event)
    {
        int countryID = Integer.MIN_VALUE;
        for(Country count : MainformController.countries.values())
        {
            if(countryCombo.getSelectionModel() == null)
            {
                return;
            }

            if(countryCombo.getSelectionModel().getSelectedItem() == null)
            {
                return;
            }

            if(countryCombo.getSelectionModel().getSelectedItem().equals(count.getCountry()))
            {
                countryID = count.getCountryID();
                break;
            }
        }

        ObservableList<String> divisions = FXCollections.observableArrayList();

        for(Division division : MainformController.divisions.values())
        {
            if(division.getCountryID() == countryID)
            {
                divisions.add(division.getDivision());
            }
        }

        regionCombo.setItems(divisions);
        regionCombo.setVisible(true);
    }


    /**
     * Deletes the currently selected customer.
     * Alerts the user if no customer is selected or customer was not deleted.
     *
     * @param event NOT USED.
     */
    @FXML
    public void deleteButtonAction(ActionEvent event)
    {
        if(customerTable.getSelectionModel().isEmpty())
        {
            deleteConfirmLabel.setText("Select Customer to delete.");
            return;
        }

        String custIDToDelete = Integer.toString(customerTable.getSelectionModel().getSelectedItem().getCustomerID());

        try
        {
            DBQuery.setPreparedStatement(DBConnection.getConnection(), "SELECT Appointment_ID FROM appointments WHERE Customer_ID=" + custIDToDelete);
            ResultSet result = DBQuery.getPreparedStatement().executeQuery();

            if(result.next())
            {
                deleteConfirmLabel.setText("Customer with appointments cannot be deleted.\nDelete all Customer's appointments first.");
                deleteConfirmLabel.setVisible(true);
                return;
            }


            DBQuery.setPreparedStatement(DBConnection.getConnection(), "DELETE FROM customers WHERE Customer_ID=" + custIDToDelete);
            DBQuery.getPreparedStatement().execute();

            if(DBQuery.getPreparedStatement().getUpdateCount() > 0)
            {
                customerTable.getItems().remove(customerTable.getSelectionModel().getSelectedItem());

                deleteConfirmLabel.setText("Customer was deleted.");
                deleteConfirmLabel.setVisible(true);
            }
            else
            {
                deleteConfirmLabel.setText("Customer was not deleted...");
                deleteConfirmLabel.setVisible(true);
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
            errorLabel.setText("Something went terribly wrong! Exit program now, please!");
            errorLabel.setVisible(true);
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
        if(customerNameTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter a Customer Name.");
            errorLabel.setVisible(true);
            return;
        }

        if(countryCombo.getSelectionModel() == null)
        {
            errorLabel.setText("Select a Country");
            errorLabel.setVisible(true);
            return;
        }

        if(countryCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select a Country");
            errorLabel.setVisible(true);
            return;
        }

        if(regionCombo.getSelectionModel() == null)
        {
            errorLabel.setText("Select a Region");
            errorLabel.setVisible(true);
            return;
        }

        if(regionCombo.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Select a Region");
            errorLabel.setVisible(true);
            return;
        }

        if(addressTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter an address.");
            errorLabel.setVisible(true);
            return;
        }

        if(postalCodeTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter a postal code.");
            errorLabel.setVisible(true);
            return;
        }

        if(phoneTextfield.getText().isBlank())
        {
            errorLabel.setText("Enter a phone number.");
            errorLabel.setVisible(true);
            return;
        }


        if(addUpdateLabel.getText().equals("Add :"))
        {
            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "INSERT INTO customers(Customer_ID, Customer_Name, " +
                        "Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?)");

                PreparedStatement stmt = DBQuery.getPreparedStatement();

                int customerID = Integer.valueOf(customerIDTextfield.getText());
                String customerName = customerNameTextfield.getText();
                String address = addressTextfield.getText();
                String postalCode = postalCodeTextfield.getText();
                String phone = phoneTextfield.getText();
                Timestamp createDate = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String createdBy = "test";
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String lastUpdatedBy = "test";

                int divisionID = Integer.MIN_VALUE;

                for(Division division : MainformController.divisions.values())
                {
                    if(division.getDivision().equals(regionCombo.getSelectionModel().getSelectedItem()))
                    {
                        divisionID = division.getDivisionID();
                        break;
                    }
                }

                stmt.setInt(1, customerID);
                stmt.setString(2, customerName);
                stmt.setString(3, address);
                stmt.setString(4, postalCode);
                stmt.setString(5, phone);
                stmt.setTimestamp(6, createDate);
                stmt.setString(7, createdBy);
                stmt.setTimestamp(8, lastUpdate);
                stmt.setString(9, lastUpdatedBy);
                stmt.setInt(10, divisionID);

                DBQuery.getPreparedStatement().execute();

                if(stmt.getUpdateCount() > 0)
                {
                    Customer cust = new Customer(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID,
                            countryCombo.getSelectionModel().getSelectedItem(), regionCombo.getSelectionModel().getSelectedItem());
                    customerTable.getItems().add(cust);

                    selectionMode();
                    deleteConfirmLabel.setText("Customer added.");
                    deleteConfirmLabel.setVisible(true);
                }
                else
                {
                    selectionMode();
                    deleteConfirmLabel.setText("Customer not added...");
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
            try
            {
                DBQuery.setPreparedStatement(DBConnection.getConnection(), "UPDATE customers SET Customer_Name = ?," +
                        "Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                        "WHERE Customer_ID = ?");

                PreparedStatement stmt = DBQuery.getPreparedStatement();

                Customer current = null;

                int customerID = Integer.valueOf(customerIDTextfield.getText());

                for (Customer cust : customerTable.getItems())
                {
                    if(customerID == cust.getCustomerID())
                    {
                        current = cust;
                        break;
                    }
                }

                String customerName = customerNameTextfield.getText();
                String address = addressTextfield.getText();
                String postalCode = postalCodeTextfield.getText();
                String phone = phoneTextfield.getText();
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String lastUpdatedBy = "test";

                int divisionID = Integer.MIN_VALUE;

                for(Division division : MainformController.divisions.values())
                {
                    if(division.getDivision().equals(regionCombo.getSelectionModel().getSelectedItem()))
                    {
                        divisionID = division.getDivisionID();
                        break;
                    }
                }

                stmt.setString(1, customerName);
                stmt.setString(2, address);
                stmt.setString(3, postalCode);
                stmt.setString(4, phone);
                stmt.setTimestamp(5, lastUpdate);
                stmt.setString(6, lastUpdatedBy);
                stmt.setInt(7, divisionID);
                stmt.setInt(8, customerID);

                DBQuery.getPreparedStatement().execute();

                if(stmt.getUpdateCount() > 0)
                {
                    current.setCustomerName(customerName);
                    current.setAddress(address);
                    current.setPostalCode(postalCode);
                    current.setPhone(phone);
                    current.setLastUpdate(lastUpdate);
                    current.setLastUpdatedBy(lastUpdatedBy);
                    current.setDivisionID(divisionID);
                    current.setDivision(MainformController.divisions.get(current.getDivisionID()).getDivision());

                    for(Country count : MainformController.countries.values())
                    {
                        if(count.getCountryID() == MainformController.divisions.get(current.getDivisionID()).getCountryID())
                        {
                            current.setCountry(count.getCountry());
                        }
                    }

                    selectionMode();
                    deleteConfirmLabel.setText("Customer updated.");
                    deleteConfirmLabel.setVisible(true);
                }
                else
                {
                    selectionMode();
                    deleteConfirmLabel.setText("Customer not updated...");
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
     * Changes the scene by showing the user data from the currently selected customer that can be changed by the user.
     *
     * @param event NOT USED.
     */
    @FXML
    public void updateButtonAction(ActionEvent event)
    {
        if(customerTable.getSelectionModel().isEmpty())
        {
            deleteConfirmLabel.setText("Select Customer to update.");
            deleteConfirmLabel.setVisible(true);
            return;
        }

        Customer custToUpdate = customerTable.getSelectionModel().getSelectedItem();

        addButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        deleteConfirmLabel.setVisible(false);
        addUpdateLabel.setText("Update :");
        addUpdateLabel.setVisible(true);
        customerIDLabel.setVisible(true);
        customerIDTextfield.setText("");
        customerIDTextfield.setVisible(true);
        customerNameLabel.setVisible(true);
        customerNameTextfield.setText("");
        customerNameTextfield.setVisible(true);
        countryCombo.setVisible(true);
        regionCombo.setVisible(true);
        addressLabel.setVisible(true);
        addressTextfield.setText("");
        addressTextfield.setVisible(true);
        postalCodeLabel.setVisible(true);
        postalCodeTextfield.setText("");
        postalCodeTextfield.setVisible(true);
        phoneLabel.setVisible(true);
        phoneTextfield.setText("");
        phoneTextfield.setVisible(true);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        errorLabel.setVisible(false);

        ObservableList<String> countries = FXCollections.observableArrayList();

        for(Country count : MainformController.countries.values())
        {
            countries.add(count.getCountry());
        }

        countryCombo.setItems(countries);

        customerIDTextfield.setText(Integer.toString(custToUpdate.getCustomerID()));

        customerNameTextfield.setText(custToUpdate.getCustomerName());

        countryCombo.setValue(custToUpdate.getCountry());

        regionCombo.setValue(custToUpdate.getDivision());

        addressTextfield.setText(custToUpdate.getAddress());

        postalCodeTextfield.setText(custToUpdate.getPostalCode());

        phoneTextfield.setText(custToUpdate.getPhone());
    }

    /**
     * Puts the scene in default selection mode where the user can add a customer
     * or select a customer from the table to update or delete.
     */
    public void selectionMode()
    {
        addButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        deleteConfirmLabel.setVisible(false);
        addUpdateLabel.setVisible(false);
        customerIDLabel.setVisible(false);
        customerIDTextfield.setText("");
        customerIDTextfield.setVisible(false);
        customerNameLabel.setVisible(false);
        customerNameTextfield.setText("");
        customerNameTextfield.setVisible(false);
        countryCombo.valueProperty().set(null);
        countryCombo.setVisible(false);
        regionCombo.valueProperty().set(null);
        regionCombo.setVisible(false);
        addressLabel.setVisible(false);
        addressTextfield.setText("");
        addressTextfield.setVisible(false);
        postalCodeLabel.setVisible(false);
        postalCodeTextfield.setText("");
        postalCodeTextfield.setVisible(false);
        phoneLabel.setVisible(false);
        phoneTextfield.setText("");
        phoneTextfield.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        errorLabel.setVisible(false);
        customerTable.refresh();
    }

    /**
     * Generates a new Customer ID
     *
     * @return the latest Customer ID
     */
    public Integer getNewCustID()
    {
        int biggest = -1;
        for(Customer cust : customerTable.getItems())
        {
            if(cust.getCustomerID() > biggest)
            {
                biggest = cust.getCustomerID();
            }
        }
        return biggest + 1;
    }
}

