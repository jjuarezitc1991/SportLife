package com.test.utils;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public final class DateUtils {
    
    private DateUtils() {
        super();
    }

    public static Date createDate(int year, int month, int day) {
        return createJodaDate(year, month, day, 0, 0, 0).toDate();
    }

    public static Date createDate(int year, int month, int day, int hour, int minute, int sec) {
        return createJodaDate(year, month, day, hour, minute, sec).toDate();
    }
    
    private static DateTime createJodaDate(int year, int month, int day, int hourOfDay, int minute, int sec) {
        DateTime date = new DateTime(year, month, day, hourOfDay, minute, sec);
        return date;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);
        if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)) {
            return false;
        }
        if (cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR)) {
            return false;
        }

        return true;
    }
    
    public static Date addDaysToDate(Date date, int nbDaysToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, nbDaysToAdd);
        return cal.getTime();
    }
    
    @SuppressWarnings("deprecation")
    public static int substractInDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return Days.daysBetween(new DateTime(startDate).toDateMidnight(), new DateTime(endDate).toDateMidnight()).getDays();
    }
    
}
