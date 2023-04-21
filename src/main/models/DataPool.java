package main.models;

import dbhelper.DatabaseRequests;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class controls adding new appointments and customers to lists. Also handle updating and deleting.
 */
public class DataPool {

    /**
     * Holds all appointment records
     */
   static private ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    /**
     * Holds all current customers records
     */
    static private ObservableList<Customers> allCustomers = FXCollections.observableArrayList();


    /*APPOINTMENTS SECTION*/

    /**
     * Get Appointment List
     */
    static public ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }

    /**
     * Duplicates checking
     */

    static public void testingForDuplicates(int id, Object newAdding) throws Exception {

        if(newAdding instanceof Appointments){

            for(int i = 0; i < allAppointments.size(); i++){
                if(id == allAppointments.get(i).getAppointmentId()){
                    deleteAppointment(allAppointments.get(i)); // delete old appointment
                }
            }
        }
        else if(newAdding instanceof Customers){
            System.out.println("Testing Customers");
            for(int i = 0; i < allCustomers.size(); i++){
                if(id == allCustomers.get(i).getCustomer_ID()){
                    deleteCustomer(allCustomers.get(i)); // delete old customer
                }
        }

     }
    }

    /**
     * Add appointment to the list
     */

    static public void addAppointmentToTheList(Appointments newApp) throws  Exception{
        allAppointments.add(newApp);
        DatabaseRequests.addNewAppointemntToDB(newApp);
    }

    /**
     * Delete appointment from the list
     */

    static public void deleteAppointment(Appointments selectedApp) {

        allAppointments.remove(selectedApp);
        try{
            DatabaseRequests.deleteAppointment(selectedApp);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Update appointment on the list
     */

    static public void updateAppointment( int appIndex, Appointments selectedApp) throws Exception {
        allAppointments.set(appIndex, selectedApp);
        //Update Database
        try{
            DatabaseRequests.updateAppointmnet(selectedApp);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception("Can't update appointment!");
        }

    }


    /*CUSTOMER SECTION*/

    /**
     * Get all customers
     */

    static public ObservableList<Customers> getAllCustomers(){
        return allCustomers;
    }


    /**
     * Add customer to the list
     */
    static public void addCustomerToTheList(Customers newCustomer){

        allCustomers.add(newCustomer);
        try{
            DatabaseRequests.addNewCustomerToDb(newCustomer);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Delete customer from the list
     */

    static public void deleteCustomer(Customers selectedCustomer) throws Exception{
        allCustomers.remove(selectedCustomer);
        DatabaseRequests.deleteCustomer(selectedCustomer);
    }


    /**
     * Update customer on the list
     */
    static public void updateCustomer(int custIndex, Customers selectedCustomer){
        allCustomers.set(custIndex, selectedCustomer);
    }
}
