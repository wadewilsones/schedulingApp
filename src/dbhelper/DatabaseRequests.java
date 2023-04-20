package dbhelper;


import main.models.Appointments;
import main.models.DataPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class provides interface for interacting with tables data
 */
 public class DatabaseRequests {

    static private boolean isAuthenticated;

    static private int currentUserID; // this will hold current logged in user
    static private String currentUsername;

    /**
     * Getters and setters for  @param isAuthenticated
     */
    static public void setAuthenticated(boolean isAuthenticated){
        isAuthenticated = isAuthenticated;
    }

    static public boolean getAuthenticated(){
        return isAuthenticated;
    }

    /**
     * Getters and setters for  @param currentUserID
     */
    static public void setUserId(int userID){
        currentUserID = userID;
    }

    static public int getUserID(){
        return currentUserID;
    }

    /**
     * Getters and setters for  @param  username
     */
    static public void setUsername(String username){
        currentUsername = username;
    }

    static public String getUsername(){
        return currentUsername;
    }



    /**
     * This method return true if username and password matched with record in the database.
     */
    static public boolean authenticate(String username, String password){

        /**
         * Connect with Database and query
         */
        try{
            Database.startConnection(); // connect to DB

            PreparedStatement findUserQuery = Database.connection.prepareStatement("SELECT * FROM users WHERE User_Name = ? AND Password = ?;");
            findUserQuery.setString(1,username); // set first query parameter equal to username
            findUserQuery.setString(2,password); // set second query parameter equal to password

            ResultSet results = findUserQuery.executeQuery(); // execute prepared query

            /**
             * Calculate rows
             */
            int size = 0;
            while(results.next()){
                size++;
                if(size == 1){
                    setAuthenticated(true);
                    // set up current user
                    setUserId(results.getInt("User_ID"));
                    setUsername(results.getString("User_Name"));
                    return true;
                }
                else{
                    setAuthenticated(false);
                    return false;
                }
            }


        }
        catch(Exception e ){
            System.out.println(e.getMessage());
        }

        return getAuthenticated(); // return result of authentication true/false
    }


    /**APPOINTMENT SECTION*/

    /**
     * Get data from appointment DB table
     */
    static public ResultSet getAppointements(){
        ResultSet results = null;
        try{
            PreparedStatement getApptms = Database.connection.prepareStatement("SELECT * FROM appointments;");
            results = getApptms.executeQuery();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return results;
    };

    /**
     * Check if CustomerID is correct
     * */
    static public boolean getCustomerIdList(int id){
        boolean isCorrectId = false;
        try{

            PreparedStatement getApptms = Database.connection.prepareStatement("SELECT * FROM Customers WHERE Customer_ID = ?;");
            getApptms.setInt(1, id);
            ResultSet results = getApptms.executeQuery();

            while(results.next()){
                if(id == results.getInt("Customer_ID")){
                    isCorrectId = true;
                }
                else{
                    isCorrectId = false;
                }

            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return isCorrectId;
    }

    /**
     * Add new Appointment data to Database
     */
    static public void addNewAppointemntToDB(Appointments appointment){
        try{
            PreparedStatement createApptms = Database.connection.prepareStatement("INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type,Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            /**Fill out variables with actual data*/
            createApptms.setInt(1, appointment.getAppointmentId()); //id
            createApptms.setString(2, appointment.getTitle()); //title
            createApptms.setString(3, appointment.getDescription()); //Description
            createApptms.setString(4, appointment.getLocation()); //Location
            createApptms.setString(5, appointment.getType()); //Type
            createApptms.setTimestamp(6, Timestamp.valueOf(appointment.getStartDate())); //start date
            createApptms.setTimestamp(7, Timestamp.valueOf(appointment.getEndDate())); //end date
            createApptms.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); //created date
            createApptms.setString(9, currentUsername); // created By
            createApptms.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now())); // last updated
            createApptms.setString(11, currentUsername); // updated By
            createApptms.setInt(12, appointment.getCustomer_ID()); //customer id
            createApptms.setInt(13, currentUserID); // updated By
            createApptms.setInt(14, appointment.getContact_ID()); //contact id

            /**Execute query*/
            createApptms.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fetch all contacts
     */
    static public ResultSet getAllContacts() throws Exception {

        PreparedStatement getContacts = Database.connection.prepareStatement("SELECT * FROM contacts");
        ResultSet results = getContacts.executeQuery();
        return results;
    }

    /**
     * Update appointment
     */

    public static void updateAppointmnet(Appointments appointment) throws Exception{
        System.out.println(appointment.getStartDate());
        PreparedStatement createApptms = Database.connection.prepareStatement(
                "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, Contact_ID = ? WHERE Appointment_Id = ?;");

        /**Fill out variables with actual data*/
        createApptms.setString(1, appointment.getTitle()); //title
        createApptms.setString(2, appointment.getDescription()); //Description
        createApptms.setString(3, appointment.getLocation()); //Location
        createApptms.setString(4, appointment.getType()); //Type
        createApptms.setTimestamp(5, Timestamp.valueOf(appointment.getStartDate())); //start date
        createApptms.setTimestamp(6, Timestamp.valueOf(appointment.getEndDate())); //end date
        createApptms.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // last updated
        createApptms.setString(8, currentUsername); // updated By
        createApptms.setInt(9, appointment.getCustomer_ID()); //customer id
        createApptms.setInt(10, appointment.getContact_ID()); //contact id
        createApptms.setInt(11, appointment.getAppointmentId()); // app id

        /**Execute query*/
        createApptms.executeUpdate();
    }

    /**
     * Delete appointment
     */

    public static void deleteAppointment(Appointments selectedApp) throws Exception {
        PreparedStatement delApp = Database.connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ? ");
        delApp.setInt(1, selectedApp.getAppointmentId());
        delApp.executeUpdate();
    }

    /**CUSTOMER SECTION*/


    /**Return customer data from DB*/
    public static ResultSet loadCustomerData() throws  Exception{
        PreparedStatement getCustomers = Database.connection.prepareStatement("SELECT * FROM customers;");
        ResultSet result  = getCustomers.executeQuery();

        return result;
    }

    /**Load country data*/
    public static ResultSet getCountries() throws  Exception{
        PreparedStatement getCustomers = Database.connection.prepareStatement("SELECT Country, Country_ID FROM countries;");
        ResultSet result  = getCustomers.executeQuery();
        return result;
    }

    /**Load division data*/
    public static ResultSet getDivision() throws  Exception{

        PreparedStatement getCustomers = Database.connection.prepareStatement("SELECT Division, Division_ID FROM first_level_divisions;");
        ResultSet result  = getCustomers.executeQuery();
        return result;
    }

    /**Load division data and filter it*/
    public static ResultSet getFilteredDivision(String Country) throws  Exception{

        ResultSet result = null;

        System.out.println("Got input" + Country);

        PreparedStatement countries = Database.connection.prepareStatement("SELECT Country, Country_ID FROM countries WHERE Country = ?;");
        countries.setString(1, Country);
        ResultSet returnedCountries  = countries.executeQuery();

        while(returnedCountries.next()){
            int targetCountry = returnedCountries.getInt("Country_ID");
            System.out.println("Got input " + Country + ", ID:" + targetCountry);

            PreparedStatement getCustomers = Database.connection.prepareStatement("SELECT Division, Division_ID FROM first_level_divisions WHERE Country_ID = ?;");
            getCustomers.setInt(1,targetCountry);
            result  = getCustomers.executeQuery();

        }
        return result;
    }

}
