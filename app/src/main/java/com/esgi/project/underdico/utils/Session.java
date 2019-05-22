package com.esgi.project.underdico.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Session {
    private static String ARG_SESSION_FILE = "sessionFile";
    private static String ARG_SESSION_KEY = "session";
    private static Token currentToken = null;
    private static User currentUser = null;

    private Session() {}

    public static void setCurrentToken(Token token, Context context) {
        currentToken = token;
        setUser(context);
    }

    public static Token getCurrentToken() {
        return currentToken;
    }

    public static void saveToSharedPrefs(Context context) {
        String tokenToJson = new Gson().toJson(currentToken);
        SharedPreferences sharedPreferences = context.getSharedPreferences(ARG_SESSION_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ARG_SESSION_KEY, tokenToJson);
        editor.commit();

    }

    public static Token getFromSharedPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ARG_SESSION_FILE, Context.MODE_PRIVATE);
        String tokenToJson = sharedPreferences.getString(ARG_SESSION_KEY, null);

        if(tokenToJson != null) {
            return new Gson().fromJson(tokenToJson, Token.class);
        }

        return null;
    }

    public static User getUser() {
        return currentUser;
    }

    private static void setUser(Context context) {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.getUser(Session.getCurrentToken().getToken(), currentToken.getUserId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() || response.body() != null) {
                    currentUser = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static boolean isLoggedIn() {
        return currentToken != null;
    }
}
