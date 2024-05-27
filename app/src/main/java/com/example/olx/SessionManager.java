package com.example.olx;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static SessionManager instance;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    // Private constructor to prevent instantiation outside this class
    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Static method to get the single instance of SessionManager
    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public void clearAuthToken() {
        editor.remove(KEY_AUTH_TOKEN);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return getAuthToken() != null;
    }
}
