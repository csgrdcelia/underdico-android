package com.esgi.project.underdico.login;

public interface LoginView {
    void askUserToFillFields();
    void showProgress(boolean show);
    void loginSuccessfully();
    void loginFail();
}
