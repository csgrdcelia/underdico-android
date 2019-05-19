package com.esgi.project.underdico.views.register;

public interface RegisterView {
    void askUserToFillFields();
    void assignViews();
    void setListeners();
    void showRegisterSuccess();
    void showLoginView(String username);
    void showError(String error);
}
