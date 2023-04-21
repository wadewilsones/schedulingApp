package main.controllers.modifying;

import dbhelper.DatabaseRequests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.models.Customers;
import main.models.DataPool;
import main.utils.Validation;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static main.controllers.CustomersController.GeneratedCustomerId;
import static main.controllers.CustomersController.selectedCustomer;


public class modifyCustomerController implements Initializable {

    /**
     * Set up Members
     */

    @FXML
    public TextField generated_id;
    @FXML
    public TextField customer_Name;
    @FXML
    public ComboBox country;
    @FXML
    public ComboBox division;
    @FXML
    public TextField street;
    @FXML
    public TextField postalCode;
    @FXML
    public TextField phoneNumber;
    @FXML
    public Text ErrorHolder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Set up initial values from selected customer
         */
        customer_Name.setText(selectedCustomer.getCustomer_Name());
        street.setText(selectedCustomer.getAddress());
        postalCode.setText(selectedCustomer.getPostal_code());
        phoneNumber.setText(selectedCustomer.getPhone());
        //Get ID
        generated_id.setPromptText("Auto-Generated: "+ selectedCustomer.getCustomer_ID());
        //Set up Division and Country
        try{
            /**
             * Intitial select division name that correlates with selected customer division
             */
            ResultSet divisions = DatabaseRequests.getDivision();
            int selectedCountryId = 0;
            while(divisions.next()){
                division.getItems().add(divisions.getString("Division"));

                if(selectedCustomer.getDivision_ID() == divisions.getInt("Division_ID")){
                    division.getSelectionModel().select(divisions.getString("Division"));
                    System.out.println("Country ID from division loop" + selectedCountryId);
                    selectedCountryId = divisions.getInt("Country_ID");


                }
            }
            //Country set
            ResultSet countries = DatabaseRequests.getCountries();

            while(countries.next()){
                country.getItems().add(countries.getString("Country")); // add all
                if(countries.getInt("Country_ID") == selectedCountryId){
                    country.getSelectionModel().select(countries.getString("Country"));
                }

            }


        }
        catch(Exception e){
            ErrorHolder.setText("Can't retrieve Country/Division Data: " + e.getMessage());
        }

    }

    /**
     * Filter divison data
     */
    public void filterDivision(ActionEvent actionEvent) throws Exception{

        Object selectedCountry = country.getSelectionModel().getSelectedItem().toString();

        ResultSet divisions = null;

        division.getItems().clear(); // reset all comboBox values

        if(selectedCountry.equals("U.S")){
            divisions = DatabaseRequests.getFilteredDivision("U.S");
        }
        else if(selectedCountry.equals("UK")){
            divisions = DatabaseRequests.getFilteredDivision("UK");
        }
        else if(selectedCountry.equals("Canada")){
            divisions = DatabaseRequests.getFilteredDivision("Canada");
        }

        while(divisions.next()){
            division.getItems().add(divisions.getString("Division"));
        }

    }

    /**
     * Handle  customer update
     */

    public void updateCustomerBtn(MouseEvent mouseEvent) throws Exception {

        ErrorHolder.setText("");
        try{
            /**Validate*/

            Validation customerValidation = new Validation(customer_Name.getText(), street.getText(), postalCode.getText(), phoneNumber.getText());
            if(customerValidation.getValidationValue()){
                /**Set up additional data for customer (dates) and comboBox*/
                LocalDateTime today = LocalDateTime.now();
                /**
                 * Get divisionID by comparing selected choice and other
                 */
                ResultSet allDivisions = DatabaseRequests.getDivision();
                int divisionData = 1;
                while(allDivisions.next()){
                    if(division.getSelectionModel().getSelectedItem().equals(allDivisions.getString("Division"))){
                        divisionData = allDivisions.getInt("Division_ID");
                    }
                }

                /**Update selected customer values Database*/
                selectedCustomer.setCustomer_Name(customer_Name.getText());
                selectedCustomer.setAddress(street.getText());
                selectedCustomer.setPostal_code(postalCode.getText());
                selectedCustomer.setPhone(phoneNumber.getText());
                selectedCustomer.setLast_Update(today);
                selectedCustomer.setLast_Update_By(DatabaseRequests.getUsername());
                selectedCustomer.setDivision_ID(divisionData);
                DataPool.updateCustomer(DataPool.getAllCustomers().indexOf(selectedCustomer), selectedCustomer);
                /**
                 * Transfer control Back to Customer Table View
                 */
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/customers.fxml"));
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setTitle("Dashboard - Customers");
                Parent root = (Parent) fxmlLoader.load();
                stage.setScene(new Scene(root));
                stage.show();
            }
            else{
                ErrorHolder.setText(customerValidation.getErrorMessageValue());
            }
    }
        catch (Exception e){
            System.out.println(e.getMessage());
        ErrorHolder.setText("Can't update customer");
        }
    }


    /**
     * Handle canceling customer modifications
     */
    public void cancelAddingCustomer(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/customers.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard - Customers");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
