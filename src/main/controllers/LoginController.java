package main.controllers;

import dbhelper.Database;
import dbhelper.DatabaseRequests;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import main.utils.TimeHandling;

import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controlling Login View
 */
public class LoginController implements Initializable {


    /**
     * Labels that can be changed to French
     */

    @FXML
    public Text Scheduling_App_Label;
    @FXML
    public Label Username_Label;
    @FXML
    public Label Password_Label;
    @FXML
    public Button Login_Button;


    /**
     * Data holders
     */
    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Text errorHolder;

    @FXML
    public Label timeZone;

    /**
     * Get current user data
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        timeZone.setText("Timezone: " + TimeHandling.getTimeZoneName()); // get timezone name
        /**
         * Display labels in French
         */
        if(TimeHandling.isUserLanguageFrench()){
            timeZone.setText("Fuseau horaire: " + TimeHandling.getTimeZoneName()); // get timezone name
            Scheduling_App_Label.setText("Application De Planification");
            Username_Label.setText("Nom d'utilisateur");
            Password_Label.setText("Mot de passe");
            Login_Button.setText("Connexion");
            usernameField.setPromptText("Tapez le nom d'utilisateur");
            passwordField.setPromptText("Tapez le mot de passe");
        };
    }

    /**
     *
     * Connecting with dataBase and checking credentials
     */

    public void loginUser(MouseEvent mouseEvent) {

        errorHolder.setText("");

        if(!usernameField.getText().equals("") && !passwordField.getText().equals("")){
            /**
             * Try to access DB
             */
            try{
                boolean isAuthenticated = DatabaseRequests.authenticate(usernameField.getText(), passwordField.getText());
                if(!isAuthenticated){
                    if(TimeHandling.isUserLanguageFrench()){
                        errorHolder.setText("L'utilisateur avec ces informations d'identification est introuvable!");
                    }
                    else{
                        errorHolder.setText("User with these credentials is not found!");
                    }

                }
            }
            catch(Exception e){
                if(TimeHandling.isUserLanguageFrench()){
                    errorHolder.setText("Quelque chose s'est mal passé!");
                }
                else{
                    errorHolder.setText("Something went wrong!");
                }

            }

        }
        else{
            if(TimeHandling.isUserLanguageFrench()){
                errorHolder.setText("Aucun champ vide n'est autorisé!");
            }
            else{
                errorHolder.setText("Username and password can't be empty!");
            }

        }
    }


}
