package main.controllers;

import dbhelper.DatabaseRequests;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsControll implements Initializable {


    /**
     * FXML data holders
     */

    @FXML
    private TableView AppointmentView;
     @FXML
     private TableColumn AppId;
    @FXML
    private TableColumn Title;
    @FXML
    private TableColumn Description;
    @FXML
    private TableColumn Location;
    @FXML
    private TableColumn Contact;
    @FXML
    private TableColumn Type;
    @FXML
    private TableColumn StartDate_Time;
    @FXML
    private TableColumn EndDate_Time;
    @FXML
    private TableColumn Cust_ID;
    @FXML
    private TableColumn User_ID;


    /**
     * Populate Tables
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
       // AppointmentView.setItems(DatabaseRequests.getAllParts()); // here we get data from App table;

    }
}
