package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
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
    }

    public void handleLogin(String username, String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            view.askUserToFillFields();
        } else {
            tryLogin(username, password);
        }
    }

    private void tryLogin (String username, String password) {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<Token> call = service.login(username, password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Session.setCurrent(response.body());
                    Session.saveToSharedPrefs(context);
                    view.loginSuccessfully();
                } else {
                    view.loginFail();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                view.loginFail();
            }
        });
    }

}
