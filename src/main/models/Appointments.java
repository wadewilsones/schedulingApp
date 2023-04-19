package main.models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * This is model for Appointments
 */
public class Appointments {

    /**
     * Setting up members of the class (matches with Appointment table columns)
     */

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime created_Date;
    private String created_By;
    private LocalDateTime last_Update;
    private String last_Update_By;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;

    /**
     * Constructor for new Appointments
     */

    public Appointments(int appId, String title, String descr, String loc, String type, LocalDateTime start, LocalDateTime end, LocalDateTime crDate, String crBy, LocalDateTime l_upd, String l_by_upd, int cusId, int uId, int contactID){

        this.appointmentId = appId;
        this.title = title;
        this.description = descr;
        this.location = loc;
        this.type = type;
        this.start = start;
        this.end = end;
        this.created_Date = crDate;
        this.created_By = crBy;
        this.last_Update = l_upd;
        this.last_Update_By = l_by_upd;
        this.customer_ID = cusId;
        this.user_ID = uId;
        this.contact_ID = contactID;

    }

    /**
     * Getters for private members
     */

    public int getAppointmentId(){
        return this.appointmentId;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getLocation(){
        return this.location;
    }
    public String getType(){
        return this.type;
    }
    public LocalDateTime getStartDate(){
        return this.start;
    }
    public LocalDateTime getEndDate(){
        return this.end;
    }
    public LocalDateTime get_created_Date(){
        return this.created_Date;
    }
    public String get_created_By(){
        return this.created_By;
    }
    public LocalDateTime get_lastUpdate(){
        return this.last_Update;
    }
    public String get_last_Update_By(){
        return this.last_Update_By;
    }
    public int getCustomer_ID(){
        return this.customer_ID;
    }
    public int getUser_ID(){
        return this.user_ID;
    }
    public int getContact_ID(){
        return this.contact_ID;
    }

    /**
     * Transform private members to fill out tables
     */
    public IntegerProperty getSimpleApptId(){
        IntegerProperty id = new SimpleIntegerProperty(this.appointmentId);
        return id;
    }
    public StringProperty getSimpleTitle(){
        StringProperty title = new SimpleStringProperty(this.title);
        return title;
    }
    public StringProperty getSimpleDescription(){
        StringProperty description = new SimpleStringProperty(this.description);
        return description;
    }
    public StringProperty getSimpleLocation(){
        StringProperty location = new SimpleStringProperty(this.location);
        return location;
    }
    public StringProperty getSimpleType(){
        StringProperty type = new SimpleStringProperty(this.type);
        return type;
    }

    public ObjectProperty<LocalDateTime> getStartDateObject(){
        ObjectProperty<LocalDateTime> startDate = new SimpleObjectProperty<LocalDateTime>(this.start);
        return startDate;
    }

    public ObjectProperty<LocalDateTime> getEndDateObject(){
        ObjectProperty<LocalDateTime> endDate = new SimpleObjectProperty<LocalDateTime>(this.end);
        return endDate;
    }

    public IntegerProperty getSimpleCus_ID(){
        IntegerProperty customer_ID = new SimpleIntegerProperty(this.customer_ID);
        return customer_ID;
    }
    public IntegerProperty getSimpleUser_ID(){
        IntegerProperty user_ID = new SimpleIntegerProperty(this.user_ID);
        return user_ID;
    }

    public IntegerProperty getSimpleContact_ID(){
        IntegerProperty contact = new SimpleIntegerProperty(this.contact_ID);
        return contact;
    }


    /**
     * Setters for private members
     */
    public void setAppointmentId(int appId){
        this.appointmentId = appId;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
       this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(LocalDateTime start) {
        this.start = start;
    }
    public void setEndDate(LocalDateTime end) {
        this.end = end;
    }
    public void set_created_Date(LocalDateTime created_Date) {
        this.created_Date = created_Date;
    }

    public void set_created_By(String created_By) {
        this.created_By = created_By;
    }
    public void set_lastUpdate(LocalDateTime last_Update) {
        this.last_Update = last_Update;
    }
    public void set_last_Update_By(String last_Update_By) {
        this.last_Update_By = last_Update_By;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }
    public void setContact_ID(int contact) {
        this.contact_ID = contact;
    }


}
