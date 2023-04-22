package main.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;

/**
 * This class is responsible to record logging information such as login attempts
 */
 public class Logs {

    /**
     * Define all holder
     */
   public static String username;
   public static int attemptNumber = 0;
   public static Timestamp loggingAttemptTime;
   public static boolean isLoginSuccessful ;

    /**
     * Test if logFile exist
     */

    public Logs(String username, Timestamp loginTime, boolean isSuccessful ){
        this.username = username;
        this.attemptNumber =   this.attemptNumber + 1;
        this.loggingAttemptTime = loginTime;
        this.isLoginSuccessful = isSuccessful;
    }

    /**
     * Writing data to Log
     */

    public static void createLog(){

        try{
            File logFile = new File("login_activity.txt");

            if(logFile.createNewFile()){
                System.out.println("NEW FILE EXISTS");
                FileWriter writer = new FileWriter("login_activity.txt");
                writer.write("Logs\n" + "\nAttempt Number: " + attemptNumber + "\n"  + "Username: " + username + "\n" + "Login Time: " + loggingAttemptTime + "\n" + "Is login Successful: " + isLoginSuccessful);
                writer.close();
            }
            else{
                System.out.println("NEW FILE DOESN'T EXISTS");
                BufferedWriter writer = new BufferedWriter (new FileWriter("login_activity.txt", true));
                writer.write(  "\n" + "\nAttempt Number: " + attemptNumber  + "\n"  + "Username: " + username + "\n" + "Login Time: " + loggingAttemptTime + "\n" + "Is login Successful: " + isLoginSuccessful);
                writer.close();
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



}
