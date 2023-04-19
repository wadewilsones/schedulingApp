package dbhelper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
        currentUserID = currentUserID;
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
     *
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
                    System.out.println(results.getInt("User_ID"));
                    setUserId(results.getInt("User_ID")); // set up current user
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

    /**
     * Get data from appointment DB table
     */
    static public ResultSet getAppointements(){
        ResultSet results = null;
        try{
            Database.startConnection();
            PreparedStatement getApptms = Database.connection.prepareStatement("SELECT * FROM appointments;");
            results = getApptms.executeQuery();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return results;
    };

}
