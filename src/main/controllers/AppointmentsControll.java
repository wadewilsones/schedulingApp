package main.controllers;

import dbhelper.DatabaseRequests;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.models.Appointments;
import main.models.DataPool;
import main.utils.TimeHandling;
import main.utils.Validation;
import org.xml.sax.ErrorHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
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
     * UI Information
     */
    @FXML
    public Text errorHolder;

    @FXML
    public Text notificationHolder;

    /**
     * To display appointments counts
     */
    @FXML
    public Text TotalNumberAppoHolder;

    @FXML
    public Text TotalNumberAppoHolderByType;

    /**
     * Radio Buttons
     */

    static public Appointments selectedApp;
    @FXML
    static ToggleGroup filerToggleGroup;
    @FXML
    static RadioButton month;
    @FXML
    static RadioButton week;
    /**
     * Hold current ID value
     */
    static private int appointmentIdGenerator;
    static public void setAppointmentIdGenerator(int id) {
        appointmentIdGenerator = id;
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
                LocalDateTime start = results.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = results.getTimestamp("End").toLocalDateTime();
                LocalDateTime createdDate = results.getTimestamp("Create_Date").toLocalDateTime();

                String crBy = results.getString("Created_By");
                Timestamp l_upd = results.getTimestamp("Last_Update");
                LocalDateTime convertedLastUpdateDate = LocalDateTime.ofInstant(l_upd.toInstant(), ZoneId.systemDefault());
                String l_by_upd = results.getString("Last_Updated_By");

                int cusId = results.getInt("Customer_ID");
                int uId = results.getInt("User_ID");
                int contId = results.getInt("Contact_ID");

                /** Create new Appointment*/
                Appointments newAppointment = new Appointments(appId,title,descr,loc,type,start,end,createdDate,crBy,convertedLastUpdateDate,l_by_upd,cusId,uId,contId);

                /**Check for duplicates and include appointment if no duplicates were detected*/
                DataPool.testingForDuplicates(appId, newAppointment);

                /** Add new Appointment to DataPool class, Appointment list*/
                DataPool.addAppointmentToTheList(newAppointment);

                /**Set ID generator*/
               setAppointmentIdGenerator(appId+1);

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

                    Cust_ID.setCellValueFactory(data -> data.getValue().getSimpleCus_ID().asObject());
                    User_ID.setCellValueFactory(data -> data.getValue().getSimpleUser_ID().asObject());

            }

            /**
             * Display calculation of appointemnts
             */

            int appByMonthCount = 0;
            int todayMonth= LocalDateTime.now().getMonthValue();
            LocalDateTime today = LocalDateTime.now();

            for(int i=0; i< DataPool.getAllAppointments().size(); i++){
                if(todayMonth == DataPool.getAllAppointments().get(i).getStartDate().getMonthValue()){
                    appByMonthCount =  appByMonthCount + 1;
                }
                TotalNumberAppoHolder.setText("Number of appointments this month: " + String.valueOf(appByMonthCount));
                /**
                 * Display upcoming appointments
                 */
                LocalTime now = LocalTime.now(); //current time
                LocalTime deadline = now.plusMinutes(15);
                LocalTime  appointemnt = DataPool.getAllAppointments().get(i).getStartDate().toLocalTime();

                 if(appointemnt.isAfter(now) && appointemnt.isBefore(deadline)){
                     String start = DataPool.getAllAppointments().get(i).getStartDate().getMonthValue() + "/"+DataPool.getAllAppointments().get(i).getStartDate().getDayOfMonth() +"/"+DataPool.getAllAppointments().get(i).getStartDate().getYear();
                     String time = DataPool.getAllAppointments().get(i).getStartDate().getHour() + ":" + DataPool.getAllAppointments().get(i).getStartDate().getMinute();

                     notificationHolder.setText("You have an upcoming appointment:\n" + "Appointment ID: " + DataPool.getAllAppointments().get(i).getAppointmentId() + "\n" +" Date: " + start + "\n" +" Time: " + time);
                 }
                else{
                    notificationHolder.setText("You have no upcoming appointment");
                }

        } }
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
        stage.setTitle("Add New Appointment");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Handle Delete Appointment event
     */
    public void deleteSelectedAppointment(MouseEvent mouseEvent) {

        //Get selected Appointment
        selectedApp = AppointmentView.getSelectionModel().getSelectedItem();
        try{
            DataPool.deleteAppointment(selectedApp);
            notificationHolder.setText( "Appointment with ID " + selectedApp.getAppointmentId()+ " and title: " + selectedApp.getTitle() + " was canceled");
        }
        catch(Exception e){
            errorHolder.setText("Can't delete Appointment");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle appointment update
     */

    public void updateAppointmnet(MouseEvent mouseEvent) throws Exception{

        try{
            // selected app
            selectedApp = AppointmentView.getSelectionModel().getSelectedItem();

            //Display  modify form
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/modifyAppointment.fxml"));
            Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setTitle("Update Appointment");
            Parent root = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            errorHolder.setText("Can't Update Appointment");
        }

    }

    /**
     *
     * Handle Appointment filter view
     */
    /**Month*/
    public void FilterAppointmentsByMonth(MouseEvent mouseEvent) {

        ObservableList<Appointments> filteredByMonth = FXCollections.observableArrayList();

        for(int i = 0; i< DataPool.getAllAppointments().size(); i++){

            LocalDateTime appDate = DataPool.getAllAppointments().get(i).getStartDate();
            LocalDateTime today = LocalDateTime.now();
            //Get date from appointment and compare it with current month
            if( appDate.getMonthValue()== today.getMonthValue() && appDate.getYear() == today.getYear()){
                filteredByMonth.add(DataPool.getAllAppointments().get(i)); //add this to new list
                /**
                 * Set current view to filtered Table
                 */
                AppointmentView.setItems(filteredByMonth);

                AppId.setCellValueFactory(data -> data.getValue().getSimpleApptId().asObject());
                Title.setCellValueFactory(data -> data.getValue().getSimpleTitle());
                Description.setCellValueFactory(data -> data.getValue().getSimpleDescription());

                Location.setCellValueFactory(data -> data.getValue().getSimpleLocation());
                Type.setCellValueFactory(data -> data.getValue().getSimpleType());

                Contact.setCellValueFactory(data -> data.getValue().getSimpleContact_ID().asObject());
                StartDate_Time.setCellValueFactory(data-> data.getValue().getStartDateObject());
                EndDate_Time.setCellValueFactory(data-> data.getValue().getEndDateObject());

                Cust_ID.setCellValueFactory(data -> data.getValue().getSimpleCus_ID().asObject());
                User_ID.setCellValueFactory(data -> data.getValue().getSimpleUser_ID().asObject());
            }

        }
        if(filteredByMonth.size() == 0){
            notificationHolder.setText("No appointments this month");
        }

    }
    /**Week*/
    public void FilterAppointmentsByWeek(MouseEvent mouseEvent) {

        ChronoLocalDateTime today = ChronoLocalDateTime.from(LocalDateTime .now());
        ChronoLocalDateTime currentWeek = ChronoLocalDateTime.from(LocalDateTime.now().plusDays(7));

        ObservableList<Appointments> filteredByWeek = FXCollections.observableArrayList();
        for (int i = 0; i < DataPool.getAllAppointments().size(); i++) {

            LocalDateTime appDate = DataPool.getAllAppointments().get(i).getStartDate();
            //Get date from appointment and compare it with current day
            if (appDate.isAfter(ChronoLocalDateTime.from(today)) && appDate.isBefore(ChronoLocalDateTime.from(currentWeek))) {

                System.out.println("Thus days are in week:" + DataPool.getAllAppointments().get(i).getStartDate());
                filteredByWeek.add(DataPool.getAllAppointments().get(i)); //add this to new list

                AppointmentView.setItems(filteredByWeek);

                AppId.setCellValueFactory(data -> data.getValue().getSimpleApptId().asObject());
                Title.setCellValueFactory(data -> data.getValue().getSimpleTitle());
                Description.setCellValueFactory(data -> data.getValue().getSimpleDescription());

                Location.setCellValueFactory(data -> data.getValue().getSimpleLocation());
                Type.setCellValueFactory(data -> data.getValue().getSimpleType());

                Contact.setCellValueFactory(data -> data.getValue().getSimpleContact_ID().asObject());
                StartDate_Time.setCellValueFactory(data -> data.getValue().getStartDateObject());
                EndDate_Time.setCellValueFactory(data -> data.getValue().getEndDateObject());

                Cust_ID.setCellValueFactory(data -> data.getValue().getSimpleCus_ID().asObject());
                User_ID.setCellValueFactory(data -> data.getValue().getSimpleUser_ID().asObject());
            }


        }
        if(filteredByWeek.size() == 0){
            notificationHolder.setText("No appointments this week");
        }
    }


    /**
     * Remove filter
     */
    public void RemoveFilterAll(MouseEvent mouseEvent) {
        AppointmentView.setItems(DataPool.getAllAppointments());

        /**
         * Using lambda expression
         */

        AppId.setCellValueFactory(data -> data.getValue().getSimpleApptId().asObject());
        Title.setCellValueFactory(data -> data.getValue().getSimpleTitle());
        Description.setCellValueFactory(data -> data.getValue().getSimpleDescription());

        Location.setCellValueFactory(data -> data.getValue().getSimpleLocation());
        Type.setCellValueFactory(data -> data.getValue().getSimpleType());

        Contact.setCellValueFactory(data -> data.getValue().getSimpleContact_ID().asObject());
        StartDate_Time.setCellValueFactory(data-> data.getValue().getStartDateObject());
        EndDate_Time.setCellValueFactory(data-> data.getValue().getEndDateObject());

        Cust_ID.setCellValueFactory(data -> data.getValue().getSimpleCus_ID().asObject());
        User_ID.setCellValueFactory(data -> data.getValue().getSimpleUser_ID().asObject());

    }
}




