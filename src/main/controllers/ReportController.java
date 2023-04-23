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

    @FXML
    public Text notificationHolder;
    /**
     * Initialize Data
     */

    /**
     * FXML data holders
     */

    @FXML
    private TableView<Appointments> AppointmentView;
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
            }
            /**
             * Get number of different Types
             */
            initialized = true;

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

                /**
                 * Initialize Tables with contact schedule
                 */

            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

        }

        monthsNumber.setText(String.valueOf(appMonth));
        typesNumber.setText(String.valueOf(appTypes));



    }

    /**
     *
     * Transfer to Appointment View
     */
    public void SwitchTableToAppointments(MouseEvent mouseEvent)throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/addCustomerForm.fxml"));
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

        System.out.println(contactsInfo.getSelectionModel().getSelectedItem());
        //will contain data
        ObservableList<Appointments> selectedAppointments = FXCollections.observableArrayList();
            for(int i = 0; i < DataPool.getAllAppointments().size(); i++ ){
                if(DataPool.getAllAppointments().get(i).getContact_ID() == contactsInfo.getSelectionModel().getSelectedIndex()+1){
                    selectedAppointments.add(DataPool.getAllAppointments().get(i));
                }
            }

        /**
         * Set up columns
         */

    }
}
