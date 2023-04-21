package main.controllers.adding;

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
import main.controllers.CustomersController;
import main.models.Customers;
import main.models.DataPool;
import main.utils.Validation;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static main.controllers.CustomersController.GeneratedCustomerId;


public class AddingCustomer implements Initializable {

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


    /**
     * Initialize Customer ID and populate comboboxes
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Get ID
        generated_id.setPromptText("Auto-Generated: "+ GeneratedCustomerId);
        //Set up Division and Country
        try{
            //Country set
            ResultSet countries = DatabaseRequests.getCountries();
            while(countries.next()){
                country.getItems().add(countries.getString("Country"));
            }

            ResultSet divisions = DatabaseRequests.getDivision();
            while(divisions.next()){
                division.getItems().add(divisions.getString("Division"));
            }
        }
        catch(Exception e){
            ErrorHolder.setText("Can't retrieve Country/Division Data: " + e.getMessage());
        }

    }

    /**
     * Filtering division according to selected country
     */

    public void filterDivision(ActionEvent actionEvent)throws Exception {

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
     * Handle confirmation
     */
    public void addCustomerBtn(MouseEvent mouseEvent) {
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
                /**Add To List and Database*/
                Customers newCustomer = new Customers(GeneratedCustomerId,customer_Name.getText(), street.getText(), postalCode.getText(), phoneNumber.getText(), today, DatabaseRequests.getUsername(), today, DatabaseRequests.getUsername(), divisionData);

                DataPool.addCustomerToTheList(newCustomer);

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
        catch(Exception e){
            ErrorHolder.setText("Can't add a new Customer");
        }
    }

    /**
     * Handle canceling of adding customer
     */
    public void cancelAddingCustomer(MouseEvent mouseEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/customers.fxml"));
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add New Customer");
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
