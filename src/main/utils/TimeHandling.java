package main.utils;

import java.time.*;
import java.util.Date;
import java.util.Locale;


/**
 * This class is responsible for providing methods to handling date and time
 */
public class TimeHandling {

    /**
     * Business Hours Data
     */
    public static ZoneId estZone = ZoneId.of("America/New_York"); // set zone
    public static ZonedDateTime workHoursStartEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), estZone);
    public static ZonedDateTime workHoursEndEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22,0), estZone);
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
     * Convert time to EST time to compare
     */
    public static ZonedDateTime convertToEst(LocalDateTime time){

        ZonedDateTime localTime = ZonedDateTime.of(LocalDate.now(),time.toLocalTime(), getTimeZoneName()); // current zone
        ZonedDateTime convertedTime = localTime.withZoneSameInstant(ZoneId.of("America/New_York")); //ET time
        return convertedTime;
    }

    /**
     * Check user system language
     */
    public static boolean isUserLanguageFrench(){

        boolean isFrench = false;
        if(Locale.getDefault().getLanguage().equals("fr")){
            isFrench = true;
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
