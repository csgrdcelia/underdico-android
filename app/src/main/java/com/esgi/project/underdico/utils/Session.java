package com.esgi.project.underdico.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.views.login.LoginView;
import com.esgi.project.underdico.views.splash.SplashView;
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

    public static void setCurrentToken(Token token) {
        currentToken = token;
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

    public static void clearSharedPreferences(Context context) {
        context.deleteSharedPreferences(ARG_SESSION_FILE);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Session.currentUser = currentUser;
    }

    public static void callUserInformation(Context context, SplashView view) {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.getUser(Session.getCurrentToken().getValue(), Session.getCurrentToken().getUserId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() || response.body() != null) {
                    currentUser = response.body();
                    view.redirectToMainActivity();
                }  else if (response.code() == Constants.HTTP_UNAUTHORIZED) {
                    view.showToast(context.getString(R.string.expired_token));
                    view.redirectToLoginPage();
                } else {
                    view.showToast(context.getString(R.string.error));
                    view.redirectToLoginPage();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.redirectToLoginPage();
                view.showToast(context.getString(R.string.error_network));
            }
        });
    }

    public static void callUserInformation(Context context, LoginView view) {
        view.showProgress(true);
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.getUser(Session.getCurrentToken().getValue(), Session.getCurrentToken().getUserId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() || response.body() != null) {
                    currentUser = response.body();
                    view.loginSuccessfully();
                }  else if (response.code() == Constants.HTTP_UNAUTHORIZED) {
                    view.showToast(context.getString(R.string.expired_token));
                } else {
                    view.showToast(context.getString(R.string.error));
                }
                view.showProgress(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showProgress(false);
                view.showToast(context.getString(R.string.error_network));
            }
        });
    }



}
