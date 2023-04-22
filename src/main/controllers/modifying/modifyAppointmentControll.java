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
import main.models.DataPool;
import main.utils.TimeHandling;
import main.utils.Validation;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
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
                        contact.getSelectionModel().select(contactIDdB-1);
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
        ErrorHolder.setText("");
        try {
            //Validate input
            Validation newValidation = new Validation(title.getText(), description.getText(), location.getText(), type.getText(), startDate.getValue(), startTime.getText(), endDate.getValue(), endTime.getText(), customerID.getText());

            if (newValidation.getValidationValue()) {

                /**Translate Name to Id*/
                ResultSet allContactsData = DatabaseRequests.getAllContacts();
                int convertedContact = 1;
                System.out.println(contact.getValue());
                while(allContactsData.next()){
                    if(contact.getValue().equals(allContactsData.getString("Contact_Name"))){
                        convertedContact = allContactsData.getInt("Contact_ID");
                        System.out.println("Selected name " + contact.getValue() + " current id" + convertedContact);
                    }
                }

                /**Date to LocalDateTime*/
                LocalDateTime convertedStartDate = TimeHandling.convertDateToLocalDateTime(startDate.getValue(), startTime.getText());
                LocalDateTime convertedEndDate = TimeHandling.convertDateToLocalDateTime(endDate.getValue(), endTime.getText());
                /**Get today's date for last updated date column*/
                LocalDateTime today = LocalDateTime.now();
                /**Update  List*/

                selectedApp.setTitle(title.getText());
                selectedApp.setLocation(location.getText());
                selectedApp.setDescription(description.getText());
                selectedApp.setType(type.getText());
                selectedApp.setStartDate(convertedStartDate);
                selectedApp.setEndDate(convertedEndDate);
                selectedApp.set_lastUpdate(today);
                selectedApp.set_last_Update_By(DatabaseRequests.getUsername());
                selectedApp.setCustomer_ID(Integer.valueOf(customerID.getText()));
                selectedApp.setContact_ID(convertedContact);

                //Appointments newApp = new Appointments(selectedApp.getAppointmentId(), title.getText(), description.getText(), location.getText(), type.getText(),convertedStartDate, convertedEndDate, selectedApp.get_created_Date(), selectedApp.get_created_By(), today, DatabaseRequests.getUsername(), Integer.valueOf(customerID.getText()), DatabaseRequests.getUserID(),convertedContact );

                DataPool.updateAppointment(DataPool.getAllAppointments().indexOf(selectedApp), selectedApp);

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
            ErrorHolder.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
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
