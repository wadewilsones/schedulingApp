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
import main.models.DataPool;
import main.utils.TimeHandling;
import main.utils.Validation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This class will handle adding appointment form
 */
public class AddingAppointment implements Initializable {


    public void CancelAddingAppoitment(MouseEvent mouseEvent) throws IOException {
        /**
         * Transfer user to Appointment table view
         */

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/appointments.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
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
    public TextField title;
    @FXML
    public TextField description;
    @FXML
    public TextField location;
    @FXML
    public ComboBox contact;
    @FXML
    public TextField type;

    /**
     * Dates and Time
     */
    @FXML
    public DatePicker startDate;
    @FXML
    public TextField startTime;
    @FXML
    public DatePicker endDate;
    @FXML
    public TextField endTime;

    /**
     * IDs
     */
    @FXML
    public TextField customerID;
    public TextField userID;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generated_id.setText(String.valueOf(AppointmentsControll.getAppointmentIdGenerator()));
        userID.setText(String.valueOf(DatabaseRequests.getUserID())); // set up userID

    }

    public void addNewAppointmentToList(MouseEvent mouseEvent) {
        ErrorHolder.setText("");
        try {
            //Validate input
            Validation newValidation = new Validation(title.getText(), description.getText(), location.getText(), type.getText(), startDate.getValue(), startTime.getText(), endDate.getValue(), endTime.getText(), customerID.getText());

            if (newValidation.getValidationValue()) {

                /**Converting input to right type*/
                int id = AppointmentsControll.getAppointmentIdGenerator();

                /**Contact */
                int convertedContact = 1;
                /**Date to LocalDateTime*/
                LocalDateTime convertedStartDate = TimeHandling.convertDateToLocalDateTime(startDate.getValue(), startTime.getText());
                LocalDateTime convertedEndDate = TimeHandling.convertDateToLocalDateTime(endDate.getValue(), endTime.getText());
                /**Get today's date for created date column*/
                LocalDateTime today = LocalDateTime.now();
                /**Create a new Apointment and add to List*/
                Appointments newApp = new Appointments(id, title.getText(), description.getText(), location.getText(), type.getText(),convertedStartDate, convertedEndDate, today, DatabaseRequests.getUsername(), today, DatabaseRequests.getUsername(), Integer.valueOf(customerID.getText()), DatabaseRequests.getUserID(),convertedContact );
                DataPool.addAppointmentToTheList(newApp);

                /**Transfer control to Dashboard*/
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/appointments.fxml"));
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setTitle("Dashboard - Appointment");
                Parent root = (Parent) fxmlLoader.load();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                ErrorHolder.setText(Validation.getErrorMessageValue());
            }
        } catch (Exception e) {
            ErrorHolder.setText("Can't add a new appointment!");
            System.out.println(e.getMessage());
        }
    }
}


