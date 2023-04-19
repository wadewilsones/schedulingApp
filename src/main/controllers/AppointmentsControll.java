package main.controllers;

import dbhelper.DatabaseRequests;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import main.models.Appointments;
import main.models.DataPool;
import main.utils.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AppointmentsControll implements Initializable {

    /**
     * FXML data holders
     */

    @FXML
    private TableView <Appointments> AppointmentView;
     @FXML
     private TableColumn <Appointments, Integer>  AppId;
    @FXML
    private TableColumn <Appointments,String> Title;
    @FXML
    private TableColumn  <Appointments,String> Description;
    @FXML
    private TableColumn  <Appointments,String> Location;
    @FXML
    private TableColumn  <Appointments,Integer> Contact;
    @FXML
    private TableColumn  <Appointments,String> Type;
    @FXML
    private TableColumn  <Appointments,LocalDateTime> StartDate_Time;
    @FXML
    private TableColumn   <Appointments,LocalDateTime> EndDate_Time;
    @FXML
    private TableColumn  <Appointments,Integer> Cust_ID;
    @FXML
    private TableColumn   <Appointments,Integer> User_ID;

    /**
     * Hold current ID value
     */
    static private int appointmentIdGenerator;
    static public void setAppointmentIdGenerator(int id) {
        appointmentIdGenerator = id+1;
    }
    static public int getAppointmentIdGenerator() {
        return appointmentIdGenerator;
    }

    /**
     * Populate Tables
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try{
            /** Get data from Appointment table in DB*/
            ResultSet results = DatabaseRequests.getAppointements();

            /**Create new appointment object that will be added to DataPool*/
            while(results.next()){

                /**Create variables to hold record's columns values from DB*/

                int appId = results.getInt("Appointment_ID");

                String title = results.getString("Title");
                String descr = results.getString("Description");
                String loc = results.getString("Location");
                String type = results.getString("Type");

                /**Convert Date to LocalDateTime*/
                Date startDate = results.getDate("Start");
                LocalDateTime convertedStartDate = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());

                Date end = results.getDate("End");
                LocalDateTime convertedEndDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

                Date crDate = results.getDate("Create_Date");
                LocalDateTime convertedCreatedDate = LocalDateTime.ofInstant(crDate.toInstant(), ZoneId.systemDefault());

                String crBy = results.getString("Created_By");
                Timestamp l_upd = results.getTimestamp("Last_Update");
                LocalDateTime convertedLastUpdateDate = LocalDateTime.ofInstant(l_upd.toInstant(), ZoneId.systemDefault());
                String l_by_upd = results.getString("Last_Updated_By");

                int cusId = results.getInt("Customer_ID");
                int uId = results.getInt("User_ID");
                int contId = results.getInt("Contact_ID");

                /** Create new Appointment*/
                Appointments newAppointment = new Appointments(appId,title,descr,loc,type,convertedStartDate,convertedEndDate,convertedCreatedDate,crBy,convertedLastUpdateDate,l_by_upd,cusId,uId,contId);

                /**Check for duplicates and include appointment if no duplicates were detected*/
                DataPool.testingForDuplicates(appId);

                /** Add new Appointment to DataPool class, Appointment list*/
                DataPool.addAppointmentToTheList(newAppointment);

                /**Set ID generator*/
                setAppointmentIdGenerator(appId);

                /**Bind Tables column with Appointment list*/
                AppointmentView.setItems(DataPool.getAllAppointments());

                    AppId.setCellValueFactory(data -> data.getValue().getSimpleApptId().asObject());
                    Title.setCellValueFactory(data -> data.getValue().getSimpleTitle());
                    Description.setCellValueFactory(data -> data.getValue().getSimpleDescription());

                    Location.setCellValueFactory(data -> data.getValue().getSimpleLocation());
                    Type.setCellValueFactory(data -> data.getValue().getSimpleType());

                    Contact.setCellValueFactory(data -> data.getValue().getSimpleContact_ID().asObject());
                    StartDate_Time.setCellValueFactory(data-> data.getValue().getStartDateObject());
                    EndDate_Time.setCellValueFactory(data-> data.getValue().getEndDateObject());

                    Cust_ID.setCellValueFactory(data -> data.getValue().getSimpleContact_ID().asObject());
                    User_ID.setCellValueFactory(data -> data.getValue().getSimpleUser_ID().asObject());

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Switch view to Customer Table
     */

    public void SwitchTableToCustomer(MouseEvent mouseEvent) throws IOException {

        /**
         * Transfer user to Customer table view
         */

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/customers.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard - Customers");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Handle Add Appointment event
     */
    public void addNewAppointment(MouseEvent mouseEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/newAppointmentForm.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard - Customers");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}




