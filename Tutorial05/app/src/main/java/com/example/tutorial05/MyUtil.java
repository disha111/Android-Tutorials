package com.example.tutorial05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;

public class MyUtil {
    public static JSONArray jsonArray = null;
    public static final String URL_USERS = "https://jsonplaceholder.typicode.com/users";
    //*******************"For checking network connection"*******************
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) return true;
        else return false;
    }
}
