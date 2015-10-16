package org.sample.currency.app.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Static (not instantiable) utility functions.
 *
 * Created by Mohamed Mekkawy.
 */
public abstract  class Utils {

    /**
     * Checks if the passed date is before Today 00:00
     */
    public static boolean historicalDataRequested(Date date) {
        if(date ==null)
            return false;

        Calendar givenDate = Calendar.getInstance();
        givenDate.setTime(date);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);

        if(givenDate.before(now))
            return true;
        else
            return false;
    }
}
