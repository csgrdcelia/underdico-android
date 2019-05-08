package com.esgi.project.underdico.presenters;

import android.text.TextUtils;

import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.login.LoginView;

public class LoginPresenter {

    LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void handleLogin(String username, String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            view.askUserToFillFields();
        } else {

            view.showProgress(true);
            boolean success = tryLogin(username, password);
            view.showProgress(false);

            if (success) {
                view.loginSuccessfully();
                Session.setCurrentUser(new User("1", "Celia"));
            }
            else {
                view.loginFail();
            }
        }
    }

    private boolean tryLogin (String username, String password) {
        // TODO: call API in another thread
        /*try {
            Thread.sleep(2000);
        } catch(InterruptedException exception) {

        }*/
        return true;
    }

}
