package main.models;

import dbhelper.DatabaseRequests;
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

    static public void testingForDuplicates(int id){

        for(int i = 0; i < allAppointments.size(); i++){
            if(id == allAppointments.get(i).getAppointmentId()){
                deleteAppointment(allAppointments.get(i)); // delete old appointment
            }
        }

    }

    /**
     * Add appointment to the list
     */

    static public void addAppointmentToTheList(Appointments newApp){
        allAppointments.add(newApp);
        DatabaseRequests.addNewAppointemntToDB(newApp);
    }

    /**
     * Delete appointment from the list
     */

    static public void deleteAppointment(Appointments selectedApp){
        allAppointments.remove(selectedApp);
    }

    /**
     * Update appointment on the list
     */

    static public void updateAppointment( int appIndex, Appointments selectedApp){
        allAppointments.set(appIndex, selectedApp);
    }



    /*CUSTOMER SECTION*/

    /**
     * Add customer to the list
     */
    static public void addCustomerToTheList(Customers newCustomer){
        allCustomers.add(newCustomer);
    }

    /**
     * Delete customer from the list
     */

    static public void deleteCustomer(Customers selectedCustomer){
        allCustomers.remove(selectedCustomer);
    }


    /**
     * Update customer on the list
     */
    static public void updateCustomer(int custIndex, Customers selectedCustomer){
        allCustomers.set(custIndex, selectedCustomer);
    }
}
