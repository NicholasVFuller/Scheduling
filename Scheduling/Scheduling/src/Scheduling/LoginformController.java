package Scheduling;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller of the Log-in Form.
 */
public class LoginformController implements Initializable
{

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label userIDLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userIDField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label userLocationLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;


    private String userIDBlank;
    private String passwordBlank;
    private String terriblyWrong;
    private String invalid;

    /**
     * Initializes this controller when changing views. Shows the users their region.
     * Form is displayed in either english or french depending on the users computer settings.
     *
     * @param url NOT USED.
     * @param resourceBundle NOT USED.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        errorLabel.setVisible(false);

        locationLabel.setText(ZoneId.systemDefault().getId());

        if(Locale.getDefault().getLanguage().equals("fr"))
        {
            welcomeLabel.setText("Bienvenue à Scheduling! Veuillez entrer votre identifiant et votre mot de passe ci-dessous pour vous connecter!");
            userIDLabel.setText("Identifiant d'utilisateur:");
            passwordLabel.setText("Mot de passe:");
            userLocationLabel.setText("Emplacement de l'utilisateur:");
            loginButton.setText("Connexion");
            exitButton.setText("Sortir");

            userIDBlank = "Veuillez saisir un identifiant.";
            passwordBlank = "Veuillez entrer un mot de passe.";
            terriblyWrong = "Quelque chose a terriblement mal tourné ! Quittez le programme maintenant, s'il vous plaît!";
            invalid = "ID utilisateur ou mot de passe invalide. Veuillez réessayer.";
        }
        else
        {
            userIDBlank = "Please enter a User ID.";
            passwordBlank = "Please enter a Password.";
            terriblyWrong = "Something went terribly wrong! Exit program now, please!";
            invalid = "Invalid User ID or Password. Please try again.";
        }
    }


    /**
     * Exits the application.
     *
     * @param event NOT USED.
     */
    @FXML
    public void exitButtionAction(ActionEvent event)
    {
        System.exit(0);
    }

    /**
     * Checks that the user name and password are correct.
     * If not, alerts the user that the credentials are incorrect.
     * Logs successful and unsuccessful login attempts in a file named "login_activity.txt" stored in the root directory of the program.
     *
     * @param event NOT USED.
     */
    @FXML
    public void loginButtonAction(ActionEvent event) throws FileNotFoundException
    {
        if(userIDField.getCharacters().toString().isBlank())
        {
            errorLabel.setText(userIDBlank);
            errorLabel.setVisible(true);
            return;
        }

        if(passwordField.getCharacters().toString().isBlank())
        {
            errorLabel.setText(passwordBlank);
            errorLabel.setVisible(true);
            return;
        }

        File activity = new File("login_activity.txt");

        PrintWriter print = new PrintWriter(new FileOutputStream(activity, true));

        if(userIDField.getCharacters().toString().equals("test") && passwordField.getCharacters().toString().equals("test"))
        {
            print.println(LocalDate.now().toString() + " " + LocalTime.now() + " : UserID = " + userIDField.getCharacters().toString() + " : Password = "
                    + passwordField.getCharacters().toString() + " : SuccessfulLogin = true");
            print.close();
            try
            {
                Scheduling.changeScene("mainform.fxml");
            }
            catch (IOException e)
            {
                e.printStackTrace();
                errorLabel.setText(terriblyWrong);
                errorLabel.setVisible(true);
            }
        }
        else
        {
            print.println(LocalDate.now().toString() + " " + LocalTime.now() + " : UserID = " + userIDField.getCharacters().toString() + " : Password = "
                    + passwordField.getCharacters().toString() + " : SuccessfulLogin = false");
            print.close();
            errorLabel.setText(invalid);
            errorLabel.setVisible(true);
        }
    }

}

