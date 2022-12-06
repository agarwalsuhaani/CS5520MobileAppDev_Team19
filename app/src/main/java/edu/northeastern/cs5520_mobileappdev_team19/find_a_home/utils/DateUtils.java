package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    public static String toString(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return dateFormat.format(calendar.getTime());
    }
}