package main.models;

import java.util.Date;

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
    private Date start;
    private Date end;
    private Date created_Date;
    private String created_By;
    private String last_Update;
    private String last_Update_By;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;

    /**
     * Constructor for new Appointments
     */

    public Appointments(int appId,String title,String descr,String loc,String type,Date start,Date end,Date crDate,String crBy,String l_upd,String l_by_upd,int cusId,int uId,int contId){

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
        this.contact_ID = contId;

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
    public String gertType(){
        return this.type;
    }
    public Date getStartDate(){
        return this.start;
    }
    public Date getEndDate(){
        return this.end;
    }
    public Date get_created_Date(){
        return this.created_Date;
    }
    public String get_created_By(){
        return this.created_By;
    }
    public String get_lastUpdate(){
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

    public void setStartDate(Date start) {
        this.start = start;
    }

    public void setEndDate(Date end) {
        this.end = end;
    }

    public void set_created_Date(Date created_Date) {
        this.created_Date = created_Date;
    }

    public void set_created_By(String created_By) {
        this.created_By = created_By;
    }

    public void set_lastUpdate(String last_Update) {
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

    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }


}
