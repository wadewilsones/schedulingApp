package main.utils;

import dbhelper.DatabaseRequests;

import java.time.LocalDate;
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

     public Validation(String title, String descr, String loc, String type, LocalDate start, String startTime, LocalDate end, String endTime, String cusId){

        try{
            /*Null check*/
            if(title.equals("") || descr.equals("") || loc.equals("") || type.equals("") || startTime.equals("") || cusId.equals("") || endTime.equals("") || start == null || end == null){
                setValidationValue(false);
                setErrorMessageValue("Fill out all fields!");
            }
            else{
                /**Convert date and time format*/
                try{
                    TimeHandling.convertDateToLocalDateTime(start, startTime);
                    TimeHandling.convertDateToLocalDateTime(end, endTime);
                    /**Check if customerID is correct*/
                    if(DatabaseRequests.getCustomerIdList(Integer.parseInt(cusId))){
                        setValidationValue(true); // validation passed
                    }
                    else{
                        setErrorMessageValue("Wrong Customer_ID value!");
                        setValidationValue(false);
                    }

                }
                catch (Exception e){
                    setValidationValue(false);
                    setErrorMessageValue(e.getMessage());
                }

                /*Business hours check*/
                /*Overlapping hours check*/
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            setValidationValue(false);
            setErrorMessageValue("Wrong value type was entered! Check all your field");
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
