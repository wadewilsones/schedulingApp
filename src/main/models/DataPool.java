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
    static public ObservableList<Appointments> getAllAppointments() {
        return allAppointments;
    }

    /**
     * Duplicates checking
     */

    static public boolean testingForDuplicates(int id, Object newAdding) throws Exception {
        boolean isDuplicateExists = false;
        if (newAdding instanceof Appointments) {

            for (int i = 0; i < allAppointments.size(); i++) {
                if (id == allAppointments.get(i).getAppointmentId()) {
                    deleteAppointment(allAppointments.get(i)); // delete old appointment
                    isDuplicateExists = true;
                }
            }
        }
        else if(newAdding instanceof Customers){
            for (int i = 0; i < getAllCustomers().size(); i++) {
                if (id == getAllCustomers().get(i).getCustomer_ID()) {
                    isDuplicateExists = true;
                }
            }
        }
        return isDuplicateExists;
    }




        /**
         * Add appointment to the list
         */

        static public void addAppointmentToTheList (Appointments newApp) throws Exception {
            allAppointments.add(newApp);
            DatabaseRequests.addNewAppointemntToDB(newApp);
        }

        /**
         * Delete appointment from the list
         */

        static public void deleteAppointment (Appointments selectedApp){

            allAppointments.remove(selectedApp);
            try {
                DatabaseRequests.deleteAppointment(selectedApp);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        /**
         * Update appointment on the list
         */

        static public void updateAppointment ( int appIndex, Appointments selectedApp) throws Exception {
            allAppointments.set(appIndex, selectedApp);
            //Update Database
            try {
                DatabaseRequests.updateAppointmnet(selectedApp);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new Exception("Can't update appointment!");
            }

        }


        /*CUSTOMER SECTION*/

        /**
         * Get all customers
         */

        static public ObservableList<Customers> getAllCustomers () {
            return allCustomers;
        }


        /**
         * Add customer to the list
         */
        static public void addCustomerToTheList (Customers newCustomer){

            allCustomers.add(newCustomer);
            try {
                DatabaseRequests.addNewCustomerToDb(newCustomer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        /**
         * Delete customer from the list
         */

        static public void deleteCustomer (Customers selectedCustomer) throws Exception {

            /***
             * Check if customer has appointments, if yes - delete them too
             */
            for (int i = 0; i < getAllAppointments().size(); i++) {
                if (selectedCustomer.getCustomer_ID() == getAllAppointments().get(i).getCustomer_ID()) {
                    deleteAppointment(getAllAppointments().get(i));
                } else {
                    System.out.println("No appointments were find");

                }
            }

            allCustomers.remove(selectedCustomer);
            DatabaseRequests.deleteCustomer(selectedCustomer);

        }

        /**
         * Update customer on the list and in DB
         */
        static public void updateCustomer(int custIndex, Customers selectedCustomer){
            try{
                allCustomers.set(custIndex, selectedCustomer);
                DatabaseRequests.updateCustomerinDB(selectedCustomer);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
