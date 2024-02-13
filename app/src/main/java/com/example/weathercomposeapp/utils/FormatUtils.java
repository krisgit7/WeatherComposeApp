package com.example.weathercomposeapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private FormatUtils() {}

    public static String formatDate(int timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d", Locale.US);
        Date date = new Date((long) timestamp * 1000);

        return sdf.format(date);
    }

    public static String formatDateTime(int timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:aa", Locale.US);
        Date date = new Date((long) timestamp * 1000);

        return sdf.format(date);
    }
}
