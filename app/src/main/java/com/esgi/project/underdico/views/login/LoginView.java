package com.esgi.project.underdico.views.login;

import com.esgi.project.underdico.views.BaseView;

public interface LoginView extends BaseView {
    void askUserToFillFields();
    void showProgress(boolean show);
    void setUsernameIfPresent();
    void goToRegisterView();
    void loginSuccessfully();
    void showError(String error);
}
