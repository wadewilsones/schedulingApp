package main.controllers.modifying;

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

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static main.controllers.AppointmentsControll.selectedApp;

public class modifyAppointmentControll implements Initializable {

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
     * Initialize with data
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**Get all data from the table*/
        generated_id.setText(String.valueOf(selectedApp.getAppointmentId()));
        title.setText(selectedApp.getTitle());
        description.setText(selectedApp.getDescription());
        location.setText(selectedApp.getLocation());
        /**
         * Fill out all Contacts data
         */
        try{
            ResultSet result = DatabaseRequests.getAllContacts();
            while(result.next()){
                String contactName = result.getString("Contact_Name");
                int contactIDdB = result.getInt("Contact_ID");
                contact.getItems().add(contactName);
                    if(selectedApp.getContact_ID() ==  contactIDdB){
                        contact.setPromptText(contactName);
                    }

            }
        }
        catch(Exception e){
            ErrorHolder.setText("Can't fetch contacts");
        }

        type.setText(selectedApp.getType());
        startDate.setValue(selectedApp.getStartDate().toLocalDate());
        startTime.setText(selectedApp.getStartDate().toLocalTime().toString());
        endDate.setValue(selectedApp.getEndDate().toLocalDate());
        endTime.setText(selectedApp.getEndDate().toLocalTime().toString());
       customerID.setText(String.valueOf(selectedApp.getCustomer_ID()));
       userID.setText(String.valueOf(selectedApp.getUser_ID()));






    }

    /**
     * Handle update Appointment
     */
    public void updateApp(MouseEvent mouseEvent) {
    }



    /**
     * Handle cancel button Click
     */
    public void CancelUpdate(MouseEvent mouseEvent) throws Exception{
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
}
