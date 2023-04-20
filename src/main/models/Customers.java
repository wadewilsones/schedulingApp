package main.models;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * This is model for Customers
 */
public class Customers {
    /**
     * Setting up members of the class (matches with Customers table columns)
     */

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_code;
    private String Phone;
    private LocalDateTime Created_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Update_By;
    private int Division_ID;


    /**
     * Constructor
     */

    public Customers(int Customer_ID, String customer_Name, String address, String postal_code, String phone, LocalDateTime created_Date, String created_By, LocalDateTime last_Update, String last_Update_By, int division_ID) {
        Customer_ID = Customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_code = postal_code;
        Phone = phone;
        Created_Date = created_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Update_By = last_Update_By;
        Division_ID = division_ID;
    }


    /**
     * Getters for Customers members
     */

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPostal_code() {
        return Postal_code;
    }

    public String getPhone() {
        return Phone;
    }

    public LocalDateTime getCreated_Date() {
        return Created_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    public String getLast_Update_By() {
        return Last_Update_By;
    }

    public int getDivision_ID() {
        return Division_ID;
    }


    /**
     * Setters for Customers members
     */

    public void setCustomer_ID(int Customer_ID) {
        Customer_ID = Customer_ID;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPostal_code(String postal_code) {
        Postal_code = postal_code;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setCreated_Date(LocalDateTime created_Date) {
        Created_Date = created_Date;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    public void setLast_Update_By(String last_Update_By) {
        Last_Update_By = last_Update_By;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * Transform to Simple property
     */
    public IntegerProperty getSimpleCustomerID(){
        IntegerProperty CustomerID = new SimpleIntegerProperty(this.Customer_ID);
        return CustomerID;
    }

    public StringProperty getSimpleCustomerName(){
        StringProperty name = new SimpleStringProperty(this.Customer_Name);
        return name;
    }

    public StringProperty getSimpleAddress(){
        StringProperty address = new SimpleStringProperty(this.Address);
        return address;
    }

    public StringProperty getPostalCode(){
        StringProperty postal = new SimpleStringProperty(this.Postal_code);
        return postal;
    }

    public StringProperty getSimplePhone(){
        StringProperty phone = new SimpleStringProperty(this.Phone);
        return phone;
    }

    public ObjectProperty<LocalDateTime> getSimplecreateDate(){
        ObjectProperty<LocalDateTime> createDate = new SimpleObjectProperty<LocalDateTime>(this.Created_Date);
        return createDate;
    }

    public StringProperty getSimpleCreatedBy(){
        StringProperty crBy = new SimpleStringProperty(this.Created_By);
        return crBy;
    }

    public ObjectProperty<LocalDateTime> getSimplelastUpdate(){
        ObjectProperty<LocalDateTime> lastUpd = new SimpleObjectProperty<LocalDateTime>(this.Last_Update);
        return lastUpd;
    }

    public StringProperty getSimpleUpdatedBy(){
        StringProperty upBy = new SimpleStringProperty(this.Last_Update_By);
        return upBy;
    }

    public IntegerProperty getSimpleDivId(){
        IntegerProperty divId = new SimpleIntegerProperty(this.Division_ID);
        return divId;
    }
}
