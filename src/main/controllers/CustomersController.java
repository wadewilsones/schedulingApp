package main.controllers;

import dbhelper.DatabaseRequests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.models.Appointments;
import main.models.Customers;
import main.models.DataPool;
import org.xml.sax.ErrorHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    /**
     * FXML data holders
     */

    @FXML
    private TableView<Customers> CustomerView;
    @FXML
    private TableColumn<Customers, Integer> Customer_ID;
    @FXML
    private TableColumn <Customers,String> Customer_Name;
    @FXML
    private TableColumn  <Customers,String> Address;
    @FXML
    private TableColumn  <Customers,String> Postal_Code;
    @FXML
    private TableColumn  <Customers,String> Phone;
    @FXML
    private TableColumn  <Customers,LocalDateTime> Create_Date;
    @FXML
    private TableColumn  <Customers, String> Created_By;
    @FXML
    private TableColumn  <Customers,LocalDateTime> Last_Update;
    @FXML
    private TableColumn  <Customers,String> Last_Updated_By;
    @FXML
    private TableColumn   <Customers,Integer> Division_ID;

    @FXML
    public Text errorHolder;

    @FXML
    public Text notificationHolder;

    static public Customers selectedCustomer;
    static public int GeneratedCustomerId = 0;


    /**
     * Initialize Customer data
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try{
            //Get data from DB
            ResultSet result = DatabaseRequests.loadCustomerData();

            //Set up variables
            while(result.next()){

                int customerId = result.getInt("Customer_ID");
                String custName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postal = result.getString("Postal_code");
                String phone = result.getString("Phone");
                LocalDateTime createdDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

                String updatedBy = result.getString("Last_Updated_By");
                int divID = result.getInt("Division_ID");

                //Create new Customer

                Customers newCustomer = new Customers(customerId,custName,address,postal,phone,createdDate,createdBy,lastUpdate,updatedBy,divID);
                System.out.println("HERE");
                //Check for duplicates
                if(!DataPool.testingForDuplicates(customerId, newCustomer))
                        DataPool.addCustomerToTheList(newCustomer);
                        GeneratedCustomerId = customerId + 1; // increase Customer ID
                }


            //Set up table
            CustomerView.setItems(DataPool.getAllCustomers());

            //set up records
            Customer_ID.setCellValueFactory(data -> data.getValue().getSimpleCustomerID().asObject());
            Customer_Name.setCellValueFactory(data -> data.getValue().getSimpleCustomerName());

            Address.setCellValueFactory(data -> data.getValue().getSimpleAddress());
            Postal_Code.setCellValueFactory(data -> data.getValue().getPostalCode());
            Phone.setCellValueFactory(data -> data.getValue().getSimplePhone());

            Create_Date.setCellValueFactory(data -> data.getValue().getSimplecreateDate());
            Created_By.setCellValueFactory(data-> data.getValue().getSimpleCreatedBy());
            Last_Update.setCellValueFactory(data->data.getValue().getSimplelastUpdate());
            Last_Updated_By.setCellValueFactory(data-> data.getValue().getSimpleUpdatedBy());

            Division_ID.setCellValueFactory(data -> data.getValue().getSimpleDivId().asObject());

        }
        catch(Exception e){
            errorHolder.setText("Can't retrieve Customers records" + e.getMessage());
        }


    }

    /**
     * Transfer user to Appointment table view
     */
    public void SwitchTableToAppointments(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/appointments.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard - Customers");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Transfer user to Adding Customer form table view
     */
    public void HandleAddingCustomerBtnClick(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/addCustomerForm.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add New Customer");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Delete Customer
     */
    public void HandleCustomerDelete(MouseEvent mouseEvent) {

         selectedCustomer = CustomerView.getSelectionModel().getSelectedItem();
            try{
                DataPool.deleteCustomer(selectedCustomer);
                notificationHolder.setText("Customer " + selectedCustomer.getCustomer_Name() +" was deleted.");
            }
                  catch (Exception e){
                      errorHolder.setText("Can't Delete Customer!");
                  }


    }
}
