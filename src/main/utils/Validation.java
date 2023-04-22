package main.utils;

import dbhelper.DatabaseRequests;
import main.models.DataPool;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * This class provides validation for Appointment and for Customers
 */
public class Validation {

    private boolean isValid;
    private static String errorMessage;

    /**
     * Getter for @isValid
     */
    public boolean getValidationValue(){
        return this.isValid;
    }

    /**
     * Setter for @isValid
     */
    public void setValidationValue(boolean value){
         this.isValid = value;
    }

    /**
     * Getter for @errorMessage
     */
    public static String getErrorMessageValue(){
        return errorMessage;
    }

    /**
     * Setter for @errorMessage
     */

    public void setErrorMessageValue(String value){
        this.errorMessage = value;
    }

    /**
     * Constructor for Appointment
     */

     public Validation(int appId, String title, String descr, String loc, String type, LocalDate start, String startTime, LocalDate end, String endTime, String cusId, String contactId) {
         /**
          * Null check
          */
         if (title.equals("") || descr.equals("") || loc.equals("") || type.equals("") || startTime.equals("") || cusId.equals("") || endTime.equals("") || start == null || end == null) {
             setValidationValue(false);
             setErrorMessageValue("Fill out all fields!");
         } else {
             /**Convert date and time format*/
             try {
                 LocalDateTime localStartTime = TimeHandling.convertDateToLocalDateTime(start, startTime);
                 LocalDateTime localEndTime = TimeHandling.convertDateToLocalDateTime(end, endTime);
                 ZonedDateTime estStart = TimeHandling.convertToEst(localStartTime);
                 ZonedDateTime estEnd = TimeHandling.convertToEst(localEndTime);

                 /**Check if customerID is correct*/
                 if (!DatabaseRequests.getCustomerIdList(Integer.parseInt(cusId))) {
                     setErrorMessageValue("Wrong Customer_ID value!");
                     setValidationValue(false);
                 }
                 /**Business hours check*/
                 else if (estStart.isBefore(TimeHandling.workHoursStartEST) || estStart.isAfter(TimeHandling.workHoursEndEST) || estEnd.isBefore(TimeHandling.workHoursStartEST) || estEnd.isAfter(TimeHandling.workHoursEndEST)) {
                     setValidationValue(false);
                     setErrorMessageValue("Time is outside business hours!");
                 }
                 else{
                     /**
                      * Overlapping appointments validation
                      * Get contact ID
                      */
                     ResultSet contacts = DatabaseRequests.getAllContacts(); // retrieve all contacts
                     String contactName = null;
                     int idOfSelectedContact = 0;
                     while(contacts.next()){
                         contactName = contacts.getString("Contact_Name");
                         if(contactName.equals(contactId)){
                             idOfSelectedContact = contacts.getInt("Contact_ID");
                         }
                     }
                     /**
                      * Compare time
                      */
                     for(int i = 0; i < DataPool.getAllAppointments().size(); i++){
                         // find customerID
                         if(idOfSelectedContact == DataPool.getAllAppointments().get(i).getContact_ID()){

                             /**
                              * If input start time is between start/end of other appointment time
                              */
                             LocalTime inputStartTime = localStartTime.toLocalTime();
                             LocalTime inputEndTime = localEndTime.toLocalTime();

                             LocalTime appStartTime = DataPool.getAllAppointments().get(i).getStartDate().toLocalTime();
                             LocalTime appEndTime = DataPool.getAllAppointments().get(i).getEndDate().toLocalTime();

                             int getApID = DataPool.getAllAppointments().get(i).getAppointmentId();
                             /**
                              * If time is overlapped
                              */
                             if(getApID != appId){
                                 if(inputEndTime.isAfter(appStartTime) && inputStartTime.isBefore(appEndTime)){
                                     System.out.println(getApID + " compare with" + appId);
                                     setValidationValue(false);
                                     setErrorMessageValue("This time is not available! Change time.");
                                 }
                                 /**
                                  * Valid
                                  */
                                 else{
                                     setValidationValue(true);
                                 }
                             }

                         }
                     }
                 }

             } catch (Exception e) {
                 setValidationValue(false);
                 setErrorMessageValue("Wrong value type was entered! Check all your field");
             }

         }
     }
    /**
     * Constructor for Customers
     */

    public Validation(String name,  String street,  String postal,  String phone){

        //Null check
        if(!(name.equals("") || street.equals("") || postal.equals("") || phone.equals(""))){

            String phonePattern = "\\d{3}-\\d{3}-\\d{4}";
            String secondPhonePattern = "\\d{1,3}-\\d{3}-\\d{3}-\\d{4}";
            if(!phone.matches(phonePattern) && !phone.matches(secondPhonePattern)){
                setErrorMessageValue("Wrong phone number format!");
                setValidationValue(false);
                System.out.println("NOT VALID PHONE");
            }
            else{
                setValidationValue(true);
                System.out.println("VALID");
            }
        }
        else{
            setErrorMessageValue("Fill out all fields!");
            setValidationValue(false);
        }

    }
}
