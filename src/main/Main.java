package main;

import dbhelper.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This is a starter file.
 */
public class Main extends Application{

    /**
    * Start program
    */
    public static void main(String args[]){

        /**
         * Open connection with Database
         */
        //Database.startConnection();
        launch(args);
    }

    /**
     * Loading FXML LOGIN and setting Scene for UI
     * */

    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println(getClass().getResource("resources/login.fxml").getPath());
        Parent root = FXMLLoader.load(getClass().getResource("resources/login.fxml"));
        primaryStage.setTitle("Scheduling application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
