package main.controllers;

import dbhelper.DatabaseRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.models.Appointments;
import main.models.DataPool;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    /**
     * To display appointments counts
     */
    @FXML
    public Text monthsNumber;
    public static int appMonth = 0;

    private static boolean initialized = false;

    @FXML
    public Text typesNumber;
    public static int appTypes = 0;

    @FXML
    public ComboBox contactsInfo;

    /**
     * For additional report
     */
    @FXML
    public Text customerName;
    @FXML
    public Text customerAppNumber;

    @FXML
    public Text errorHolder;
    @FXML
    public Text notificationHolder;


    /**
     * Initialize Data
     */

    /**
     * FXML data holders
     */

    @FXML
    private TableView<Appointments> contactSchedule;
    @FXML
    private TableColumn<Appointments, Integer> AppId;
    @FXML
    private TableColumn <Appointments,String> Title;
    @FXML
    private TableColumn  <Appointments,String> Description;
    @FXML
    private TableColumn  <Appointments,String> Type;
    @FXML
    private TableColumn  <Appointments,LocalDateTime> StartDate_Time;
    @FXML
    private TableColumn   <Appointments,LocalDateTime> EndDate_Time;
    @FXML
    private TableColumn  <Appointments,Integer> Cust_ID;


    @Override
     public void initialize(URL url, ResourceBundle resourceBundle){

        /**
         * Run once
         */
        if(!initialized){

            for(int i = 0; i < DataPool.getAllAppointments().size(); i++){
                /**
                 * Get all months
                 */
                if(DataPool.getAllAppointments().get(i).getStartDate().getMonthValue() == LocalDateTime.now().getMonthValue()){
                    appMonth = appMonth + 1;
                    monthsNumber.setText(String.valueOf(appMonth));
                }

            /**
             * Get all contacts data
             */
            try{
                ResultSet contacts = DatabaseRequests.getAllContacts();
                    while(contacts.next()){
                        //add to listview
                        contactsInfo.getItems().add(contacts.getString("Contact_Name"));
                        contactsInfo.setPromptText("Select Contact");
                    }

            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
            initialized = true;

        }

        /**
         *
         * 1F Additional Report: Display customer with the most scheduled  appointments
         */

        String customerNameHolder = ""; // for additional report
        int maxAppnumber = 1;
        int customerIdwithMaxtApp = 0;


        ArrayList <Integer> customer_Id_List = new ArrayList<Integer>();
        for(int i = 0; i < DataPool.getAllAppointments().size(); i++){
            customer_Id_List.add(DataPool.getAllAppointments().get(i).getCustomer_ID()); //add customer to list
        }

        for(int i =0; i <  customer_Id_List.size(); i++){
            if(customer_Id_List.get(i) > maxAppnumber){
                customerIdwithMaxtApp = customer_Id_List.get(i) + 1;
                maxAppnumber = maxAppnumber+1;
            }
        }
        if(maxAppnumber > 1){

            try{
                ResultSet customers = DatabaseRequests.loadCustomerData();
                while(customers.next()){
                    if(customerIdwithMaxtApp == customers.getInt("Customer_ID")){
                        customerNameHolder = customers.getString("Customer_Name");
                    }
                }
            }
            catch (Exception e){
                errorHolder.setText("Can't retrieve customer Name");
            }

        }
        else{
            customerAppNumber.setText("No more than 1 appointment scheduled for each customer");
        }

        monthsNumber.setText(String.valueOf(appMonth));
        typesNumber.setText(String.valueOf(appTypes));
        customerName.setText(customerNameHolder + " has max number (" + maxAppnumber + ") appointments.");

    }

    /**
     *
     * Transfer to Appointment View
     */
    public void SwitchTableToAppointments(MouseEvent mouseEvent)throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/appointments.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add New Customer");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     *
     * Transfer to Customer View
     */

    public void SwitchTableToCustomer(MouseEvent mouseEvent)throws Exception {
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
     * Populate table with selected contract schedule
     */

    public void showSchedule(ActionEvent actionEvent) throws Exception{

        notificationHolder.setText("");
        ObservableList<Appointments> selectedAppointments = FXCollections.observableArrayList();
        int selectedContactId = contactsInfo.getSelectionModel().getSelectedIndex() + 1;

            try{
                System.out.println(DataPool.getAllAppointments().size());
                for(int i = 0; i < DataPool.getAllAppointments().size(); i++){

                    Appointments currentTestedApp = DataPool.getAllAppointments().get(i);

                    if(currentTestedApp.getContact_ID() == selectedContactId){
                        System.out.println("Current APP" + currentTestedApp.getContact_ID() + "VS" + selectedContactId);
                        selectedAppointments.add(DataPool.getAllAppointments().get(i));
                     }
                }

                /**
                 * Populate rows with data
                 */

                if(selectedAppointments.size() > 0){
                    contactSchedule.setItems(selectedAppointments);
                    AppId.setCellValueFactory(data -> data.getValue().getSimpleApptId().asObject());
                    Title.setCellValueFactory(data -> data.getValue().getSimpleTitle());
                    Description.setCellValueFactory(data -> data.getValue().getSimpleDescription());

                    Type.setCellValueFactory(data -> data.getValue().getSimpleType());

                    StartDate_Time.setCellValueFactory(data-> data.getValue().getStartDateObject());
                    EndDate_Time.setCellValueFactory(data-> data.getValue().getEndDateObject());

                    Cust_ID.setCellValueFactory(data -> data.getValue().getSimpleCus_ID().asObject());
                }
                else{
                    notificationHolder.setText("No appointments for this contact!");
                }


    }
            catch(Exception e){
                System.out.println(e.getMessage());
            }


                }

    }

