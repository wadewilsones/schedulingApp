package main.utils;

import java.time.*;
import java.util.Date;
import java.util.Locale;


/**
 * This class is responsible for providing methods to handling date and time
 */
public class TimeHandling {

    /**
     * Get user timezone
     * @return returns name of current timezone
     */
    public static ZoneId getTimeZoneName(){

        //Get TimeZone
        ZoneId userTimeZone = ZoneId.systemDefault(); // get name of user time zone
        return userTimeZone;
    }


    /**
     * Check user system language
     */
    public static boolean isUserLanguageFrench(){

        boolean isFrench = false;
        if(Locale.getDefault().getLanguage().equals("fr")){
            System.out.println("French language");
            isFrench = true;
        }
        else{
            System.out.println("English language");
        }
        return isFrench;
    }

    /**
     * Conversion To localDateTime
     */

    public static LocalDateTime convertDateToLocalDateTime(LocalDate selectedDate, String selectedTime ) throws Exception {

        LocalDateTime convertedDate;
        if(selectedTime.matches("\\d{2}:\\d{2}")){
            try{
                LocalTime newTime = LocalTime.parse(selectedTime);
                convertedDate = LocalDateTime.of(selectedDate, newTime);
                System.out.println(convertedDate);
                return convertedDate;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
                return null;
            }
        else{
            throw new Exception("Wrong time format");

        }
    }
}
