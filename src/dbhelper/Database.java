package dbhelper;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * This class creating connection with DB
 */
public abstract class Database {

    /**
     * Creating variables to use in DB connection
     */
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static String password = "Passw0rd!"; // should be hidden from GitHub
    private static String userName = "sqlUser"; // should be hidden from GitHub

    /**
     * @connection declare interface for DB connection
     */
    public static Connection connection;

    /**
     * Connection opening
     */

    public static void startConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // make sure that class is loaded by the classloader
            connection = DriverManager.getConnection(jdbcUrl, userName,password);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

/**
 * Connection closing
 */

    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection is Closed");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
