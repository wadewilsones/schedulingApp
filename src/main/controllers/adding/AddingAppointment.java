package main.controllers.adding;

import dbhelper.DatabaseRequests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.controllers.AppointmentsControll;
import main.models.Appointments;
import main.utils.Validation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This class will handle adding appointment form
 */
public class AddingAppointment implements Initializable {



    public void CancelAddingAppoitment(MouseEvent mouseEvent) throws IOException {
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

    /**
     * Fields of Add New Appointment Form
     */
    @FXML
    public TextField generated_id;
    @FXML
    public TextField  title;
    @FXML
    public TextField  description;
    @FXML
    public TextField  location;
    @FXML
    public ComboBox contact;
    @FXML
    public TextField  type;

    /**Dates and Time*/
    @FXML
    public DatePicker startDate;
    @FXML
    public TextField startTime;
    @FXML
    public DatePicker endDate;
    @FXML
    public TextField endTime;

    /**IDs*/
    @FXML
    public TextField customerID;
    public TextField  userID;

    /**
     * Error displayer
     */

    @FXML
    public Text ErrorHolder;

    /**
     * After add appointment button was clicked handle it by adding data to Data pool and DB
     */

    /**
     * Initialize User ID and Appointment ID
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        generated_id.setText(String.valueOf(AppointmentsControll.getAppointmentIdGenerator()));
        userID.setText(String.valueOf(DatabaseRequests.getUserID())); // set up userID

    }

    public void addNewAppointmentToList(MouseEvent mouseEvent) {
        ErrorHolder.setText("");
        System.out.println(title.getText());
        try{
            Validation newValidation = new Validation(title.getText(), description.getText(), location.getText(), type.getText(), startDate.getValue(), startTime.getText(), endDate.getValue(), endTime.getText(), customerID.getText());

            if(newValidation.getValidationValue()){
                System.out.println("Valid!");
            }
        }
        catch(Exception e){
            ErrorHolder.setText("Can't add a new appointment!");
            System.out.println(e.getMessage());
        }

/*
        if(newValidation.getValidationValue()){
            System.out.println("Valid!");
            LocalDateTime today = LocalDateTime.now();
            /**Prepare values for new appointment entry*/

            //Convert dates to LocalDateTime
            //LocalDateTime convertedCreatedDate = LocalDateTime.ofInstant(added_startD.toInstant(), ZoneId.systemDefault());


            int id = AppointmentsControll.getAppointmentIdGenerator();
            //Appointments newApp = new Appointments(id, added_title.getText(), added_description.getText(), added_location.getText(), added_type.getText(), added_startD.getValue(), added_endD.getValue(), today, DatabaseRequests.getUsername(), today.toString(), DatabaseRequests.getUsername(), Integer.valueOf(added_cusID.getText()), DatabaseRequests.getUserID(), added_contact.getValue().toString() );

    }
}

