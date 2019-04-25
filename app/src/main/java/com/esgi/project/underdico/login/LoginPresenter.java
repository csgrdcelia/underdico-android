package com.esgi.project.underdico.login;

import android.text.TextUtils;

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

            if (success)
                view.loginSuccessfully();
            else
                view.loginFail();
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
