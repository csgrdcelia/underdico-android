package com.esgi.project.underdico.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.esgi.project.underdico.models.Token;
import com.google.gson.Gson;

public class Session {
    private static String ARG_SESSION_FILE = "sessionFile";
    private static String ARG_SESSION_KEY = "session";
    private static Token current = null;

    private Session() {}

    public static void setCurrent(Token token) {
        current = token;
    }

    public static Token getCurrent() {
        return current;
    }

    public static void saveToSharedPrefs(Context context) {
        String tokenToJson = new Gson().toJson(current);
        SharedPreferences sharedPreferences = context.getSharedPreferences(ARG_SESSION_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ARG_SESSION_KEY, tokenToJson);
        editor.commit();

    }

    public static Token getFromSharedPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ARG_SESSION_FILE, Context.MODE_PRIVATE);
        String tokenToJson = sharedPreferences.getString(ARG_SESSION_KEY, null);

        if(tokenToJson != null)
            return new Gson().fromJson(tokenToJson, Token.class);

        return null;
    }

    public static boolean isLoggedIn() {
        return current != null;
    }
}
