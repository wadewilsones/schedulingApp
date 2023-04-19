package main.utils;

import java.time.LocalDate;
import java.util.Date;

/**
 * This class provides validation for Appointment and for Customers
 */
public class Validation {

    private boolean isValid;
    private String errorMessage;

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
    public String getErrorMessageValue(){
        return this.errorMessage;
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

        /*Type Check for customer ID*/
        try{
            /*Null check*/
            if(title.equals("") || descr.equals("") || loc.equals("") || type.equals("") || startTime.equals("") || cusId.equals("") || endTime.equals(null)){
                setValidationValue(false);
                setErrorMessageValue("Fill out all fields!");
            }
            Integer.getInteger(cusId);
            /*Business hours check*/

            /*Overlapping hours check*/
            setValidationValue(true);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            setValidationValue(false);
            setErrorMessageValue("Wrong value was entered into the field.");
        }
        // Is empty

        //Type validation

    }

}
