package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.login.LoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    LoginView view;
    Context context;

    public LoginPresenter(LoginView view, Context context) {
        this.view = view;
        this.context = context;

        initialize();
        checkIfLogged();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
    }

    private void checkIfLogged() {
        Token token = Session.getFromSharedPref(context);
        if(token != null && token.isValid()) {
            Session.setCurrentToken(token);
            Session.callUserInformation(context, view);
        }
    }

    public void handleLogin(String username, String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            view.askUserToFillFields();
        } else {
            tryLogin(username, password);
        }
    }

    private void tryLogin(String username, String password) {
        view.showProgress(true);
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<Token> call = service.login(username, password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                view.showProgress(false);
                if (response.isSuccessful()) {
                    Session.setCurrentToken(response.body());
                    Session.saveToSharedPrefs(context);
                    Session.callUserInformation(context, view);
                } else {
                    view.loginFail();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showProgress(false);
                view.loginFail();
            }
        });
    }

}
