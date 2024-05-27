package com.example.olx;

import android.content.Context;
import android.widget.Toast;


public class Utils {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public static String[] getCategories() {
        // TODO: Get String List of each category
        // String[] getCategories();

        // return getCategories();
        return new String[]{"Phone", "TV"};
    }
}
