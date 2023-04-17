package main.utils;

import java.time.*;
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
}
