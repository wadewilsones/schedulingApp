package main.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class CustomersController {

    public void SwitchTableToAppointments(MouseEvent mouseEvent) throws IOException {

        /**
         * Transfer user to Appointment table view
         */

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/appointments.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard - Customers");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
