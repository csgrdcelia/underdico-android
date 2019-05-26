package com.esgi.project.underdico.views.login;

public interface LoginView {
    void askUserToFillFields();
    void showProgress(boolean show);
    void assignViews();
    void setListeners();
    void setUsernameIfPresent();
    void goToRegisterView();
    void loginSuccessfully();
    void showError(String error);
    void loginFail();
}
