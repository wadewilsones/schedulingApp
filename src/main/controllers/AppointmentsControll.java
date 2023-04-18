package main.controllers;

import dbhelper.DatabaseRequests;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import main.models.Appointments;
import main.models.DataPool;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
    private TableColumn  <Appointments,Date> StartDate_Time;
    @FXML
    private TableColumn   <Appointments,Date> EndDate_Time;
    @FXML
    private TableColumn  <Appointments,Integer> Cust_ID;
    @FXML
    private TableColumn   <Appointments,Integer> User_ID;


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
                Date start = results.getDate("Start");
                Date end = results.getDate("End");
                Date crDate = results.getDate("Create_Date");
                String crBy = results.getString("Created_By");
                String l_upd = results.getString("Last_Update");
                String l_by_upd = results.getString("Last_Updated_By");
                int cusId = results.getInt("Customer_ID");
                int uId = results.getInt("User_ID");
                int contId = results.getInt("Contact_ID");

                /**Check for duplicates*/

                /** Create new Appointment*/
                Appointments newAppointment = new Appointments(appId,title,descr,loc,type,start,end,crDate,crBy,l_upd,l_by_upd,cusId,uId,contId);
                /** Add new Appointment to DataPool class, Appointment list*/
                DataPool.addAppointmentToTheList(newAppointment);

                /**Bind Tables column with Appointment list*/
                AppointmentView.setItems(DataPool.getAllAppointments());

                AppId.setCellValueFactory(data -> data.getValue().getSimpleApptId().asObject());
                Title.setCellValueFactory(data -> data.getValue().getSimpleTitle());
                Description.setCellValueFactory(data -> data.getValue().getSimpleDescription());
                Location.setCellValueFactory(data -> data.getValue().getSimpleLocation());
                Type.setCellValueFactory(data -> data.getValue().getSimpleType());
                Contact.setCellValueFactory(data -> data.getValue().getSimpleContact_ID().asObject());
                StartDate_Time.setCellValueFactory(data-> (ObservableValue<Date>) data.getValue().getStartDateObject());
                EndDate_Time.setCellValueFactory(data-> (ObservableValue<Date>) data.getValue().getEndDateObject());
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


}
