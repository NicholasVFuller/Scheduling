package Scheduling;

import DB.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * An application that allows the user to log in then view and make changes to customer data, view and make changes to customer appointments,
 * view upcoming appointments by month and week, and view a variety of reports.
 *
 * @author Nick Fuller
 * @version 1.0
 */
public class Scheduling extends Application
{
    private static Stage stage;

    /**
     * Starts the program by taking users to the log-in screen.
     *
     * @param primaryStage the primary stage used to set scenes by the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../SchedulingFXML/log-inform.fxml"));
        primaryStage.setTitle("Scheduling");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Changes scenes based on fxml file name passed in.
     *
     * @param fxml the full name of the fxml file to load.
     * @throws IOException
     */
    public static void changeScene(String fxml) throws IOException
    {
        Parent root = loadFXML("../SchedulingFXML/" + fxml);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }

    /**
     * Loads the fxml file specified by a String.
     *
     * @param fxml the full name of the fxml file to load.
     * @return the parent node extracted for the FXMLLoader.
     * @throws IOException
     */
    public static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Scheduling.class.getResource(fxml));
        return fxmlLoader.load();
    }

    /**
     * Starts the connection to the database and starts the program.
     *
     * @param args command line args passed into the program.
     */
    public static void main(String[] args)
    {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
