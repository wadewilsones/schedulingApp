package main.controllers;

import dbhelper.Database;
import dbhelper.DatabaseRequests;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Controlling Login View
 */
public class LoginController {

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Text errorHolder;

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
                    errorHolder.setText("User with these credentials is not found!");
                }
            }
            catch(Exception e){
                errorHolder.setText("Something went wrong!");
            }

        }else{
            errorHolder.setText("Username and password can't be empty!");
        }
    }
}
